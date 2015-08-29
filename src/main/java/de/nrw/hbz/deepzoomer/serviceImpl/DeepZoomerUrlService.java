/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.util.DziResult;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import java.io.File;
import java.io.IOException;



/**
 * @author aquast
 *
 */

@Path("/getDzi")
public class DeepZoomerUrlService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(DeepZoomerUrlService.class);

	//  Jersey annotated Methods 
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public DziResult getDZI(@QueryParam("imageUrl") String imageUrl){
		
		DziResult dziRes = null;

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
		
		dziRes = new DziResult(fileName);
		return dziRes;
	}
}
