/**
 * FileUtil.java - This file is part of the DiPP Project by hbz
 * Library Service Center North Rhine Westfalia, Cologne 
 *
 * -----------------------------------------------------------------------------
 *
 * <p><b>License and Copyright: </b>The contents of this file are subject to the
 * D-FSL License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at <a href="http://www.dipp.nrw.de/dfsl/">http://www.dipp.nrw.de/dfsl/.</a></p>
 *
 * <p>Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.</p>
 *
 * <p>Portions created for the Fedora Repository System are Copyright &copy; 2002-2005
 * by The Rector and Visitors of the University of Virginia and Cornell
 * University. All rights reserved."</p>
 *
 * -----------------------------------------------------------------------------
 *
 */
package de.nrw.hbz.deepzoomer.fileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;

/**
 * Class filteUtil
 * 
 * <p>
 * <em>Title: </em>
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aquast, email creation date: 24.09.2009
 * @author jschnasse
 *
 */
public class FileUtil {
	/**
	 * <p>
	 * <em>Title: Create a temporary File from a file identified by URL</em>
	 * </p>
	 * <p>
	 * Description: Method creates a temporary file from a remote file addressed
	 * an by URL representing the orginal PDF, that should be converted
	 * </p>
	 * 
	 * @param fileName
	 * @param url
	 * @return
	 */
	public static String saveUrlToFile(String fileName, String url) {
		try {
			File inputFile = new File(Configuration.properties.getProperty("tempDir") + "/" + fileName);
			Files.copy(getInputStreamFromUrl(new URL(url)), Paths.get(inputFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
			return inputFile.getName();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static InputStream getInputStreamFromUrl(URL url) throws IOException {
		Map<String, String> httpHeaders = new HashMap<>();
		httpHeaders.put("User-Agent", "hbz deepzoom");
		return urlToInputStream(url, httpHeaders);
	}

	private static InputStream urlToInputStream(URL url, Map<String, String> args) {
		HttpURLConnection con = null;
		InputStream inputStream = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(15000);
			con.setReadTimeout(15000);
			if (args != null) {
				for (Entry<String, String> e : args.entrySet()) {
					con.setRequestProperty(e.getKey(), e.getValue());
				}
			}
			con.connect();
			int responseCode = con.getResponseCode();
			/*
			 * By default the connection will follow redirects. The following
			 * block is only entered if the implementation of HttpURLConnection
			 * does not perform the redirect. The exact behavior depends to the
			 * actual implementation (e.g. sun.net). !!! Attention: This block
			 * allows the connection to switch protocols (e.g. HTTP to HTTPS),
			 * which is <b>not</b> default behavior. See:
			 * https://stackoverflow.com/questions/1884230 for more info!!!
			 */
			if (responseCode < 400 && responseCode > 299) {
				String redirectUrl = con.getHeaderField("Location");
				try {
					URL newUrl = new URL(redirectUrl);
					return urlToInputStream(newUrl, args);
				} catch (MalformedURLException e) {
					URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
					return urlToInputStream(newUrl, args);
				}
			}
			/* !!!!! */

			inputStream = con.getInputStream();
			return inputStream;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
