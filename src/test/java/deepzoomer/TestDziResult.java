/**
 * 
 */
package deepzoomer;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.DeepZoomerUrlService;
import de.nrw.hbz.deepzoomer.serviceImpl.Globals;
import de.nrw.hbz.deepzoomer.util.DziResult;
import junit.framework.Assert;

/**
 * @author aquast
 *
 */
public class TestDziResult {
	// Initiate Logger for TestRestClient
	private static Logger log = Logger.getLogger(TestDziResult.class);

	@Test
	public void getDziResult() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("sagrada_familia.png");
		DeepZoomerUrlService service = new DeepZoomerUrlService();
		DziResult dzi = service.getDZI(url.toString());
		File dziFile = new File(Globals.conf.getResultDirPath() + "/"
				+ Globals.createFileName(url.getPath()) + ".dzi");
		File filesDir = new File(Globals.conf.getResultDirPath() + "/"
				+ Globals.createFileName(url.getPath()) + "_files");
		Assert.assertTrue(dziFile.exists());
		Assert.assertTrue(filesDir.exists());
	}

	@After
	public void cleanUp() {
		new File(Globals.conf.workingDir).delete();
	}
}
