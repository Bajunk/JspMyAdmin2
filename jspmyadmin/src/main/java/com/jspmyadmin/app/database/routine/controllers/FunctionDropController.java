/**
 * 
 */
package com.jspmyadmin.app.database.routine.controllers;

import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

import java.sql.SQLException;

/**
 *
 * _at 2016/02/16
 *
 */
@WebController(authentication = true, path = "/database_function_drop.html", requestLevel = RequestLevel.DATABASE)
public class FunctionDropController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private RoutineListBean bean;

	@HandlePost
	@ValidateToken
	private void functionDrop() {

		RoutineLogic routineLogic = null;
		try {
			routineLogic = new RoutineLogic();
			routineLogic.dropRoutines(bean, false);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_FUNCTION_DROP_SUCCESS);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_FUNCTIONS);
	}

}
