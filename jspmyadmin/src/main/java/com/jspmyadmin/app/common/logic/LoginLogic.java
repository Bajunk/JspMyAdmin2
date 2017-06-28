/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import com.jspmyadmin.app.common.beans.LoginBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class LoginLogic extends AbstractLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginLogic.class);

	public boolean isValidConnection(LoginBean bean) {
		ApiConnection apiConnection = null;
		HttpSession httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		try {
			httpSession.setAttribute(Constants.SESSION_HOST, bean.getHostname());
			httpSession.setAttribute(Constants.SESSION_PORT, bean.getPortnumber());
			httpSession.setAttribute(Constants.SESSION_USER, bean.getUsername());
			httpSession.setAttribute(Constants.SESSION_PASS, bean.getPassword());
			apiConnection = super.getConnection();
			if (apiConnection != null) {
				httpSession.setAttribute(Constants.SESSION, true);
				return true;
			}
		} catch (SQLException e) {
		    LOGGER.error("Failed to connect", e);
		} finally {
			close(apiConnection);
		}
		return false;
	}

}
