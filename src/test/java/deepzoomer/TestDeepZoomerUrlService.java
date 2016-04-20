/**
 * 
 */
package deepzoomer;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.serviceImpl.DeepZoomerUrlService;
import de.nrw.hbz.deepzoomer.serviceImpl.Globals;
import de.nrw.hbz.deepzoomer.serviceImpl.VipsRunner;
import de.nrw.hbz.deepzoomer.util.DziResult;

/**
 * @author aquast
 *
 */
public class TestDeepZoomerUrlService {

	// Initialize logger object 
	private static Logger log = Logger.getLogger(TestDeepZoomerUrlService.class);

	@Test
	public void TestGetDzi(){
		log.info("Create "+Globals.conf.getResultDirPath());
		new File(Globals.conf.getResultDirPath()).mkdirs();
		log.info("Create "+Globals.conf.getTempDirPath());
		new File(Globals.conf.getTempDirPath()).mkdirs();		
		String fileName = "sagrada_familia.png";
		File testFile = new File(Thread.currentThread().getContextClassLoader().getResource("sagrada_familia.png").getPath());
		// copy testfile into temp directory
		String url = "file://" + testFile.getAbsolutePath();
		fileName = testFile.getAbsolutePath().replaceAll("/", "");
		copyTestFile(fileName, url);
		// Call DeepZoomerUrlService
		DeepZoomerUrlService dziS = new DeepZoomerUrlService();
		DziResult dziContent = dziS.getDZI(url);
		log.info("dzi-Datei-Inhalt: " + dziContent);
	}
	
	
	private void copyTestFile(String fileName, String url){
		FileUtil.saveUrlToFile(fileName, url); 
	}
	
	@After 
	public void cleanUp(){
		new File(Globals.conf.workingDir).delete();
	}

}
