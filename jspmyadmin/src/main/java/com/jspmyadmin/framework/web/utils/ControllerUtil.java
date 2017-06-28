/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.*;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * _at 2016/01/29
 *
 */
class ControllerUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtil.class);

	static final String HANDLEGET = "handleGet";
	static final String HANDLEPOST = "handlePost";

	private static final String _APP_PACKAGE = "com.jspmyadmin.app";
	private static final String _CLASS = ".class";
	static final Map<String, PathInfo> PATH_MAP = new ConcurrentHashMap<>();

	private static List<String> _classList = new ArrayList<>();
	private static ClassFileFilter _fileFilter = new ClassFileFilter();

	/**
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * 
	 */
	static void scan() throws ClassNotFoundException, Exception {
		_scanAllClasses();
		_scanControllers();
	}

	/**
	 * 
	 */
	static void destroy() {
		PATH_MAP.clear();
	}

	/**
	 * 
	 * @throws URISyntaxException
	 */
	private static void _scanAllClasses() throws URISyntaxException, IOException {

		URL packageURL;
		URI uri;
		ClassLoader classLoader;
        VirtualFile file;
        String classPackage;
        List<VirtualFile> children;
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
			packageURL = classLoader.getResource(_APP_PACKAGE.replace(Constants.SYMBOL_DOT, Constants.SYMBOL_BACK_SLASH));
			uri = new URI(packageURL != null ? packageURL.toString() : "");
    		file = VFS.getChild(uri);
    		children = file.getChildrenRecursively();
    		for(VirtualFile child: children){
    		    if(child.isFile()){
    		        classPackage = getClassPackage(child.getPathName());
    		        _classList.add(classPackage);
                }
            }
        } finally {
			classLoader = null;
			uri = null;
			packageURL = null;
		}
	}

    private static String getClassPackage(String path){
        String splitPath = path.split("/classes/")[1];
        String formattedPath = splitPath.replace('/', '.');
        int suffixLength = _CLASS.length();
        return formattedPath.substring(0, formattedPath.length() - suffixLength);
    }

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private static void _scanControllers() throws ClassNotFoundException, Exception {
		if (_classList.size() < 1) {
			return;
		}
		Iterator<String> classIterator = _classList.iterator();
		String strClass;
		Class<?> klass;
		PathInfo pathInfo;
		WebController webController;
		try {
			while (classIterator.hasNext()) {
				strClass = classIterator.next();
				klass = Class.forName(strClass);
				webController = klass.getAnnotation(WebController.class);
				if (webController != null) {
					pathInfo = new PathInfo();
					pathInfo.setController(klass);
					pathInfo.setAuthRequired(webController.authentication());
					pathInfo.setRequestLevel(webController.requestLevel());
					if (klass.isAnnotationPresent(Rest.class)) {
						pathInfo.setResponseBody(true);
					}

					boolean isMapped = false;
					for (Method method : klass.getDeclaredMethods()) {
						if (method.isAnnotationPresent(HandleGetOrPost.class)) {
							isMapped = true;
							pathInfo.setAnyMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setValidateToken(true);
							}
						} else if (method.isAnnotationPresent(HandleGet.class)) {
							isMapped = true;
							pathInfo.setGetMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setValidateToken(true);
							}
						} else if (method.isAnnotationPresent(HandlePost.class)) {
							isMapped = true;
							pathInfo.setPostMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setPostDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setPostValidateToken(true);
							}
						}
					}
					if (isMapped) {

						Map<Field, DetectType> detectMap = new HashMap<>();
						for (Field field : klass.getDeclaredFields()) {
							if (field.isAnnotationPresent(Detect.class)) {
								Class<?> fieldType = field.getType();
								field.setAccessible(true);
								if (EncodeHelper.class == fieldType) {
									detectMap.put(field, DetectType.ENCODE_HELPER);
								} else if (RequestAdaptor.class == fieldType) {
									detectMap.put(field, DetectType.REQUEST_ADAPTOR);
								} else if (RedirectParams.class == fieldType) {
									detectMap.put(field, DetectType.REDIRECT_PARAMS);
								} else if (HttpServletRequest.class == fieldType) {
									detectMap.put(field, DetectType.REQUEST);
								} else if (HttpServletResponse.class == fieldType) {
									detectMap.put(field, DetectType.RESPONSE);
								} else if (HttpSession.class == fieldType) {
									detectMap.put(field, DetectType.SESSION);
								} else if (Messages.class == fieldType) {
									detectMap.put(field, DetectType.MESSAGES);
								} else if (View.class == fieldType) {
									detectMap.put(field, DetectType.VIEW);
								}
							} else if (field.isAnnotationPresent(Model.class)) {
								field.setAccessible(true);
								pathInfo.setModel(field);
							}

						}
						pathInfo.setDetectMap(detectMap);

						PATH_MAP.put(webController.path(), pathInfo);
					}
				}
			}
		} finally {
			_fileFilter = null;
			_classList = null;
		}
	}

	/**
	 * 
	 *
	 * _at 2016/01/29
	 *
	 */
	private static class ClassFileFilter implements FileFilter {

		public boolean accept(File pathname) {
            return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(_CLASS);
        }

	}
}
