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

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Class filteUtil
 * 
 * <p><em>Title: </em></p>
 * <p>Description: </p>
 * 
 * @author aquast, email
 * creation date: 24.09.2009
 *
 */
public class FileUtil {

	// Initiate Logger for filteUtil
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	private static File inputFile = null;




	/**
	 * <p><em>Title: Create a temporary File from a file identified by URL</em></p>
	 * <p>Description: Method creates a temporary file from a remote file addressed 
	 * an by URL representing the orginal PDF, that should be converted</p>
	 * 
	 * @param fileName
	 * @param url
	 * @return 
	 */
	public static String saveUrlToFile(String fileName, String url) throws Exception {
		File inputFile = new File(Configuration.getTempDirPath() +"/" + fileName);
		log.debug(inputFile.getAbsolutePath());
		InputStream is = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		
		log.info(url);
		
		ImgSourceUrl isu = new ImgSourceUrl();
		URL inputDocument;
		inputDocument = isu.getSourceUrl(url);

		HttpURLConnection connection = (HttpURLConnection) inputDocument.openConnection();
        ConnectionManager.setConnectionProperties(connection);
		
		try {
			connection.connect();
			is = inputDocument.openStream();
			bis = new BufferedInputStream(is);

			fos = new FileOutputStream(inputFile);
			bos = new BufferedOutputStream(fos);
			int i = -1;
			while((i = bis.read()) != -1){
				bos.write(i);
			}
			bos.flush();
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(bos != null){
				try{
					bos.close();
				}catch(IOException ioExc){
					log.error(ioExc);
				}
			}
			if(fos != null){
				try{
					fos.close();
				}catch(IOException ioExc){
					log.error(ioExc);
				}
			}
			if(bis != null){
				try{
					bis.close();
				}catch(IOException ioExc){
					log.error(ioExc);
				}
			}
			if(is != null){
				try{
					is.close();
				}catch(IOException ioExc){
					log.error(ioExc);
				}
			}
			connection.disconnect();
		}
		return inputFile.getName();
	}
	

}
