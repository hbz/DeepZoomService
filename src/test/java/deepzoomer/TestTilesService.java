package deepzoomer;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.serviceImpl.TilesService;

public class TestTilesService {

	@Test
	public void testSucceed() {
		java.nio.file.Path tilesDir = Paths.get(Configuration.properties.getProperty("tilesDir"));
		java.nio.file.Path p = Paths.get(tilesDir + File.separator + "0/0_0.jpeg");
		System.out.println(tilesDir +" | "+p);
		Assert.assertTrue(TilesService.isChild(p,tilesDir));
	}
	
	@Test
	public void testMustFail() {
		java.nio.file.Path tilesDir = Paths.get(Configuration.properties.getProperty("tilesDir"));
		java.nio.file.Path p = Paths.get(tilesDir + File.separator + "../../../etc/passwd");
		System.out.println(tilesDir +" | "+p);
		Assert.assertFalse(TilesService.isChild(p,tilesDir));
	}

}
