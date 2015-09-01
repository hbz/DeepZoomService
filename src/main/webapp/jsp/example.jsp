<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.nrw.hbz.deepzoomer.serviceImpl.Configuration" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example Client for hbz DeepZoom-Service</title>
<link rel="stylesheet" type="text/css" href="../css/style.css"/>
<link rel="stylesheet" type="text/css" href="../css/jquery-ui.min.css"/>
</head>
<body>
<h1>Example Client for RESTful hbz DeepZoom-Service</h1>
<p>Service-Provider URLused by Example Page:<br/>
<em><%= Configuration.getServiceUrl() %></em></p>
<div class="menu">
    <!-- 
    <div>Bildauswahl:</div>
    <div>
      <ul>
		<li>
    		<a class="dz_image_url" href="http://phacops.spdns.de/opensd/ruhrinfrastruktur.tif" 
    		target="_blank">Ruhr-Infrastruktur</a>
		</li>
		<li class="dz_image_url">Misdroy2400</li>
		<li class="dz_image_url">Misdroy</li>
		<li class="dz_image_url">sagrada_familia_png</li>
      </ul>
    </div> -->
    <div>
    	<form action="GET">
    		<h3>Insert Image Url</h3>
    		<input class="dz_image_url" type="url" size="30 name="image_url" value="http://phacops.spdns.de/opensd/ruhrinfrastruktur.tif"></input>
    		<input type="submit"></input> 
    	</form>
    </div>
</div>

<div class="viewer" id="openseadragon1" style="width: 800px; height: 600px; background:#ccc;"></div>

<script src="../js/openseadragon.min.js"></script>
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.min.js"></script>
<script src="../js/osviewer.js"></script>

<script language="javascript" type="text/javascript"> 
$(document).ready(getImageUrl);

var serviceUrl = "<%= Configuration.getServiceUrl() %>api/getDzi?imageUrl=";
var callbackString = "&callback=?";
var imageUrl = "http://phacops.spdns.de/opensd/ruhrinfrastruktur.tif";
var viewer = null;

var tileSourcesFn

function getImageUrl(){
	
	$('#openseadragon1').dialog({
	      modal: true,
	      autoOpen: false,
	      height: ($(window).height() - 60),
	      width: ($(window).width() - 60),
	      buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
		
	};

	/*
	$("li.dz_image_url").each(function(){
		$(this).attr("style", "cursor: pointer;");
		$(this).click(function(){
			imageUrl = $(this).html(); 
			alert(imageUrl);
		});
	});
	
	$("a.dz_image_url").each(function(){
		$(this).click(function(){
			imageUrl = $(this).attr("href");

			deepZoomService();
			return false;
		});

	});
	*/

	$("input.dz_image_url").each(function(){
		$(this).parent().find("input[type='submit']").click(function(){
			imageUrl = $("input.dz_image_url").val();
			$("#openseadragon1").dialog("open");
			deepZoomService();
			return false;
		});

	});



function deepZoomService (){

/*	if (ImageUrl !== null){
		imageUrl = ImageUrl;
	}
*/
	
	var url = serviceUrl + imageUrl + callbackString;
	
	
	
	
	$.getJSON(url, function(json){
		tileSourcesFn = json;

		if(viewer){
        	viewer.destroy();
        }
        
		viewer = OpenSeadragon({
          id: "openseadragon1",
          prefixUrl: "../OSimages/",
          tileSources: {
        	  Image: {
        		  xmlns:    "http://schemas.microsoft.com/deepzoom/2008",
        		  Url: tileSourcesFn.Url + "/",
                  Format:   tileSourcesFn.Format, 
                  Overlap:  tileSourcesFn.Overlap, 
                  TileSize: tileSourcesFn.TileSize,
                  Size: {
                      Height: tileSourcesFn.Size.Height,
                      Width:  tileSourcesFn.Size.Width
                  }
        		  
        	  }
          },
          showNavigator: "true",
        });
        
    });

	
}
	

</script>
</body>
</html>