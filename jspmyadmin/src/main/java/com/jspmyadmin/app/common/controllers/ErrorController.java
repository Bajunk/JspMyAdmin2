package com.jspmyadmin.app.common.controllers;

import com.jspmyadmin.app.common.beans.ErrorBean;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RedirectParams;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebController(authentication = false, path = AppConstants.PATH_ERROR, requestLevel = RequestLevel.DEFAULT)
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Detect
    private HttpServletRequest request;
    @Detect
    private RedirectParams redirectParams;
    @Detect
    private HttpSession session;
    @Detect
    private View view;
    @Model
    private ErrorBean bean;

    @HandleGet
    private void handleError() {
        if(redirectParams.get(Constants.ERR)!=null){
            bean.setErr(String.valueOf(redirectParams.get(Constants.ERR)));
            bean.setStatus(String.valueOf(redirectParams.get(Constants.ERR)));
        }
        if(redirectParams.get(Constants.ERR_KEY)!=null){
            bean.setErr_key(String.valueOf(redirectParams.get(Constants.ERR_KEY)));
            bean.setMessage(String.valueOf(redirectParams.get(Constants.ERR_KEY)));
        }
        String errorStatus = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        String uri = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        if(!errorStatus.equalsIgnoreCase("null")){
            bean.setStatus(errorStatus);
            if(errorStatus.equalsIgnoreCase(String.valueOf(HttpServletResponse.SC_NOT_FOUND))){
                bean.setErr_key(AppConstants.ERR_NOT_FOUND);
                bean.setMessage(uri!=null?uri+" "+AppConstants.ERR_NOT_FOUND:AppConstants.ERR_NOT_FOUND);
            }
        }
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_COMMON_ERROR);
    }

}
