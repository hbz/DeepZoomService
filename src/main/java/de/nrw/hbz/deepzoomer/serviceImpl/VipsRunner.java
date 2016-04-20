/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author aquast
 *
 */
public class VipsRunner {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(VipsRunner.class);

	private String exitStateStr = null;
	private String stoutStr = null;

	/**
	 * <p>
	 * <em>Title: </em>
	 * </p>
	 * <p>
	 * Description: Method creates the command line string with all parameters
	 * given. Then executes the shell command
	 * </p>
	 * 
	 * @param paramString
	 * @param fileName
	 */
	public void executeVips(String paramString, String fileName) {
		// call to execute VIPS

		// Complete execute String
		String programPath = new String("vips");
		String defaultParams = new String("dzsave ");
		String executeString = new String(
				programPath + " " + defaultParams + " " + Configuration.getTempDirPath()
						+ fileName + " " + Configuration.getResultDirPath() + fileName);

		log.info("The execute String: " + executeString);
		try {
			// Process proc = java.lang.Runtime.getRuntime().exec("echo " +
			// executeString);
			Process proc = java.lang.Runtime.getRuntime().exec(executeString);
			int exitState = proc.waitFor();
			InputStream stout = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stout);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer lineBuffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				lineBuffer.append(line + "\n");
			}
			log.debug("STOUT: " + lineBuffer.toString());
			log.info("Exit State: " + exitState);
			stoutStr = lineBuffer.toString();
			exitStateStr = Integer.toString(exitState);
		} catch (Exception Exc) {
			log.error(Exc);
		} finally {
			// unlink temp file
			if (new File(Configuration.getTempDirPath() + fileName).isFile()) {
				new File(Configuration.getTempDirPath() + fileName).delete();
			}

		}

		// TODO: das Ausführen von VIPS kann etwas dauern... was mache
		// ich um festzustellen, dass VIPS seine Arbeit beendet hat?
	}

	public String getStoutStr() {
		return stoutStr;
	}

	public String getExitStateStr() {
		return exitStateStr;
	}
}
