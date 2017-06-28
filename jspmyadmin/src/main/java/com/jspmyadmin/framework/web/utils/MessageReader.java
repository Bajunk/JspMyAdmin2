/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * _at 2016/01/27
 *
 */
public class MessageReader implements Messages {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReader.class);

	private static String _properties = ".properties";
	private static String _equals = "=";
	private static final String _DEFAULT = "default";
	private static final String _ENCODE = "UTF-8";
	private static final Map<String, Map<String, String>> _MESSAGEMAP = new ConcurrentHashMap<>();

	/**
	 * 
	 */
	public static synchronized void read() {
		if (_MESSAGEMAP.size() > 0) {
			return;
		}
		String dot = "\\.";
		String underscore = "_";
		String name;
		URL location;
        VirtualFile directory;
        List<VirtualFile> files;
		URI uri;
		try {
            location = Thread.currentThread().getContextClassLoader().getResource("com/jspmyadmin/messages");
			uri = new URI(location.toString());
            directory = VFS.getChild(uri);
            files = directory.getChildrenRecursively();
			if (files != null) {
                for(VirtualFile file: files) {
                    if(file.isFile()){
                        name = file.getName().split(dot)[0];
                        if (name.contains(underscore)) {
                            name = file.getName().split(dot)[0].split(underscore)[1];
                            _readFile(file.getPhysicalFile(), name);
                        } else {
                            _readFile(file.getPhysicalFile(), _DEFAULT);
                        }
                    }
                }
			}
		} catch (URISyntaxException e) {
            LOGGER.error("Resource path error", e);
		} catch (IOException e) {
		    LOGGER.error("I/O error", e);
		} finally {
			_properties = null;
			_equals = null;
		}
	}

	/**
	 * 
	 * @param file
	 * @param locale
	 * @throws IOException
	 */
	private static void _readFile(File file, String locale) throws IOException {

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String line = null;
		String[] data = null;
		Map<String, String> messageMap = null;
		try {
			inputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(inputStream, _ENCODE);
			bufferedReader = new BufferedReader(inputStreamReader);
			messageMap = new ConcurrentHashMap<String, String>();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(_equals)) {
					data = line.split(_equals);
					messageMap.put(data[0].trim(), data[1].trim());
				}
			}
			_MESSAGEMAP.put(locale, messageMap);
		} finally {
			data = null;
			line = null;
			if (bufferedReader != null) {
				bufferedReader.close();
				bufferedReader = null;
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
				inputStreamReader = null;
			}
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
			file = null;
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static synchronized void remove() throws IOException {
		for (String key : _MESSAGEMAP.keySet()) {
			_MESSAGEMAP.get(key).clear();
		}
		_MESSAGEMAP.clear();
	}

	private final Map<String, String> _messageMap;

	/**
	 * 
	 * @param locale
	 */
	public MessageReader(String locale) {
		if (locale != null && _MESSAGEMAP.containsKey(locale)) {
			_messageMap = _MESSAGEMAP.get(locale);
		} else {
			_messageMap = _MESSAGEMAP.get(_DEFAULT);
		}
		locale = null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return _messageMap.get(key);
	}

}
