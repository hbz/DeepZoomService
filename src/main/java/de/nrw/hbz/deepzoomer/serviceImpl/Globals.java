package de.nrw.hbz.deepzoomer.serviceImpl;

public class Globals {

	public static Configuration conf = new Configuration();

	public static String createFileName(String string) {
		return string.replaceAll("\\W", "").replace("https", "").replace("http", "")
				.replace("file", "");
	}
}
