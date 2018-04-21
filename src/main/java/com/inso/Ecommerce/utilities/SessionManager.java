package com.inso.Ecommerce.utilities;

import javax.servlet.http.HttpSession;


public class SessionManager {

	private static SessionManager _Instance;
	
	private final String KEY_MAIL = "MAIL";
	
	
	private SessionManager() {}
	
	public static SessionManager getInstance() {
		if(_Instance == null)
			_Instance = new SessionManager();
		
		return _Instance;
	}
	
	private  boolean isValid(HttpSession session) {
		try {
			return session != null && session.getAttribute(KEY_MAIL) != null;
		}catch(NullPointerException e) {
			return false;
		}
	}
	
	public void setSessionEmail(HttpSession session, String email) {
		session.setAttribute(KEY_MAIL, email);
	}

	public String getSessionEmail(HttpSession session) {
		return (!isValid(session))? null : session.getAttribute(KEY_MAIL).toString();
	}
	
	public void delete(HttpSession session) {
		if(session!=null)
			session.invalidate();
	}
}
