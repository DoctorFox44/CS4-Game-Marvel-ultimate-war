package view;

import java.util.ArrayList;

import engine.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import model.world.*;

public class AlertBox {
	public static void display(String msg) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Warning");
		window.setMinWidth(400);

		Label l = new Label();
		l.setText(msg);
		Button close = new Button("Okay");
		close.setOnAction(e -> window.close());

		VBox v = new VBox(10);
		v.getChildren().addAll(l, close);
		v.setAlignment(Pos.CENTER);

		Scene scene = new Scene(v);
		window.setScene(scene);
		window.showAndWait();
	}
}
