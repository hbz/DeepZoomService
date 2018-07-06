/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import java.io.File;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.json.JSONWithPadding;

import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.util.ZoomifyResult;



/**
 * @author aquast
 *
 */

@Path("/getZoomify")
public class ZoomifyUrlService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(ZoomifyUrlService.class);

	//  Jersey annotated Methods 	

	/**
	 * POST-implementation of getDzi-Service, for using RESTful please consider to use GET
	 *  
	 * @param imageUrl
	 * @return DziResult
	 */
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	public ZoomifyResult getZmf(@QueryParam("imageUrl") String imageUrl){
		
		ZoomifyResult zmiRes = null;		
		zmiRes = getZoomifyResult(imageUrl);
		return zmiRes;
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

		return new JSONWithPadding(getZoomifyResult(imageUrl), callback);
	}
	
	
	/**
	 * Main method to call VipsRunner for MapTiles creation and call DziResult for 
	 * the dzi-response required by OpenSeadragon
	 *   
	 * @param imageUrl
	 * @return
	 */
	private ZoomifyResult getZoomifyResult(String imageUrl){
		ZoomifyResult zmf = null;
		
		String pathName = imageUrl.replaceAll("\\W", "").replace("https", "")
				.replace("http", "").replace("file", "");
		log.info(pathName);
		if (new File(Configuration.properties.getProperty("tilesDir")+File.separator+ pathName + "/ImageProperties.xml").isFile()){
			//nothing to do here
			log.debug("use cached DeepZoom-Images");
		} else {
			String resultPathName = FileUtil.saveUrlToFile(pathName, imageUrl);
			if(resultPathName.equals(pathName)) {
				FileUtil.saveUrlToFile(pathName, imageUrl);
				VipsRunner vips = new VipsRunner();
				vips.executeVips("--layout zoomify", pathName);
			} else {
				// provide a default error image if something fails here
				getZoomifyResult(Configuration.properties.getProperty("serviceUrl") + "/" + resultPathName);
				pathName = (Configuration.properties.getProperty("serviceUrl") + "/" + resultPathName)
						.replaceAll("\\W", "").replace("https", "").replace("http", "").replace("file", "");
			}
				
		}
		zmf =  new ZoomifyResult(pathName);
		return zmf;
	}
}
