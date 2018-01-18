package com.etree.rts.dao.session;

import java.util.List;

import com.etree.rts.domain.session.Session;
import com.etree.rts.response.Response;

public interface SessionDAO {

	public List<Session> getSessions() throws Exception;

	public Response saveSession(Session session) throws Exception;

	public Response updateSession(Session session) throws Exception;

	public Session getSession(String sessionId) throws Exception;

	public Response updateSession(String sessionId) throws Exception;
}