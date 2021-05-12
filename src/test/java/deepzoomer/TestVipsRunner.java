/**
 * 
 */
package deepzoomer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.VipsRunner;

/**
 * @author aquast
 *
 */
public class TestVipsRunner {

	// Initialize logger object 
	private static Logger log = LogManager.getLogger(TestVipsRunner.class);

	@Test
	public void TestVips(){
		
		String paramString = "";
		String fileName = "sagrada_familia.png";
		File testFile = new File("src/test/resources/sagrada_familia.png");
		String from =  testFile.getAbsolutePath();
		copyTestFile(from,fileName);
		VipsRunner vips = new VipsRunner();
		vips.executeVips(paramString, fileName);
		log.info("VipsRunnerTest");
	}
	
	
	private void copyTestFile(String from, String to){
		try {
			Files.copy( new File(from).toPath(), new File(to).toPath(), StandardCopyOption.REPLACE_EXISTING );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
