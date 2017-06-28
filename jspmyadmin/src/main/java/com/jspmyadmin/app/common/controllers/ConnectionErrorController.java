/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.InstallBean;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * _at 2016/02/03
 *
 */
@WebController(authentication = false, path = AppConstants.PATH_CONNECTION_ERROR, requestLevel = RequestLevel.DEFAULT)
public class ConnectionErrorController {

	@Detect
	private HttpSession session;
	@Detect
	private View view;
	@Model
	private InstallBean bean;

	@HandleGetOrPost
	private void load() throws EncodingException, SQLException {

		if (session.getAttribute(Constants.SESSION_CONNECT) != null) {
			session.invalidate();
			view.setType(ViewType.FORWARD);
			view.setPath(AppConstants.JSP_COMMON_CONNECTION_ERROR);
		} else {
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_HOME);
		}
	}

}
