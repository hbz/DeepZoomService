/**
 * 
 */
package de.nrw.hbz.deepzoomer.fileUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.exception.RestrictedDomainException;
import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;


/**
 * @author aquast
 *
 */
public class ImgSourceUrl {
		
	private static Logger log = Logger.getLogger(de.nrw.hbz.deepzoomer.fileUtil.ImgSourceUrl.class);
	private URL validateUrlFormat(String SourceUrl) throws MalformedURLException {
		String sourceUrl = SourceUrl;
		URL url = new URL(sourceUrl);
		return url;
	}
	
	private URL allowedDomain(String Url) throws RestrictedDomainException, MalformedURLException {
		URL acceptedUrl = null;
		URL url = validateUrlFormat(Url);
		
		if(Configuration.getDomainRestriction() != null && Configuration.getDomainRestriction().isEmpty() == false) {
			ArrayList<String> domainToCheck = getDomainArray();
			acceptedUrl = null; 
			for(int i=0; i < domainToCheck.size(); i++) {
				if (url.getHost().equals(domainToCheck.get(i))) {
					log.debug("found accepted Domain: " + url.getHost());
					acceptedUrl = url;
				}
			}
			if(acceptedUrl == null) {
				RestrictedDomainException e = new RestrictedDomainException();
				throw e;				
			}
			
			
		}
		return acceptedUrl;
	}
	
	private ArrayList<String> getDomainArray() {
		String[] domainsToCheck = Configuration.getDomainRestriction().split(",");
		ArrayList<String> acceptedDomains = new ArrayList<String>(Arrays.asList(domainsToCheck));
		for(String domain : acceptedDomains) {
			log.info("Domaine in Datei: " + domain);
		}
		return acceptedDomains;
	}
	
	public URL getSourceUrl(String Url) throws Exception {
		URL url = allowedDomain(Url);
		return url;
	}
	
}
