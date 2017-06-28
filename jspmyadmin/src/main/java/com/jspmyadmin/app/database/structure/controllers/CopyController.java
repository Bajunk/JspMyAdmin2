/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
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
@WebController(authentication = true, path = "/database_structure_copy.html", requestLevel = RequestLevel.DATABASE)
public class CopyController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private StructureBean bean;

	@HandlePost
	@ValidateToken
	private void copyTables() {

		StructureLogic structureLogic = null;
		try {
			structureLogic = new StructureLogic();
			structureLogic.copyTables(bean);
			redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_COPY_DONE);
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
	}

}
