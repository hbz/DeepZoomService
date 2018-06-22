/**
 * 
 */
package de.nrw.hbz.deepzoomer.serviceImpl;

import org.apache.log4j.Logger;
import de.nrw.hbz.deepzoomer.fileUtil.FileUtil;
import de.nrw.hbz.deepzoomer.fileUtil.SourceValidator;
import de.nrw.hbz.deepzoomer.util.ZoomifyResult;

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

@Path("/getZoomify")
public class ZoomifyUrlService {
	// Initiate Logger for PilotRunner
	private static Logger log = Logger.getLogger(ZoomifyUrlService.class);
	
	/**
	 * Method to check if url given with parameter imageUrl matches the 
	 * domainRestriction in deepzoomer.cfg if one is set there
	 * CHECKME: return type is not set static here as i understand that it otherwise would be be a class variable? (Bjoern)
	 */
	private boolean validateUrl (String imageUrl) {
		SourceValidator sVal= new SourceValidator(imageUrl);
		boolean allowed = false;
		try {
			allowed = sVal.checkUrl();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			log.error("Domain not valid");
		}
		return allowed;
	}

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
		boolean allowed = validateUrl(imageUrl);
		if (allowed == true) {
			zmiRes = getZoomifyResult(imageUrl);
			return zmiRes;
		} else {
			return null;
		}
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

		boolean allowed = validateUrl(imageUrl);
		if (allowed == true) {
			return new JSONWithPadding(getZoomifyResult(imageUrl), callback);
		} else {
			return null;
		}
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
		if (new File(Configuration.getResultDirPath()+ pathName + "/ImageProperties.xml").isFile()){
			//nothing to do here
			log.debug("use cached DeepZoom-Images");
		} else {
			log.debug("create new DeepZoom-Images");
			log.info(FileUtil.saveUrlToFile(pathName, imageUrl));
			
			VipsRunner vips = new VipsRunner();
			vips.executeVips("--layout zoomify", pathName);
		}
		zmf =  new ZoomifyResult(pathName);
		return zmf;
	}
}
