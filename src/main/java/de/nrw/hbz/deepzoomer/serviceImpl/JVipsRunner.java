package de.nrw.hbz.deepzoomer.serviceImpl;

import com.criteo.vips.Vips;
import com.criteo.vips.VipsImage;
import com.criteo.vips.VipsContext;
import com.criteo.vips.VipsException;


public class JVipsRunner {
	
	public String  exampleImageName = "testBild";
	public int width = 200;
	public int height = 400;
	public boolean scale = true;
	
	public void createRunner () {
		try {
			VipsImage vips = new VipsImage(exampleImageName);
			vips.resize(width, height, scale);
			
			vips.close();
		} catch (Exception e) {
			
		} finally {
			
		}
		
	}

}
