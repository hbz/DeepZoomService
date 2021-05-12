function getDziName() {

  var viewer = openSeadragon({
    id: "openseadragon1",
    prefixUrl: "openseadragon-bin-2.0.0/images/",
    tileSources: [{ Image: { Url: "http://192.168.178.30/opensd/Grenzbaude2400_files/" } }],
    showNavigator: "true",
  });

  jQuery("li").each(function() {
    var dziName = jQuery(this).html();

    jQuery(this).click(function() {
      viewer.destroy();
      viewer = openSeadragon({
        id: "openseadragon1",
        prefixUrl: "openseadragon-bin-2.0.0/images/",
        tileSources: dziName + ".dzi",
        showNavigator: "true",
      });

    });
  });
}

jQuery(document).ready(getDziName());


