/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author aquast
 *
 */
public class Configuration {

	// Initialize logger object
	private static Logger log = Logger.getLogger(Configuration.class);

	private static Properties defProp = new Properties();
	private static Properties sysProp = null;

	private static String serviceUrl = null;
	private static String tempDirUrl = null;
	private static String resultDirUrl = null;

	static String resultDirPath = null;
	static String tempDirPath = null;

	static {
		initLog();
		setDefaultProp();
		loadConfigurationFile();
		setResultDirPath();
		setTempDirPath();
		setServiceUrl();
		setResultDirUrl();
		setTempDirUrl();
	}

	private static void setDefaultProp() {
		defProp.setProperty("host", "localhost");
		defProp.setProperty("protocol", "http");
		defProp.setProperty("port", "8080");
		defProp.setProperty("path", "deepzoom");
		defProp.setProperty("tempDir", "temp");
		defProp.setProperty("resultDir", "tilesCache");
		defProp.setProperty("userDir", "all");
		defProp.setProperty("workingDir", "/var/lib/tomcat7/webapps/");

	}

	private static void setTempDirUrl() {
		tempDirUrl = serviceUrl + sysProp.getProperty("tempDir") + "/";
	}

	private static void setResultDirUrl() {
		resultDirUrl = serviceUrl + sysProp.getProperty("resultDir") + "/";

	}

	private static void setServiceUrl() {
		serviceUrl = sysProp.getProperty("protocol") + "://"
				+ sysProp.getProperty("host") + ":" + sysProp.getProperty("port") + "/"
				+ sysProp.getProperty("path") + "/";
	}

	private static void setResultDirPath() {
		// resultDirPath = System.getProperty("user.dir") +
		// sysProp.getProperty("resultDir") + "/";
		resultDirPath =
				sysProp.getProperty("workingDir") + sysProp.getProperty("path") + "/"
						+ sysProp.getProperty("resultDir") + "/";
	}

	private static void setTempDirPath() {
		// tempDirPath = System.getProperty("user.dir") +
		// sysProp.getProperty("tempDir") + "/";
		tempDirPath =
				sysProp.getProperty("workingDir") + sysProp.getProperty("path") + "/"
						+ sysProp.getProperty("tempDir") + "/";
	}

	public static void loadConfigurationFile() {
		sysProp = new Properties(defProp);
		try {
			InputStream propStream = new Configuration().getClass()
					.getResourceAsStream("/conf/deepzoomer.cfg");
			if (propStream == null) {
				throw new IOException(
						"Error loading configuration: /conf/deepzoomer.conf not found in classpath");
			} else {
				sysProp.load(propStream);
				System.out.println("loaded config file");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Method for initiate Logging System which include Logger Configuration from
	 * log4j.properties
	 * 
	 * @author Andres Quast
	 */
	public static void initLog() {
		try {
			PropertyConfigurator.configure(new Configuration().readLogProperties());

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * <p>
	 * <em>Title: </em>
	 * </p>
	 * <p>
	 * Description: Method loads log properties from file if accessible
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	private Properties readLogProperties() throws IOException {
		Properties logProps = new Properties();
		InputStream propStream =
				this.getClass().getResourceAsStream("/conf/log4j.properties");
		if (propStream == null) {
			throw new IOException("failed to load log4j.properties: file not found");
		} else {
			logProps.load(propStream);
			System.out.println("read log4j-configuration");
		}
		return logProps;
	}

	public static String getTempDirPath() {
		return tempDirPath;
	}

	public static String getResultDirPath() {
		return resultDirPath;
	}

	public static String getServiceUrl() {
		return serviceUrl;
	}

	public static String getTempDirUrl() {
		return tempDirUrl;
	}

	public static String getResultDirUrl() {
		return resultDirUrl;
	}

	public static String getWorkingDir() {
		return sysProp.getProperty("workingDir");
	}

	public static String getUserDir() {
		return sysProp.getProperty("userDir");
	}

}
