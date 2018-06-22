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
	 * GET-implementation of getDzi-Service. Returns OpenLayers liable zoomify-Format 
	 * as JSONP
	 *  
	 * @param callback
	 * @param imageUrl
	 * @return
	 */
	@GET
	@Produces({"application/x-javascript", MediaType.APPLICATION_JSON})
	public JSONWithPadding getZMFJsonP(
			@QueryParam("callback") @DefaultValue("fn") String callback,
			@QueryParam("imageUrl") String imageUrl) {

		return new JSONWithPadding(getZoomifyResult(imageUrl), callback);
	}
	
	
	/**
	 * Main method to call VipsRunner for MapTiles creation and call ZoomifyResult for 
	 * the zoomify-response required by OpenLayers
	 *   
	 * @param imageUrl
	 * @return
	 */
	private ZoomifyResult getZoomifyResult(String imageUrl){
		ZoomifyResult zmf = null;
		
		// test if domain is allowed
		boolean allowed = validateDomain(imageUrl); 
		if (allowed != true) {
			zmf = new ZoomifyResult();
			return zmf;
		}
		
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
	
	/**
	 * Method to check if imageUrl matches the domain set in
	 * domainRestriction in deepzoomer.cfg if any
	 * TODO: move me into SourceValidator
	 */
	private boolean validateDomain (String imageUrl) {
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
}
