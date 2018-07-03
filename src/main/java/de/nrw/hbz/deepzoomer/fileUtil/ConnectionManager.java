/**
 * 
 */
package de.nrw.hbz.deepzoomer.fileUtil;

import java.net.HttpURLConnection;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;

/**
 * @author aquast
 *
 */
public abstract class ConnectionManager {
	
	
	public static HttpURLConnection connection = null;
	
	public static void setConnectionProperties(HttpURLConnection conn) {
		connection = conn;
		
		if ( Configuration.getConnectionTimeout() != null && Configuration.getConnectionTimeout().isEmpty() == false ) {
			int timeout = Integer.parseInt(Configuration.getConnectionTimeout());
			connection.setConnectTimeout(timeout);
		}
	}
	
}
