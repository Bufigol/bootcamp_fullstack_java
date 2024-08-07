
$(document).ready(function () {
  $("#Baires").click(function () {
    $("#detallesBaires").toggle();
  });
  $("#MachuPicchu").click(function () {
    $("#detallesMachuPicchu").toggle();
  });
  $("#Rio").click(function () {
    $("#detallesRio").toggle();
  });
})

$(document).ready(function () {
  $(".btn-close").click(function () {
    $(".detalles").hide();
  });
})