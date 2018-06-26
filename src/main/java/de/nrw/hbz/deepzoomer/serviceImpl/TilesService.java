package de.nrw.hbz.deepzoomer.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/tiles")
public class TilesService {
	private static Logger log = Logger.getLogger(TilesService.class);

	@GET
	@Path("{path : .+}")
	@Produces("image/jpeg")
	public Response get(@PathParam("path") String path) {
		try {
			java.nio.file.Path tilesDir = Paths.get(Configuration.properties.getProperty("tilesDir"));
			java.nio.file.Path p = Paths.get(tilesDir + File.separator + path);
			if (isChild(p, tilesDir)) {
				return Response.ok(Files.readAllBytes(p)).header("content-type", "image/jpeg").build();
			}
			log.warn("Forbidden path traversal. {IP} tries to access location outside "+tilesDir+".");
		} catch (IOException e) {
			log.warn("", e);
		}
		return Response.status(406).build();
	}

	public static boolean isChild(java.nio.file.Path maybeChild, java.nio.file.Path possibleParent) {
		try {
			return maybeChild.toFile().getCanonicalPath().startsWith(possibleParent.toFile().getCanonicalPath());
		} catch (Exception e) {
			return false;
		}
	}
}
