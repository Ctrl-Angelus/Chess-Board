package app;

import app.scenes.MainScene;
import app.utils.AppImages;
import app.utils.AppParameters;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        MainScene mainScene = new MainScene();
        stage.setScene(mainScene.getScene());
        stage.getIcons().add(AppImages.ICON);
        stage.setTitle(AppParameters.TITLE);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}