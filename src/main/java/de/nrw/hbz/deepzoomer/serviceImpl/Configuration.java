/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author aquast
 *
 */
public class Configuration {

	public static Properties properties = new Properties();

	static {
		loadConfigurationFile();
		initLog();
	}

	public static void loadConfigurationFile() {
		try (InputStream propStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("conf/deepzoomer.cfg")) {
			properties.load(propStream);
			File tilesDir = new File(properties.getProperty("tilesDir"));
			File tempDir = new File(properties.getProperty("tempDir"));
			tilesDir.mkdirs();
			tempDir.mkdirs();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method for initiate Logging System which include Logger Configuration
	 * from log4j.properties
	 * 
	 * @author Andres Quast
	 */
	public static void initLog() {
		// PropertyConfigurator.configure(new Configuration().readLogProperties());
		
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
	private Properties readLogProperties() {
		try {
			Properties logProps = new Properties();
			InputStream propStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("conf/log4j.properties");
			logProps.load(propStream);
			System.out.println("read log4j-configuration");
			return logProps;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
