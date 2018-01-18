package com.etree.rts.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = { "com.etree.rts" })
public class RTSFilter implements Constants, Filter {
	@Autowired
	RTSProperties rtsProperties;
	private static final Logger logger = LoggerFactory.getLogger(RTSFilter.class);

	@SuppressWarnings("unused")
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("In doFilter");
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		cors(request, response);
		String url = request.getRequestURL().toString();
		if (url.contains("swagger") || url.contains("api-docs") || url.contains("image/stock")
				|| url.contains("image/order") || url.contains("sync/order/status") || url.contains("printTemplates")) {
			chain.doFilter(request, response);
		} else {;
			String xApiKey = request.getHeader(ACCESS_KEY_HEADER);
			String jsonResponse = null;
			jsonResponse = validateRequest(xApiKey);
			if (jsonResponse != null) {
				response.getWriter().println(jsonResponse);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	public void destroy() {
	}

	public String validateRequest(String xApiKey) {
		Response response = new Response();
		ArrayList<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		if (!rtsProperties.getHeaderApiKey().equalsIgnoreCase(xApiKey)) {
			ErrorObject errorObject = new ErrorObject();
			errorObject.setTitle("Invalid Header Api Key");
			errorObject.setDetail("Invalid Header Api Key");
			errorObjects.add(errorObject);
		}
		if (errorObjects != null && !errorObjects.isEmpty()) {
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors((ErrorObject[]) errorObjects.toArray(new ErrorObject[errorObjects.size()]));
		} else {
			response.setStatus(StatusCode.SUCCESS.name());
		}
		if (response != null && response.getStatus().equalsIgnoreCase(StatusCode.ERROR.name()))
			return CommonUtils.getJson(response);
		return null;

	}

	public void cors(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,X-API-KEY");
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addIntHeader("Access-Control-Max-Age", 3600);

	}

}