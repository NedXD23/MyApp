package ro.mta.se.lab;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.controller.controller;

import java.io.IOException;

public class Main extends Application {




    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader();

        try {

            loader.setLocation(this.getClass().getResource("/view/RaportVremeView.fxml"));
            loader.setController(new controller());
            primaryStage.setTitle("MyWeather");
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
