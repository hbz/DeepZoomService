/**
 * 
 */
package deepzoomer;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.serviceImpl.Globals;
import de.nrw.hbz.deepzoomer.serviceImpl.VipsRunner;

/**
 * @author aquast
 *
 */
public class TestVipsRunner {
	// Initialize logger object 
	private static Logger log = Logger.getLogger(TestVipsRunner.class);

	@Test
	public void TestVips(){
		String paramString = "";
		URL url=Thread.currentThread().getContextClassLoader().getResource("sagrada_familia.png");
		String fileName = Globals.createFileName(url.getPath());
		log.info("Filename: "+fileName);
		FileUtil.saveUrlToFile(fileName, url.toString()); 
		VipsRunner vips = new VipsRunner();
		vips.executeVips(paramString, fileName);
	}
	@After 
	public void cleanUp(){
		new File(Globals.conf.workingDir).delete();
	}
}
