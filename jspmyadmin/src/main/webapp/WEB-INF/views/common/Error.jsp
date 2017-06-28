<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<style type="text/css">
html {
	font-size: ${sessionScope.fontsize}%;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
<style type="text/css">
html {
	overflow: auto;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
</head>
<body>
	<div style="width: 100%; text-align: center; margin-top: 50px;">
		<div style="width: 400px; margin-left: auto; margin-right: auto;">
			<a href="${pageContext.request.contextPath}">
				<img alt="Logo" id="site-logo"
					 src="${pageContext.request.contextPath}/components/images/logo.png">
			</a>
		</div>
	</div>

    <div class="dialog">
        <div class="dialog-widget dialog-error">
            <div class="close" id="error-close">&#10005;</div>
            <div class="dialog-header">
                <m:print key="lbl.errors" /> ${requestScope.command.status}
                <jma:notEmpty name="#status" scope="command">
                    <m:print key="status" scope="command" />
                </jma:notEmpty>
            </div>
            <div class="dialog-content">
                <p>
                    <jma:notEmpty name="#err_key" scope="command">
                        <m:print key="err_key" scope="command" />
                    </jma:notEmpty>
                    <jma:notEmpty name="#message" scope="command">
                        <m:print key="message" scope="command" />
                    </jma:notEmpty>
                </p>
            </div>
        </div>
    </div>

    <script type="application/javascript">
        $(function () {
            $('#error-close').click(function () {
                $(this).parent().parent().empty().remove();
            });
        });
    </script>
</body>
</html>