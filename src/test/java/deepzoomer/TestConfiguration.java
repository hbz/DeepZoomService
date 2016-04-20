/**
 * TestConfiguration.java - This file is part of the DiPP Project by hbz
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
package deepzoomer;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.Globals;
import junit.framework.Assert;

/**
 * Class TestConfiguration
 * 
 * <p>
 * <em>Title: </em>
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aquast, email creation date: 05.08.2013
 *
 */
public class TestConfiguration {

	private static Logger log = Logger.getLogger(TestConfiguration.class);

	@Test
	public void testConfiguration() {
		String tempDir = "temp";
		String resultDir = "tilesCache";
		String workingDir = "/var/lib/tomcat7/webapps/";
		String protocol = "http";
		String host = "localhost";
		String port = "8080";
		String path = "deepzoom";
		Assert.assertEquals(tempDir, Globals.conf.tempDir);
		Assert.assertEquals(resultDir, Globals.conf.resultDir);
		Assert.assertEquals(workingDir, Globals.conf.workingDir);
		Assert.assertEquals(protocol, Globals.conf.protocol);
		Assert.assertEquals(host, Globals.conf.host);
		Assert.assertEquals(port, Globals.conf.port);
		Assert.assertEquals(path, Globals.conf.path);
		Assert.assertEquals(workingDir + "/" + path + "/" + tempDir, Globals.conf.getTempDirPath());
		Assert.assertEquals(workingDir + "/" + path + "/" + resultDir, Globals.conf.getResultDirPath());
		Assert.assertEquals(Globals.conf.getServiceUrl() + resultDir, Globals.conf.getResultDirUrl());
		Assert.assertEquals(protocol + ".//" + host + ":"+port+"/" + path, Globals.conf.getServiceUrl());
	}
}
