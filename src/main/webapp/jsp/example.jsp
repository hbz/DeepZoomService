<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.nrw.hbz.deepzoomer.serviceImpl.Configuration"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example Client for hbz DeepZoom-Service</title>
<link rel="stylesheet" type="text/css"
	href="<%=Configuration.getServiceUrl()%>css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=Configuration.getServiceUrl()%>css/jquery-ui.min.css" />
</head>
<body>
	<div class="header">
		<h1>Example Web Page using the RESTful hbz DeepZoom-Service</h1>
		<p>
			Service-Provider URL used by Example Page:<br /> <em><%=Configuration.getServiceUrl()%></em>
		</p>
	</div>

	<div class="menu">
	<h2>Example for using the Service from your server</h2>
		<div class="local">
		 <h3>Gaudis Sagrada Familia (Images by Andres Quast)</h3>
			<div>
				<table>
					<tr>
						<td align='center'><a class="dz_image_url"
							href="<%=Configuration.getServiceUrl()%>example_img/sagrada_portal.png">
								<img
								src="<%=Configuration.getServiceUrl()%>example_img/thumbs/sagrada_portal.png.jpg"
								width="140" height="105" alt="thumbs/sagrada_portal.png.jpg" />
						</a>
							<div>Northern Portal of Sagrada Familia in Barcelona</div>
							<div>(Original <a target="_blank" href="<%=Configuration.getServiceUrl()%>example_img/sagrada_portal.png" >image</a> has size of 15.6 MB)</div></td>
						<td align='center'><a class="dz_image_url"
							href="<%=Configuration.getServiceUrl()%>example_img/sagrada.jpg">
								<img
								src="<%=Configuration.getServiceUrl()%>example_img/thumbs/sagrada.jpg.jpg"
								width="140" height="105" alt="thumbs/sagrada_portal.JPG.jpg" />
						</a>
							<div>Sagrada Familia in Barcelona</div>
							<div>(Original <a target="_blank" href="<%=Configuration.getServiceUrl()%>example_img/sagrada.jpg" >image</a> has size of 4.7MB)</div></td>
					</tr>
				</table>
			</div>
		</div>

		<!-- <div class="remote">
			<h2>Example for using the Service form your Server</h2>
			<div>
				<ul>
					<li class="dz_image_url"
						img_url="<%=Configuration.getServiceUrl()%>example_img/sagrada_portal.png">
						Nordportal (?) der Sagrada Familia</li>
					<li class="dz_image_url"
						img_url="<%=Configuration.getServiceUrl()%>example_img/sagrada.jpg">
						Sagrada Familia</li>
					<li class="dz_image_url"
						img_url="<%=Configuration.getServiceUrl()%>example_img/sagrada.jpg">
						Sagrada Familia von Innen</li>
					<li class="dz_image_url"
						img_url="<%=Configuration.getServiceUrl()%>example_img/Pfingststrum.JPG">
						Pfingststurm in Bochum</li>
					<li class="dz_image_url"
						img_url="<%=Configuration.getServiceUrl()%>example_img/Bambusgarten.png">
						Bambusgarten in den Cevennen</li>
				</ul>
			</div>
		</div> -->

		<h2>Hands on!</h2>
		<div>
			<p>Create your own mashup by providing an remote Image Url</p>
			<form action="">
				<h3>Insert Image Url</h3>
				<input class="dz_image_url" type="url" size="30 name="
					image_url" value="<%=Configuration.getServiceUrl()%>example_img/sagrada_portal.png"></input>
				<input type="submit"></input>
			</form>
		</div>

	</div>

	<div class="viewer" id="openseadragon1"
		style="width: 800px; height: 600px; background: #ccc;"></div>

	<script
		src="<%=Configuration.getServiceUrl()%>js/openseadragon.min.js"
		type="text/javascript"></script>
	<script
		src="<%=Configuration.getServiceUrl()%>js/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script src="<%=Configuration.getServiceUrl()%>js/jquery-ui.min.js"
		type="text/javascript"></script>

	<script language="javascript" type="text/javascript"> 
$(document).ready(getImageUrl);

var serviceUrl = "<%=Configuration.getServiceUrl()%>api/getDzi?imageUrl=";
var callbackString = "&callback=?";
var imageUrl = "<%=Configuration.getServiceUrl()%>/opensd/ruhrinfrastruktur.png";
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

	
	$("li.dz_image_url").each(function(){
		$(this).attr("style", "cursor: pointer;");
		$(this).click(function(){
			imageUrl = $(this).attr("img_url"); 
			$("#openseadragon1").dialog("open");
			deepZoomService();
			return false;
		});
	});
	
	
	
	$("a.dz_image_url").each(function(){
		$(this).click(function(){
			imageUrl = $(this).attr("href");
			$("#openseadragon1").dialog("open");
			deepZoomService();
			return false;
		});

	});
	
	
	
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
          prefixUrl: "<%=Configuration.getServiceUrl()%>OSimages/",
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

		}
	</script>
</body>
</html>