/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * _at 2016/02/23
 *
 */
@WebController(authentication = true, path = "/database_structure_utils.text", requestLevel = RequestLevel.DEFAULT)
@Rest
public class UtilsController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Model
	private StructureBean bean;

	@HandlePost
	private JSONObject getColumnCreate() throws Exception {
		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			if (Constants.COLUMN.equalsIgnoreCase(bean.getType())) {
				String result = structureLogic.getNewColumn(bean);
				jsonObject.put(Constants.ERR, Constants.BLANK);
				jsonObject.put(Constants.DATA, result);
			}
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
