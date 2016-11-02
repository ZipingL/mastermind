package project6;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import java.net.URL;

import javafx.geometry.*;

public class HighAlertWindow {
	
	static boolean input;

    public static boolean display(String alertTitle, String message, String buttonName1, String buttonName2) {
        Stage alert = new Stage();
    	javafx.scene.image.Image appImage = null;
    	URL imageURL = Gui.class.getResource("/Icon_Alert.png");
    	appImage = new Image(imageURL.toString());
        alert.getIcons().add(appImage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(alertTitle);
        alert.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        Button positive = new Button(buttonName1);
        Button negative = new Button(buttonName2 != null ? buttonName2 : "");
        
        positive.setOnAction(e -> {
        	input = true;
        	alert.close();
        });
        
        negative.setOnAction(e -> {
        	input = false;
        	alert.close();
        });

        VBox layout = new VBox(10);
        if(buttonName2 != null)
        layout.getChildren().addAll(label, positive, negative);
        else
        layout.getChildren().addAll(label, positive);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alert.setScene(scene);
        alert.showAndWait();
        
        return input;
    }

}
