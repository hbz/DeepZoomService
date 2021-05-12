/**
 * 
 */
package deepzoomer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.util.DziResult;

/**
 * @author aquast
 *
 */
public class TestDziResult {

	// Initiate Logger for TestRestClient
	private static Logger log = LogManager.getLogger(TestDziResult.class);
	
		@Test
		public void getDziResult(){
			Configuration.initLog();
			String testFileName = "src/test/resources/dzi/dzi.xml";
			DziResult dzi = new DziResult(testFileName);
			log.info(dzi.toString());
		}
	
	/**
	 * <p><em>Title: </em></p>
	 * <p>Description: </p>
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		//Configuration.initLog();
		TestDziResult dziResult= new TestDziResult();
		

	}


}
