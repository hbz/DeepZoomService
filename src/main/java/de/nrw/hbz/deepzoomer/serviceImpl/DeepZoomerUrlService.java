/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.fileUtil.ImgSourceUrl;
import de.nrw.hbz.deepzoomer.util.DziResult;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.sun.jersey.api.json.JSONWithPadding;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * @author aquast
 *
 */

@Path("/getDzi")
public class DeepZoomerUrlService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(DeepZoomerUrlService.class);

	//  Jersey annotated Methods 	

	/**
	 * POST-implementation of getDzi-Service, for using RESTful please consider to use GET
	 *  
	 * @param imageUrl
	 * @return DziResult
	 */
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public DziResult getDZI(@QueryParam("imageUrl") String imageUrl){
		
		DziResult dziRes = null;
		dziRes = getDziResult(imageUrl);
		return dziRes;
	
	}
	
	/**
	 * GET-implementation of getDzi-Service. Returns OpenSeadragon liable dzi-Format 
	 * as JSONP
	 *  
	 * @param callback
	 * @param imageUrl
	 * @return
	 */
	@GET
	@Produces({"application/x-javascript", MediaType.APPLICATION_JSON})
	public JSONWithPadding getDZIJsonP(
			@QueryParam("callback") @DefaultValue("fn") String callback,
			@QueryParam("imageUrl") String imageUrl) {

		return new JSONWithPadding(getDziResult(imageUrl), callback);
	}
	
	
	/**
	 * Main method to call VipsRunner for MapTiles creation and call DziResult for 
	 * the dzi-response required by OpenSeadragon
	 *   
	 * @param imageUrl
	 * @return
	 */
	private DziResult getDziResult(String imageUrl){
		DziResult dzi = null;
		
		String fileName = imageUrl.replaceAll("\\W", "").replace("https", "")
				.replace("http", "").replace("file", "");
		if (new File(Configuration.getResultDirPath()+ fileName + ".dzi").isFile()){
			//nothing to do here
			log.info("use cached DeepZoom-Images");
		} else {
			log.info("create new DeepZoom-Images");

			try {
				FileUtil.saveUrlToFile(fileName, imageUrl);
				VipsRunner vips = new VipsRunner();
				vips.executeVips("", fileName);

			} catch (Exception e) {
				log.error(e.getMessage());
				//VipsRunner vips = new VipsRunner();
				//vips.executeVips("", "request-error.png");
				
			}
			
		}
		dzi =  new DziResult(fileName);
		return dzi;
	}
}
