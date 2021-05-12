/**
 * 
 */
package de.nrw.hbz.deepzoomer.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import de.nrw.hbz.deepzoomer.dzi.Image;
import de.nrw.hbz.deepzoomer.serviceImpl.Configuration;

/**
 * @author aquast
 *
 */
@XmlRootElement(name = "Image", namespace = "http://schemas.microsoft.com/deepzoom/2008")
public class DziResult {

	// Initiate Logger for TestRestClient
	private static Logger log = LogManager.getLogger(DziResult.class);

	// private String xmlns = "http://schemas.microsoft.com/deepzoom/2008";
	private String Url = null;
	private String Format = null;
	private String Overlap = null;
	private String TileSize = null;
	ArrayList<Size> Size = new ArrayList<Size>();

	public DziResult() {

	}

	public DziResult(String parseFileName) {
		parseDzi(parseFileName);
	}

	private void parseDzi(String parseFileName) {

		String contextPath = "de.nrw.hbz.deepzoomer.dzi";
		JAXBContext jCon = null;
		try {
			jCon = JAXBContext.newInstance(contextPath);
			Unmarshaller jConUn = jCon.createUnmarshaller();
			String dir = Configuration.properties.getProperty("tilesDir");
			File file = new File(parseFileName + ".dzi");
			if(dir!=null && !dir.isEmpty()){
				file = new File(dir + File.separator + parseFileName + ".dzi");
			}
			Image dziObj = (Image) jConUn.unmarshal(file);
			Url = Configuration.properties.getProperty("resultUrl")+"/" + parseFileName + "_files/";
			Format = dziObj.getFormat();
			TileSize = dziObj.getTileSize();
			Overlap = dziObj.getOverlap();
			Size size = new Size();
			// dziObj.setSize(new de.nrw.hbz.deepzoomer.dzi.Image.Size());
			// dziObj.getSize().setWidth(1234);

			log.info("size: " + dziObj.getSize().getHeight());
			size.setHeight(dziObj.getSize().getHeight());
			size.setWidth(dziObj.getSize().getWidth());
			Size.add(size);

			// Size.put("Width", dziObj.getSize().getWidth().toString());
			// Size.put("Height", dziObj.getSize().getHeight().toString());

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
	 * @return the overlap
	 */
	@XmlElement(name = "Overlap")
	public String getOverlap() {
		return Overlap;
	}

	/**
	 * @param overlap
	 *            the overlap to set
	 */
	public void setOverlap(String overlap) {
		Overlap = overlap;
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
	 * @return the size
	 */
	@XmlElement(name = "Size")
	public ArrayList<Size> getSize() {
		return Size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(ArrayList<Size> size) {
		Size = size;
	}

	public static class Size {
		public Size() {

		}

		String Width = null;
		String Height = null;

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

}
