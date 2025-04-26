package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

public class ControllerEpub {

    private static final Logger logger = LoggerFactory.getLogger(ControllerEpub.class);

    @FXML
    private VBox rootVBox; 
    @FXML
    private WebView webViewEPUB;
    @FXML
    private ImageView imageViewEPUB; 

    private List<Resource> chapters;
    private int currentChapterIndex = 0;
    private Map<Integer, String> highlightedContent = new HashMap<>();
    private String currentHtmlContent;
    private Stage stage;

    @FXML
    public void initialize() {
        imageViewEPUB.setPreserveRatio(true);
        imageViewEPUB.setFitWidth(500);
        imageViewEPUB.setFitHeight(400);
               
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setResizable(false);
    }

    @FXML
    public void abrirEPUB() {
    	  logger.info("Intentando abrir un archivo EPUB desde Supabase...");

    	    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
    	    retrofit2.Call<String> call = apiService.obtenerUrlFirmada("la_celestina.epub");

    	    call.enqueue(new retrofit2.Callback<String>() {
    	        @Override
    	        public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
    	            if (response.isSuccessful() && response.body() != null) {
    	                String urlFirmada = response.body();
    	                logger.info("URL firmada obtenida: " + urlFirmada);

    	                try {
    	                    // Descargar el archivo EPUB temporalmente
    	                    java.net.URL url = new java.net.URL(urlFirmada);
    	                    java.io.InputStream inputStream = url.openStream();
    	                    File tempFile = File.createTempFile("epub_temp", ".epub");
    	                    FileOutputStream outputStream = new FileOutputStream(tempFile);

    	                    byte[] buffer = new byte[4096];
    	                    int bytesRead;
    	                    while ((bytesRead = inputStream.read(buffer)) != -1) {
    	                        outputStream.write(buffer, 0, bytesRead);
    	                    }
    	                    inputStream.close();
    	                    outputStream.close();

    	                    // Abrir el EPUB descargado
    	                    Book book = (new EpubReader()).readEpub(new FileInputStream(tempFile));
    	                    logger.info("EPUB cargado correctamente desde Supabase.");

    	                    Resource coverResource = book.getCoverImage();
    	                    if (coverResource != null) {
    	                        byte[] coverData = coverResource.getData();
    	                        File tempCover = File.createTempFile("cover_temp", ".jpg");
    	                        try (FileOutputStream fos = new FileOutputStream(tempCover)) {
    	                            fos.write(coverData);
    	                        }
    	                        Image coverImage = new Image(tempCover.toURI().toString());
    	                        Platform.runLater(() -> {
    	                            imageViewEPUB.setImage(coverImage);
    	                            imageViewEPUB.setVisible(true);
    	                            webViewEPUB.setVisible(false);
    	                        });
    	                        logger.info("✅ Imagen de portada mostrada correctamente.");
    	                    } else {
    	                        logger.warn("⚠️ No se encontró imagen de portada en el EPUB.");
    	                    }

    	                    chapters = book.getSpine().getSpineReferences().stream()
    	                            .map(ref -> ref.getResource())
    	                            .toList();

    	                    if (!chapters.isEmpty()) {
    	                        currentChapterIndex = 0;
    	                    } else {
    	                        logger.warn("⚠️ No se encontraron capítulos en el EPUB.");
    	                    }

    	                } catch (Exception e) {
    	                    logger.error("Error al descargar o abrir el EPUB", e);
    	                    mostrarError("Error al abrir el archivo EPUB", e.getMessage());
    	                }
    	            } else {
    	                logger.error("Respuesta no válida al obtener URL firmada.");
    	                mostrarError("Error al obtener archivo", "No se pudo obtener el archivo EPUB.");
    	            }
    	        }

    	        @Override
    	        public void onFailure(retrofit2.Call<String> call, Throwable t) {
    	            logger.error("Fallo en la llamada Retrofit", t);
    	            mostrarError("Error de conexión", t.getMessage());
    	        }
    	    });
    }

    @FXML
    public void resaltarTexto() {
        ejecutarResaltado("yellow", ""); 
    }

    @FXML
    public void resaltarTextoConComentario() {
        Platform.runLater(() -> {
            try {
                if (webViewEPUB.getEngine().getDocument() == null) {
                    logger.warn("⚠️ WebView aún no está listo.");
                    return;
                }

                String selectedText = (String) webViewEPUB.getEngine().executeScript("window.getSelection().toString()");
                if (selectedText == null || selectedText.trim().isEmpty()) {
                    logger.warn("⚠️ No se ha seleccionado texto para resaltar.");
                    return;
                }

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Añadir Comentario");
                dialog.setHeaderText("Ingresa un comentario:");
                Optional<String> result = dialog.showAndWait();

                if (!result.isPresent() || result.get().trim().isEmpty()) {
                    logger.warn("⚠️ No se ingresó comentario. Cancelado.");
                    return;
                }
                String comentario = result.get().trim();

                String normalizedSelection = selectedText.trim().replaceAll("\\s+", " ");
                logger.info("🔹 Texto seleccionado: [{}]", normalizedSelection);
                logger.info("🔹 Comentario agregado: [{}]", comentario);

                webViewEPUB.getEngine().executeScript(
                    "try {" +
                    "    var selection = window.getSelection();" +
                    "    if (selection.rangeCount > 0) {" +
                    "        var range = selection.getRangeAt(0);" +
                    "        var newSpan = document.createElement('span');" +
                    "        newSpan.style.backgroundColor = 'orange';" +
                    "        newSpan.style.borderBottom = '1px dashed black';" +
                    "        newSpan.title = '" + comentario.replace("'", "\\'") + "';" +
                    "        newSpan.textContent = range.toString();" +
                    "        range.deleteContents();" +
                    "        range.insertNode(newSpan);" +
                    "        selection.removeAllRanges();" +
                    "    }" +
                    "} catch (e) {" +
                    "    console.error('⚠️ Error en resaltado:', e);" +
                    "}"
                );

                guardarResaltados(); // Guardar los cambios para este capítulo
                logger.info("✅ Texto resaltado con comentario correctamente.");

            } catch (Exception e) {
                logger.error("❌ Error al resaltar el texto con comentario", e);
            }
        });
    }
    


    private void ejecutarResaltado(String color, String comentario) {
        Platform.runLater(() -> {
            try {
                if (webViewEPUB.getEngine().getDocument() == null) {
                    logger.warn("⚠️ WebView aún no está listo.");
                    return;
                }

                String selectedText = (String) webViewEPUB.getEngine().executeScript("window.getSelection().toString()");
                if (selectedText == null || selectedText.trim().isEmpty()) {
                    logger.warn("⚠️ No se ha seleccionado texto para resaltar.");
                    return;
                }

                String normalizedSelection = selectedText.trim().replaceAll("\\s+", " ");
                logger.info("🔹 Texto seleccionado: [{}]", normalizedSelection);

                webViewEPUB.getEngine().executeScript(
                    "try {" +
                    "    var selection = window.getSelection();" +
                    "    if (selection.rangeCount > 0) {" +
                    "        var range = selection.getRangeAt(0);" +
                    "        var newSpan = document.createElement('span');" +
                    "        newSpan.style.backgroundColor = '" + color + "';" +
                    "        newSpan.style.color = 'black';" +
                    "        " + (comentario.isEmpty() ? "" : "newSpan.title = '" + comentario.replace("'", "\\'") + "';") +
                    "        newSpan.textContent = range.toString();" +
                    "        range.deleteContents();" +
                    "        range.insertNode(newSpan);" +
                    "        selection.removeAllRanges();" +
                    "    }" +
                    "} catch (e) {" +
                    "    console.error('⚠️ Error en resaltado:', e);" +
                    "}"
                );

                guardarResaltados();
                logger.info("✅ Texto resaltado correctamente.");

            } catch (Exception e) {
                logger.error("❌ Error al resaltar el texto", e);
            }
        });
    }

    private void guardarResaltados() {
        Platform.runLater(() -> {
            String currentHtml = (String) webViewEPUB.getEngine().executeScript("document.documentElement.outerHTML");
            highlightedContent.put(currentChapterIndex, currentHtml);
        });
    }

    private void restaurarResaltados() {
        if (highlightedContent.containsKey(currentChapterIndex)) {
            Platform.runLater(() -> {
                webViewEPUB.getEngine().loadContent(highlightedContent.get(currentChapterIndex));
            });
        }
    }

    @FXML
    public void paginaAnterior() {
        if (currentChapterIndex > 0) {
            currentChapterIndex--;
            mostrarCapitulo(currentChapterIndex);
        }
    }

    @FXML
    public void paginaSiguiente() {
        if (currentChapterIndex < chapters.size() - 1) {
            currentChapterIndex++;
            mostrarCapitulo(currentChapterIndex);
        }
    }

    private void mostrarCapitulo(int index) {
        if (index == 0) {
            return;
        }

        try {
            Resource chapter = chapters.get(index);
            InputStream is = chapter.getInputStream();
            byte[] byteArray = is.readAllBytes();
            currentHtmlContent = new String(byteArray, StandardCharsets.UTF_8);

            Platform.runLater(() -> {
                webViewEPUB.setVisible(true);
                imageViewEPUB.setVisible(false);
                webViewEPUB.getEngine().loadContent(currentHtmlContent);
                restaurarResaltados();
            });

        } catch (Exception e) {
            mostrarError("Error al cargar el capítulo", e.getMessage());
        }
    }

    @FXML
    public void cerrarEPUB() {
        Stage stage = (Stage) webViewEPUB.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}