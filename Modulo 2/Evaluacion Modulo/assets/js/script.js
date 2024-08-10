$(document).ready(function () {
  // Objeto para almacenar todas las tarjetas
  const cards = {};

  // Función para configurar la interacción de una tarjeta
  function setupCardInteraction(imgId, cardContainerId, closeButtonId) {
    const $img = $(`#${imgId}`);
    const $cardContainer = $(`#${cardContainerId}`);
    const $closeButton = $(`#${closeButtonId}`);

    // Almacena la referencia de la tarjeta
    cards[imgId] = $cardContainer;

    // Muestra la tarjeta cuando el ratón entra en la imagen
    $img.on("mouseenter", function () {
      // Cierra todas las otras tarjetas
      closeAllCardsExcept(imgId);

      const imgPosition = $img.offset();
      $cardContainer.css({
        display: "block",
        top: imgPosition.top + $img.height() + 10,
        left: imgPosition.left,
      });
    });

    // Oculta la tarjeta cuando se hace clic en el botón de cierre
    $closeButton.on("click", function () {
      $cardContainer.hide();
    });
  }

  // Función para cerrar todas las tarjetas excepto la especificada
  function closeAllCardsExcept(exceptImgId) {
    Object.keys(cards).forEach((imgId) => {
      if (imgId !== exceptImgId) {
        cards[imgId].hide();
      }
    });
  }

  // Configura la interacción para la tarjeta YOLO
  setupCardInteraction("YoLo", "yoloCardContainer", "closeYoloCard");

  // Configura la interacción para la tarjeta Pull Shark
  setupCardInteraction(
    "PullShark",
    "PullSharkCardContainer",
    "closePullSharkCard"
  );

  // Configura la interacción para la tarjeta Pair Extraordinaire
  setupCardInteraction(
    "PairExtraordinaire",
    "PairExtraordinaireCardContainer",
    "closePairExtraordinaireCard"
  );

  // Oculta todas las tarjetas si se hace clic fuera de ellas
  $(document).on("click", function (e) {
    if (!$(e.target).closest(".card-container, .achievement-img").length) {
      closeAllCardsExcept(null);
    }
  });
});

$(document).ready(function () {
  // Inicializa EmailJS con tu User ID de EmailJS
  emailjs.init("TU_USER_ID_DE_EMAILJS");

  $("#contactForm").on("submit", function (event) {
    event.preventDefault(); // Previene el envío normal del formulario

    // Obtiene los valores del formulario
    var name = $("#name").val();
    var email = $("#email").val();
    var message = $("#message").val();

    // Prepara los parámetros para el correo
    var templateParams = {
      from_name: name,
      from_email: email,
      message: message,
    };

    // Envía el correo usando EmailJS
    const TU_SERVICE_ID="service_gmail";
    const TU_TEMPLATE_ID="template_default";
    const TU_TEMPLATE_ID_CONFIRMACION="template_confirmacion";
    emailjs
      .send(TU_SERVICE_ID, TU_TEMPLATE_ID, templateParams)
      .then(function (response) {
        console.log(
          "Correo enviado exitosamente",
          response.status,
          response.text
        );
        alert(
          "Gracias por contactarnos. Te hemos enviado un correo de confirmación."
        );

        // Envía el correo de confirmación al usuario
        var confirmationParams = {
          to_email: email,
          to_name: name,
          message:
            "Hemos recibido tu mensaje: '" +
            message +
            "'. Nos pondremos en contacto contigo pronto.",
        };

        return emailjs.send(
          TU_SERVICE_ID,
          TU_TEMPLATE_ID_CONFIRMACION,
          confirmationParams
        );
      })
      .then(function (response) {
        console.log(
          "Correo de confirmación enviado",
          response.status,
          response.text
        );
        $("#contactForm")[0].reset(); // Limpia el formulario
      })
      .catch(function (error) {
        console.log("Error al enviar el correo", error);
        alert(
          "Hubo un error al enviar tu mensaje. Por favor, intenta de nuevo más tarde."
        );
      });
  });
});
