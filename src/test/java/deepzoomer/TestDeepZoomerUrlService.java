/**
 * 
 */
package deepzoomer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.serviceImpl.DeepZoomerUrlService;
import de.nrw.hbz.deepzoomer.serviceImpl.Globals;
import de.nrw.hbz.deepzoomer.util.DziResult;

/**
 * @author aquast
 *
 */
public class TestDeepZoomerUrlService {

	// Initialize logger object
	private static Logger log = Logger.getLogger(TestDeepZoomerUrlService.class);

	@Test
	public void TestGetDzi() throws JsonProcessingException, IOException {
		log.info("Create " + Globals.conf.getResultDirPath());
		new File(Globals.conf.getResultDirPath()).mkdirs();
		log.info("Create " + Globals.conf.getTempDirPath());
		new File(Globals.conf.getTempDirPath()).mkdirs();
		String fileName = "sagrada_familia.png";
		File testFile = new File(Thread.currentThread().getContextClassLoader()
				.getResource("sagrada_familia.png").getPath());
		// copy testfile into temp directory
		String url = "file://" + testFile.getAbsolutePath();

		copyTestFile(fileName, url);
		// Call DeepZoomerUrlService
		DeepZoomerUrlService dziS = new DeepZoomerUrlService();
		DziResult dziContent = dziS.getDZI(url);

		String expectedJsonString =
				"{\"format\":\"jpeg\",\"tileSize\":\"256\",\"overlap\":\"1\",\"url\":\""
						+ Globals.conf.getResultDirUrl() + "/"
						+ Globals.createFileName(testFile.getAbsolutePath()) + "_files/"
						+ "\",\"size\":[{\"height\":\"4320\",\"width\":\"3240\"}]}";
		log.info("expected:\t" + expectedJsonString);
		log.info("actual:\t" + dziContent.toString());

		ObjectMapper mapper = new ObjectMapper();
		Assert.assertEquals(mapper.readTree(expectedJsonString),
				mapper.readTree(dziContent.toString()));
	}

	private void copyTestFile(String fileName, String url) {
		FileUtil.saveUrlToFile(fileName, url);
	}

	@After
	public void cleanUp() {
		new File(Globals.conf.workingDir).delete();
	}

}
