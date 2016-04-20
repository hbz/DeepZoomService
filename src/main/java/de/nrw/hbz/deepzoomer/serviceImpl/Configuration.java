/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;
import java.util.Properties;

/**
 * @author aquast
 *
 */
public class Configuration {
	public String tempDir;
	public String resultDir;
	public String workingDir;
	public String protocol;
	public String host;
	public String path;
	public String port;

	public Configuration() {
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/deepzoomer.cfg"));
			tempDir = props.getProperty("tempDir");
			resultDir = props.getProperty("resultDir");
			workingDir = props.getProperty("workingDir");
			protocol = props.getProperty("protocol");
			host = props.getProperty("host");
			path = props.getProperty("path");
			port = props.getProperty("port");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getTempDirPath() {
		return workingDir + "/" + path + "/" + resultDir;
	}

	public String getResultDirPath() {
		return workingDir + "/" + path + "/" + tempDir + "/";
	}

	public String getResultDirUrl() {
		return Globals.conf.getServiceUrl() + resultDir;
	}

	public String getServiceUrl() {
		return protocol + ".//" + host + ":"+port + "/" + path;
	}
}
