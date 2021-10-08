package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Подгружаем нашу форму
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample2.fxml")));
        //Ставим размер нашего окна(Ширина, Высота)
        Scene scene = new Scene(root, 600, 625);
        //Делаем фокус на сцене, для того чтобы она захватывала нажатия кнопок
        scene.getRoot().requestFocus();
        //Ставим название нашей игры
        primaryStage.setTitle("2048 Lite");
        //Меняем иконку у нашей игры
        primaryStage.getIcons().add(new Image("/sample/pictures/logo.png"));
        //Ставим возможность изменения размера окна на false
        primaryStage.setResizable(false);
        //Ставим и запускаем наше окно
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
