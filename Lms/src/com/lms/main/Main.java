package com.lms.main;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	static Scene scene;
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	File f = new File("/home/dasu/eclipse-workspacen/Library-Manamgement-System/Lms/src/com/lms/styles/styles.css");
    	    	
    	System.out.println(getClass().getResource("/com/lms/view/LibraryHome.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/com/lms/view/LibraryHome.fxml"));

        scene = new Scene(root);
        
        scene.getStylesheets().clear();
    	scene.getStylesheets().add("file:///" + f);

        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void changePage(String fxml) throws IOException {
    	scene.setRoot(loadFXML(fxml));
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/lms/view/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
