package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainScene mainScene = new MainScene();
        stage.setScene(mainScene.getScene());
        stage.setTitle(appParameters.TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}