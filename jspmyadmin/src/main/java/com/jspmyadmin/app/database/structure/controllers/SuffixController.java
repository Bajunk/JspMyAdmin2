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

@WebController(authentication = true, path = "/database_structure_suffix.html", requestLevel = RequestLevel.DATABASE)
public class SuffixController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private View view;
	@Model
	private StructureBean bean;

	@HandlePost
	@ValidateToken
	private void suffixTables() {

		StructureLogic structureLogic = null;
		try {
			structureLogic = new StructureLogic();
			if (bean.getType() != null) {
				if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
					structureLogic.addSuffix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
					structureLogic.replaceSuffix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
					structureLogic.removeSuffix(bean);
					redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
				} else if (Constants.COPY.equalsIgnoreCase(bean.getType())) {
					redirectParams.put(Constants.MSG_KEY, Constants.BLANK);
				}

			}
		} catch (SQLException e) {
			redirectParams.put(Constants.ERR, e.getMessage());
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(bean.getAction());
	}

}
