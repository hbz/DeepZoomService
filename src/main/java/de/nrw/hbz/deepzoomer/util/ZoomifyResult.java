/**
 * 
 */
package de.nrw.hbz.deepzoomer.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;
import de.nrw.hbz.deepzoomer.zoomify.IMAGEPROPERTIES;

/**
 * @author aquast
 *
 */
@XmlRootElement(name = "IMAGE_PROPERTIES", namespace = "")
public class ZoomifyResult {
	// Initiate Logger for TestRestClient
	private static Logger log = LogManager.getLogger(ZoomifyResult.class);

	// private String xmlns = "http://schemas.microsoft.com/deepzoom/2008";
	private String Url = null;
	private String Format = "zoomify";
	private String TileSize = null;
	private String Height = null;
	private String Width = null;

	public ZoomifyResult() {}
	
	public ZoomifyResult(String path) {
		String contextPath = "de.nrw.hbz.deepzoomer.zoomify";
		JAXBContext jCon = null;
		try {
			jCon = JAXBContext.newInstance(contextPath);
			Unmarshaller jConUn = jCon.createUnmarshaller();
			IMAGEPROPERTIES zmiObj = (IMAGEPROPERTIES) jConUn
					.unmarshal(new File(Configuration.properties.getProperty("tilesDir")+File.separator + path + "/ImageProperties.xml"));
			Url = Configuration.properties.getProperty("resultUrl") +"/"+ path;
			TileSize = zmiObj.getTILESIZE();
			Height = zmiObj.getHEIGHT();
			Width = zmiObj.getWIDTH();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}

	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @return the url
	 */
	@XmlElement(name = "Url")
	public String getUrl() {
		return Url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}

	/**
	 * @return the format
	 */
	@XmlElement(name = "Format")
	public String getFormat() {
		return Format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}

	/**
	 * @return the tileSize
	 */
	@XmlElement(name = "TileSize")
	public String getTileSize() {
		return TileSize;
	}

	/**
	 * @param tileSize
	 *            the tileSize to set
	 */
	public void setTileSize(String tileSize) {
		TileSize = tileSize;
	}

	/**
	 * @return the width
	 */
	@XmlElement(name = "Width")
	public String getWidth() {
		return Width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		Width = width;
	}

	/**
	 * @return the height
	 */
	@XmlElement(name = "Height")
	public String getHeight() {
		return Height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		Height = height;
	}

}
