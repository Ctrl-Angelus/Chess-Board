package app;

import app.scenes.MainScene;
import app.utils.appParameters;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainScene mainScene = new MainScene();
        stage.setScene(mainScene.getScene());
        stage.getIcons().add(appParameters.ICON);
        stage.setTitle(appParameters.TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}