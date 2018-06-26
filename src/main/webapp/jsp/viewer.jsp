<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.nrw.hbz.deepzoomer.serviceImpl.Configuration"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Example Client for hbz DeepZoom-Service</title>
	<link rel="stylesheet" type="text/css"
		href="<%=Configuration.properties.getProperty("serviceUrl")%>/css/style.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=Configuration.properties.getProperty("serviceUrl")%>/css/jquery-ui.min.css" />
	<script
		src="<%=Configuration.properties.getProperty("serviceUrl")%>/js/openseadragon.min.js"
		type="text/javascript"></script>
	<script
		src="<%=Configuration.properties.getProperty("serviceUrl")%>/js/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script src="<%=Configuration.properties.getProperty("serviceUrl")%>/js/jquery-ui.min.js"
		type="text/javascript"></script>
	<script language="javascript" type="text/javascript"> 
	var serviceUrl = "<%=Configuration.properties.getProperty("serviceUrl")%>/api/getDzi?imageUrl=";
	var imageUrl="<%=request.getParameter("imageUrl")%>";
	var url = serviceUrl + imageUrl;
	$.getJSON(url, function(json){
		tileSourcesFn = json;

        
		viewer = OpenSeadragon({
          id: "openseadragon1",
          prefixUrl: "<%=Configuration.properties.getProperty("serviceUrl")%>/OSimages/",
									tileSources : {
										Image : {
											xmlns : "http://schemas.microsoft.com/deepzoom/2008",
											Url : tileSourcesFn.Url + "/",
											Format : tileSourcesFn.Format,
											Overlap : tileSourcesFn.Overlap,
											TileSize : tileSourcesFn.TileSize,
											Size : {
												Height : tileSourcesFn.Size.Height,
												Width : tileSourcesFn.Size.Width
											}

										}
									},
									showNavigator : "true",
								});

							});


	</script>
</head>
<body>
	<div class="viewer" id="openseadragon1"
		style="align:center;width: 800px; height: 600px; background: #000000;"></div>
</body>
</html>