/**
 * 
 */
package deepzoomer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.util.ZoomifyResult;
import junit.framework.Assert;

/**
 * @author aquast
 *
 */
public class TestZoomifyResult {

	// Initiate Logger for TestRestClient
	private static Logger log = LogManager.getLogger(TestZoomifyResult.class);
	
		@Test
		public void getDziResult(){
			Configuration.initLog();
			String testPathName = "src/test/resources/zoomify";
			ZoomifyResult zmi = new ZoomifyResult(testPathName);
			log.info(zmi.toString());
			Assert.assertNotNull(zmi);
		}
}
