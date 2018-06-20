/**
 * 
 */
package deepzoomer;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
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
		String fileName = "sagrada_familia.png";
		File testFile = new File("src/test/resources/sagrada_familia.png");
		String url = "file://" + testFile.getAbsolutePath();
		copyTestFile(fileName, url);
		VipsRunner vips = new VipsRunner();
		vips.executeVips(paramString, fileName);
		log.info("VipsRunnerTest");
	}
	
	
	private void copyTestFile(String fileName, String url){
		FileUtil.saveUrlToFile(fileName, url); 

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestVipsRunner tVips = new TestVipsRunner();
		tVips.TestVips();

	}

}
