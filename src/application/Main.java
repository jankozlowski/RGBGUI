package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class Main extends Application {
	File file;
	StringBuilder text = new StringBuilder();

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("RGB Tranlslator");

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Image");

			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"));

			Button openButton = new Button();
			openButton.setText("Open");
			Button startButton = new Button();
			startButton.setText("Start");

			Label label1 = new Label("Name:");
			TextField textField = new TextField();
			TextField textField2 = new TextField();

			final GridPane inputGridPane = new GridPane();
			HTMLEditor htmlEditor = new HTMLEditor();
			htmlEditor.setHtmlText(text.toString());
			htmlEditor.setPrefWidth(1020);

			openButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					file = fileChooser.showOpenDialog(primaryStage);
					if (file != null) {
						textField2.setText(file.getAbsolutePath());
					}

				}
			});
			startButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					BufferedImage img = null;
					try {
						img = ImageIO.read(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					int width = img.getWidth();
					int height = img.getHeight();

					String values[][] = new String[height][width];
					for (int y = 0; y < height; y++) {
						for (int x = 0; x < width; x++) {
							Color mycolor = new Color(img.getRGB(x, y));

							text.append("Red " + mycolor.getRed() + " Blue "
									+ mycolor.getBlue() + " Green "
									+ mycolor.getGreen() + "</br>");

							values[y][x] = mycolor.getRed() + ","
									+ mycolor.getBlue() + ","
									+ mycolor.getGreen();

						}
					}

					StringBuilder text2 = new StringBuilder();
					text2.append("<p style='font-size:8pt; white-space: nowrap'> void "
							+ textField.getText()
							+ " (){"
							+ "</br>"
							+ "int width = "
							+ width
							+ "</br>"
							+ "int height = " + height + "</br>" +

							"const uint32_t colors[width][height]={</br>");
					for (int y = 0; y < height; y++) {
						text2.append("{");
						for (int x = 0; x < width; x++) {
							if (x != width - 1) {
								text2.append("strip.Color(" + values[y][x]
										+ "),");
							} else {
								text2.append("strip.Color(" + values[y][x]
										+ ")");
							}
						}
						text2.append("}</br>");
					}
					text2.append("};");

					String function = "</br></br>for(j=0; j&lt;width; j++){<br/>"
							+ "&emsp;&emsp;&emsp;for(i=0; i&lt;height; i++) {</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;strip.setPixelColor(i, colors[j][i]);</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;strip.show();</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;delay(1);</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;for(k=0; k&lt;width; k++) strip.setPixelColor(k, 0);</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;strip.show();</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;delay(1);</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;}</br>"
							+ "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;delay(1);</br>"
							+ "&emsp;&emsp;&emsp;}</br>}</br>}";

					text2.append(function);

					htmlEditor.setHtmlText(text2.toString());

				}
			});

			startButton.setMaxWidth(Double.MAX_VALUE);

			GridPane.setConstraints(openButton, 0, 2);
			GridPane.setConstraints(htmlEditor, 0, 0);
			GridPane.setColumnSpan(htmlEditor, 2);
			GridPane.setConstraints(label1, 0, 1);
			GridPane.setConstraints(textField, 1, 1);
			GridPane.setConstraints(textField2, 1, 2);
			inputGridPane.setHgap(6);
			inputGridPane.setVgap(6);
			inputGridPane.getChildren().addAll(openButton, htmlEditor, label1,
					textField, textField2, startButton);

			final Pane rootGroup = new VBox(12);
			rootGroup.getChildren().addAll(inputGridPane, startButton);
			rootGroup.setPadding(new Insets(12, 12, 12, 12));
			rootGroup.setPrefWidth(1045);
			rootGroup.setPrefHeight(650);

			primaryStage.setScene(new Scene(rootGroup));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
