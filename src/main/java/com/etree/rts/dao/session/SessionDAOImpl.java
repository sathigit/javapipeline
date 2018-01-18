package com.etree.rts.dao.session;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.session.Session;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class SessionDAOImpl implements SessionDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(SessionDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Session> getSessions() throws Exception {
		try {
			String sql = "SELECT * FROM session";
			return jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<Session>(Session.class));
		} catch (Exception e) {
			logger.error("Exception in getSessions", e);
		}
		return null;
	}

	public Response saveSession(Session session) {
		Response response = CommonUtils.getResponseObject("Add session data");
		try {
			String sql = "INSERT INTO session (sessionId,userId,lastAccessTime) VALUES(?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { session.getSessionId(), session.getUserId(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveSession", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	public Response updateSession(Session session) {
		Response response = CommonUtils.getResponseObject("Update session data");
		try {
			String sql = "UPDATE session SET userId=?, lastAccessTime=? WHERE sessionId=?";

			int res = jdbcTemplate.update(sql, session.getUserId(), new Date(), session.getSessionId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update session data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	public Response updateSession(String sessionId) {
		Response response = CommonUtils.getResponseObject("Update session data");
		try {
			String sql = "UPDATE session SET lastAccessTime=? WHERE sessionId=?";

			int res = jdbcTemplate.update(sql, new Date(), sessionId);

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update session data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Session getSession(String sessionId) {
		try {
			String sql = "SELECT * FROM session where sessionId=?";
			return (Session) jdbcTemplate.queryForObject(sql, new Object[] { sessionId },
					new BeanPropertyRowMapper(Session.class));
		} catch (Exception e) {
			logger.error("Exception in getSession", e);
			return null;
		}
	}
}