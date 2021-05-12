/**
 * 
 */
package deepzoomer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.util.ZoomifyResult;

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
		}
	
	/**
	 * <p><em>Title: </em></p>
	 * <p>Description: </p>
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		//Configuration.initLog();
		TestZoomifyResult zmiResult= new TestZoomifyResult();
		

	}


}
