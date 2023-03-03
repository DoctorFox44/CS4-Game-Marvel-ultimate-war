package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndGameBox {
	public static void display(String msg,Stage w) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Warning");
		window.setMinWidth(400);

		Label l = new Label();
		l.setText(msg);
		Button close = new Button("Yay");
		close.setOnAction(e -> window.close());

		VBox v = new VBox(10);
		v.getChildren().addAll(l, close);
		v.setAlignment(Pos.CENTER);

		Scene scene = new Scene(v);
		window.setScene(scene);
		window.showAndWait();
		w.close();
	}
}
