import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.*;

public class ConfirmationBox {

	static Stage stage; 
	static boolean btnYesClicked;
	public static boolean show(String message,String textYes, String textNo) {

		btnYesClicked = false;

		stage = new Stage(); 
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setMinWidth(350);

		Label lbl = new Label(); 
		lbl.setText(message);

		Button btnYes = new Button(); 
		btnYes.setText(textYes);
		btnYes.setOnAction(e -> btnYes_Clicked() );

		Button btnNo = new Button(); 
		btnNo.setText(textNo);
		btnNo.setOnAction(e -> btnNo_Clicked() );

		HBox paneBtn = new HBox(20); 
		paneBtn.getChildren().addAll(btnYes, btnNo);
		paneBtn.setAlignment(Pos.CENTER);

		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl, paneBtn);
		pane.setAlignment(Pos.CENTER);

		Scene scene = new Scene(pane,300,150); 
		scene.getStylesheets().add("Sample.css");
		//scene.setStyle("-fx-background-color: #ffcd59");
		stage.setScene(scene);
		stage.showAndWait();

		return btnYesClicked; 
	}

	private static void btnYes_Clicked() {
		stage.close();
		btnYesClicked = true;
	}

	private static void btnNo_Clicked() {
		stage.close();
		btnYesClicked = false;
	}
}