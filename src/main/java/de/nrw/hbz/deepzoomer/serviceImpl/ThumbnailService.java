/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.util.DziResult;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.sun.jersey.api.json.JSONWithPadding;

import java.io.File;
import java.io.IOException;



/**
 * @author aquast
 *
 */

@Path("/thumb")
public class ThumbnailService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(ThumbnailService.class);

	//  Jersey annotated Methods 	

	/**
	 * POST-implementation of getThumb-Service for using RESTful "create"
	 *  
	 * @param imageUrl
	 * @return response
	 */
	@POST
	@Produces("image/png")
	public StreamingOutput postThumb(
			@QueryParam("imageUrl") String imageUrl,
			@QueryParam("size") String thumbSize
			) {
		
		File thumb = createThumb(imageUrl, thumbSize);
		
		StreamingOutput response = null;
		return response;
	}
	
	/**
	 * GET-implementation of getThumb-Service
	 *  
	 * @param callback
	 * @param imageUrl
	 * @return
	 */
	@GET
	@Produces("image/png")
	public StreamingOutput getThumb(
			@QueryParam("imageUrl") String imageUrl,
			@QueryParam("thumbSize") String size) {

		return null;
	}
	
	
	/**
	 * Main method to call VipsRunner for MapTiles creation and call DziResult for 
	 * the dzi-response required by OpenSeadragon
	 *   
	 * @param imageUrl
	 * @return
	 */
	private File createThumb(String imageUrl, String size){
		File thumb = null;
		
		String fileName = imageUrl.replaceAll("\\W", "").replace("https", "")
				.replace("http", "").replace("file", "");
		log.info(fileName);
		if (new File(Configuration.getResultDirPath()+ fileName + "_thumb.png").isFile()){
			//nothing to do here
			log.debug("use cached thumbnail");
		} else {
			log.debug("create new thumbnail");
			log.info(FileUtil.saveUrlToFile(fileName, imageUrl));
			
			// create Param String
			StringBuffer sb = new StringBuffer();
			sb.append(checkSize(size));
			
			VipsRunner vips = new VipsRunner();
			vips.executeVips("", fileName);
		}
		thumb =  new File(fileName);
		return thumb;
	}
	
	private String checkSize(String size){

		String pSize = ""; 
		try{
			int iSize = Integer.parseInt(size);	    	
			pSize = Integer.toString(iSize);   
	    }catch(NumberFormatException nfe){
	    	log.error(nfe);
	    }		
		return pSize;
	}
}
