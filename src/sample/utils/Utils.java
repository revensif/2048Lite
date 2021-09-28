package sample.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Utils {

    public static final int BOARD_SIZE = 4;
    public static final int SCORE2048 = 2048;
    public static final int CELL_FOUR = 4;
    public static final int CELL_TWO = 2;

    //Создаем наши клетки для значений
    public static Label[][] createButtons(GridPane mainGrid) {
        Label[][] labels = new Label[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                labels[i][j] = new Label();
                labels[i][j].setAlignment(Pos.CENTER);
                labels[i][j].setStyle("-fx-text-fill: #black;");
                labels[i][j].setMinWidth(20.0);
                labels[i][j].setPrefHeight(115.0);
                labels[i][j].setPrefWidth(115.0);
                labels[i][j].setFont(new Font(40));
                mainGrid.add(labels[i][j], j, i);
            }
        }
        return labels;
    }

    //Получаем цвет для заднего фона(кроме клеток)
    public static Background getColorForBackground(Color color) {
        return new Background(new BackgroundFill(color, new CornerRadii(5.0), new Insets(0)));
    }

    //Открывает окно с одной кнопкой
    public static void displayDialog(final Stage stage, Button button) {
        Scene myDialogScene;
        VBox vBox = new VBox();
        vBox.getChildren().add(button);
        button.setMaxHeight(100);
        button.setMaxWidth(200);
        vBox.setAlignment(Pos.CENTER);
        myDialogScene = new Scene(vBox);

        stage.setWidth(300);
        stage.setHeight(150);
        stage.centerOnScreen();
        stage.setTitle("This is the end of the game");
        stage.setResizable(false);
        stage.setScene(myDialogScene);
        stage.show();
    }

    //Открывает окно с двумя кнопками при нажатии на Escape
    public static void displayDialogsEscape(final Stage stage, Button button1, Button button2) {
        Scene myDialogsScene;
        VBox vBox = new VBox();
        vBox.getChildren().add(button1);
        vBox.getChildren().add(button2);
        vBox.setSpacing(5);
        button1.setMaxHeight(100);
        button1.setMaxWidth(200);
        button2.setMaxHeight(100);
        button2.setMaxWidth(200);
        vBox.setAlignment(Pos.CENTER);
        myDialogsScene = new Scene(vBox);

        stage.setWidth(300);
        stage.setHeight(150);
        stage.centerOnScreen();
        stage.setTitle("Are you sure you want to exit?");
        stage.setResizable(false);
        stage.setScene(myDialogsScene);
        stage.show();
    }

    //Открывает окно с двумя кнопками при победе
    public static void displayDialogs(final Stage stage, Button button1, Button button2) {
        Scene myDialogsScene;
        VBox vBox = new VBox();
        vBox.getChildren().add(button1);
        vBox.getChildren().add(button2);
        vBox.setSpacing(5);
        button1.setMaxHeight(100);
        button1.setMaxWidth(200);
        button2.setMaxHeight(100);
        button2.setMaxWidth(200);
        vBox.setAlignment(Pos.CENTER);
        myDialogsScene = new Scene(vBox);

        stage.setWidth(300);
        stage.setHeight(150);
        stage.centerOnScreen();
        stage.setTitle("You won!!!");
        stage.setResizable(false);
        stage.setScene(myDialogsScene);
        stage.show();
    }
}
