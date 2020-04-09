package org.kaal.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainFX extends Application {
	
	public Stage stage;


	public void start (Stage primaryStage) {
		
		this.stage = primaryStage;
		mainWindow();
		
	}
	
	
	public void mainWindow() {
		
		try {

			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("main.fxml"));
			root.setCursor(Cursor.NONE);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("style-avatar1.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Death");
			stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			stage.setFullScreen(true);
			stage.setAlwaysOnTop(true);
			stage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        
	}
	
	
    public static void main(String[] args) {

        launch(args);
    }


}
