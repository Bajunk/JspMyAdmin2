package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

@WebController(authentication = true, path = "/database_create_view.html", requestLevel = RequestLevel.DATABASE)
public class CreateViewController {

	@Detect
	private RedirectParams redirectParams;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private View view;
	@Model
	private CreateViewBean bean;

	@HandlePost
	@ValidateToken
	private void createView() throws EncodingException, SQLException {
		StructureLogic structureLogic = new StructureLogic();
		if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
			redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_VIEW_ALREADY_EXISTED);
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_VIEW_LIST);
			return;
		}

		bean.setToken(requestAdaptor.generateToken());
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_VIEW);
	}
}
