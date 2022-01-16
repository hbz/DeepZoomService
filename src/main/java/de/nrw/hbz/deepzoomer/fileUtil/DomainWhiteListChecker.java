/**
 * 
 */
package de.nrw.hbz.deepzoomer.fileUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import de.nrw.hbz.deepzoomer.exception.NotWhiteListedDomainException;
import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;


/**
 * @author aquast
 *
 */
public abstract class DomainWhiteListChecker {
		
	
	DomainWhiteListChecker() {
		existsWhiteList();
	}
	private static Logger log = Logger.getLogger(de.nrw.hbz.deepzoomer.fileUtil.DomainWhiteListChecker.class);
	
	private static ArrayList<String> domainWhiteList = null; 
		
	public static boolean existsWhiteList() {
		boolean check = false;
		if(getDomainWhiteList() != null) {
			check = true;
		};
		return check;
	}
	
	/**
	 * Checks if the domain of image source is in the white list of domains
	 * @param Url
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean isListed(String Url) throws MalformedURLException {
		URL url = new URL(Url);
		boolean test = false;
				
		for(int i=0; i < domainWhiteList.size(); i++) {
			if (url.getHost().equals(domainWhiteList.get(i))) {
				log.info("found Domain in white list: " + url.getHost());
				test = true;
			}
		}
			
		return test;
	}
	
	private static ArrayList<String> getDomainArray() {
		String[] domainsToCheck = Configuration.properties.getProperty("domainRestriction").split(",");
		ArrayList<String> acceptedDomains = new ArrayList<String>(Arrays.asList(domainsToCheck));
		for(String domain : acceptedDomains) {
			log.info("Domain found in conf file: " + domain);
		}
		return acceptedDomains;
	}
	
	/**
	 * Method for convenience throws Exception if the images source Domain is not a white listed domain. 
	 * Use isListed if you feel better with a boolean return  
	 **/
	public static void check(String Url) throws NotWhiteListedDomainException, MalformedURLException {
			if(isListed(Url) == false) {
				NotWhiteListedDomainException e = new NotWhiteListedDomainException();
				throw e;				
			}
	}
	
	public static ArrayList<String> getDomainWhiteList(){
		if(Configuration.properties.getProperty("domainRestriction") != null && Configuration.properties.getProperty("domainRestriction").isEmpty() == false) {
			domainWhiteList = getDomainArray();
		}
		return domainWhiteList;
	}
	
}
