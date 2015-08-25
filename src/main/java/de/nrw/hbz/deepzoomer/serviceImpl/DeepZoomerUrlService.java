/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.File;


/**
 * @author aquast
 *
 */

@Path("/api")
public class DeepZoomerUrlService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(DeepZoomerUrlService.class);

	//  Jersey annotated Methods 
	
	@Path("/getDzi")
	@GET
	@Produces("text/plain")
	public String getDZI(@QueryParam("imageUrl") String imageUrl){
		
		String dziFileString = null;
		String fileName = imageUrl.replaceAll("/", "").replace("http:", "")
				.replace("file:", "");
		log.info(fileName);
		if (new File(Configuration.getResultDirPath()+ fileName + ".dzi").isFile()){
			//nothing to do here
			log.debug("use cached DeepZoom-Images");
		} else {
			log.debug("create new DeepZoom-Images");
			log.info(FileUtil.saveUrlToFile(fileName, imageUrl));
			
			VipsRunner vips = new VipsRunner();
			vips.executeVips("", fileName);
		}
			
		dziFileString = FileUtil.loadFileIntoString(new File(Configuration.getResultDirPath()+ fileName + ".dzi"));				
		return dziFileString;
	}
}
