/**
 * 
 */
package com.jspmyadmin.framework.connection;

/**
 *
 * _at 2016/08/29
 *
 */
public final class ConnectionTypeCheck {

	/**
	 * 
	 */
	private ConnectionTypeCheck() {
		// nothing
	}

	/**
	 * 
	 * @return
	 */
	public static ConnectionType check() {
		return ConnectionFactory.connectionType;
	}

}
