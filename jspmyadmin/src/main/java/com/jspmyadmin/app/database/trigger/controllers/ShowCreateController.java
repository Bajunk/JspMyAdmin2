/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import com.jspmyadmin.app.database.trigger.beans.TriggerListBean;
import com.jspmyadmin.app.database.trigger.logic.TriggerLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * _at 2016/03/30
 *
 */
@WebController(authentication = true, path = "/database_trigger_show_create.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class ShowCreateController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private TriggerListBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject showCreateTriggier() throws JSONException, EncodingException {
		JSONObject jsonObject = new JSONObject();
		TriggerLogic triggerLogic = null;
		try {
			triggerLogic = new TriggerLogic();
			String result = triggerLogic.showCreate(bean);
			jsonObject.put(Constants.DATA, result);
			jsonObject.put(Constants.ERR, Constants.BLANK);
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
