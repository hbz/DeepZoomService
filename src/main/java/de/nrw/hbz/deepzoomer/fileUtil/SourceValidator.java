/**
 * 
 */
package de.nrw.hbz.deepzoomer.fileUtil;

import java.net.MalformedURLException;
import java.net.URL;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;


/**
 * @author aquast
 *
 */
public class SourceValidator {

	private String domain = null;
	private String sourceUrl = null;
	private URL url = null; 
	
	public SourceValidator (String SourceUrl) {
		sourceUrl = SourceUrl;
	}
	
	private void validateUrlFormat() throws MalformedURLException {
		url = new URL(sourceUrl);
	}
	
	private boolean isAllowedDomain() {
		boolean allowed = false;
		if(Configuration.getDomainRestriction() != null && !Configuration.getDomainRestriction().isEmpty()) {
			if (url.getHost().equals(Configuration.getDomainRestriction())) {
				allowed = true;
			}
		} else {
			allowed = true;
		}
		return allowed;
	}
	
	public boolean checkUrl() throws MalformedURLException {
		
		validateUrlFormat();
		return isAllowedDomain();
	}
	
}
