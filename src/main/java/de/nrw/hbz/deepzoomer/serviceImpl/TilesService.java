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
			java.nio.file.Path p = Paths.get(Configuration.properties.getProperty("tilesDir") + File.separator + path);
			return Response.ok(Files.readAllBytes(p)).header("content-type", "image/jpeg").build();
		} catch (IOException e) {
			return Response.status(406).build();
		}
	}
}
