package com.jspmyadmin.app.common.beans;

import com.jspmyadmin.framework.web.utils.Bean;

public class ErrorBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String status;
    private String message;

    public ErrorBean(){
        status = "unknown";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorBean{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                super.toString() +
                '}';
    }
}
