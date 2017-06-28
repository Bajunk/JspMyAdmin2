package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebController(authentication = true, path = "/database_create_view_post.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class CreateViewPostController {

	@Detect
	private Messages messages;
	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private CreateViewBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject createView() throws JSONException, EncodingException {

		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
				jsonObject.put(Constants.ERR, messages.getMessage(AppConstants.MSG_VIEW_ALREADY_EXISTED));
			} else {
				String result = structureLogic.createView(bean);
				jsonObject.append(Constants.ERR, Constants.BLANK);
				if (result != null) {
					jsonObject.append(Constants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(Constants.MSG_KEY, AppConstants.MSG_VIEW_CREATED);
					jsonObject.append(Constants.MSG, encodeObj.encode(msg.toString()));
				}
			}
		} catch (SQLException e) {
			jsonObject.append(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}
}
