/**
 * 
 */
package deepzoomer;

import java.io.File;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.serviceImpl.DeepZoomerUrlService;
import de.nrw.hbz.deepzoomer.util.DziResult;

/**
 * @author aquast
 *
 */
public class TestDeepZoomerUrlService {

	// Initialize logger object
	private static Logger log = Logger.getLogger(TestDeepZoomerUrlService.class);

	// @Test
	public void TestGetDzi() {

		String fileName = "sagrada_familia.png";
		File testFile = new File("src/test/resources/sagrada_familia.png");

		// copy testfile into temp directory
		String url = "file://" + testFile.getAbsolutePath();
		fileName = testFile.getAbsolutePath().replaceAll("/", "");

		copyTestFile(fileName, url);

		// Call DeepZoomerUrlService
		DeepZoomerUrlService dziS = new DeepZoomerUrlService();
		DziResult dziContent = dziS.getDZI(url);
		log.info("dzi-Datei-Inhalt: " + dziContent);

	}

	private void copyTestFile(String fileName, String url) {
		FileUtil.saveUrlToFile(fileName, url);
	}

}
