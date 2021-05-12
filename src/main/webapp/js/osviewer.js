/** 
 * JQuery based DeepZoom-Viewer use RESTful DeepZoom Generator
 * Author: Andres Quast, quast@hbz-nrw.de
 */

$(document).ready(getImageUrl);

var serviceUrl = "http://localhost:8080/deepzoom/api/getDzi?imageUrl=";
var callbackString = "&callback=?";
var imageUrl = "http://localhost/opensd/Bambusgarten.png";
var viewer = null;

var tileSourcesFn;

function getImageUrl() {

	$('#openseadragon1').dialog({
		modal: true,
		autoOpen: false,
		height: ($(window).height() - 60),
		width: ($(window).width() - 60),
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});

}

$("li.dz_image_url").each(function() {
	$(this).attr("style", "cursor: pointer;");
	$(this).click(function() {
		imageUrl = $(this).html();
		alert(imageUrl);
	});
});

$("a.dz_image_url").each(function() {
	$(this).click(function() {
		imageUrl = $(this).attr("href");
		$("#openseadragon1").dialog("open");
		deepZoomService();
		return false;
	});

});

function deepZoomService() {

	/*	if (ImageUrl !== null){
			imageUrl = ImageUrl;
		}
	*/

	var url = serviceUrl + imageUrl + callbackString;




	$.getJSON(url, function(json) {
		tileSourcesFn = json;

		if (viewer) {
			viewer.destroy();
		}

		viewer = OpenSeadragon({
			id: "openseadragon1",
			prefixUrl: "../OSimages/",
			tileSources: {
				Image: {
					xmlns: "http://schemas.microsoft.com/deepzoom/2008",
					Url: tileSourcesFn.Url + "/",
					Format: tileSourcesFn.Format,
					Overlap: tileSourcesFn.Overlap,
					TileSize: tileSourcesFn.TileSize,
					Size: {
						Height: tileSourcesFn.Size.Height,
						Width: tileSourcesFn.Size.Width
					}

				}
			},
			showNavigator: "true",
		});

	});


$("input.dz_image_url").each(function() {
	$(this).parent().find("input[type='submit']").click(function() {
		imageUrl = $("input.dz_image_url").val();
		$("#openseadragon1").dialog("open");
		deepZoomService();
		return false;
	});

});





}


