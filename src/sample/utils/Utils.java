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

    public static final int FIELD_SIZE = 4;
    public static final int SCORE2048 = 2048;
    public static final int CELL_FOUR = 4;
    public static final int CELL_TWO = 2;

    //Создаем наши клетки для значений
    public static Label[][] createButtons(GridPane mainGrid) {
        Label[][] labels = new Label[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                labels[i][j] = new Label();
                //Позиционируем текст на клетке по центру
                labels[i][j].setAlignment(Pos.CENTER);
                //Ставим цвет нашего текста
                labels[i][j].setStyle("-fx-text-fill: #black;");
                //Ставим размеры клеток(Высота, ширина и размер текста)
                labels[i][j].setPrefHeight(115.0);
                labels[i][j].setPrefWidth(115.0);
                labels[i][j].setFont(new Font(45));
                //Добавляем их на наш GridPane
                mainGrid.add(labels[i][j], j, i);
            }
        }
        return labels;
    }

    //Получаем цвет для заднего фона
    public static Background getColorForBackground(Color color) {
        //Возвращаем BackGround с цветом и укругленными боковыми сторонами и
        return new Background(new BackgroundFill(color, new CornerRadii(10.0), new Insets(0)));
    }

    //Открывает окно с одной кнопкой
    public static void displayDialog(final Stage stage, Button button, Label label) {
        Scene myDialogScene;
        VBox vBox = new VBox();
        //Ставим размеры для кнопки(Высота, ширина и текст)
        button.setMaxHeight(120);
        button.setMaxWidth(220);
        button.setFont(new Font(15));
        //Ставим размер текста
        label.setFont(new Font(20));
        //Добавляем кнопку и текст на Vbox
        vBox.getChildren().add(button);
        vBox.getChildren().add(label);
        //Позиционируем по центру
        vBox.setAlignment(Pos.CENTER);
        //Ставим расстояние между элементами vbox
        vBox.setSpacing(7);
        myDialogScene = new Scene(vBox);

        //Ставим размеры нашего окна(ширина и длина)
        stage.setWidth(300);
        stage.setHeight(150);
        //Ставим название нашего окна
        stage.setTitle("This is the end of the game");
        //Ставим возможность изменения размера окна на false
        stage.setResizable(false);
        //Ставим и запускаем наше окно
        stage.setScene(myDialogScene);
        stage.show();
    }

    //Открывает окно с двумя кнопками при нажатии на Esc
    public static void displayDialogs(final Stage stage, Button button1, Button button2, Label label) {
        Scene myDialogsScene;
        VBox vBox = new VBox();
        //Ставим размеры для 1 кнопки(Высота, ширина и текст)
        button1.setMaxHeight(120);
        button1.setMaxWidth(220);
        button1.setFont(new Font(15));
        //Ставим размеры для 2 кнопки(Высота, ширина и текст)
        button2.setMaxHeight(120);
        button2.setMaxWidth(220);
        button2.setFont(new Font(15));
        //Ставим размеры для текста
        label.setFont(new Font(20));
        //Добавляем кнопки и текст на Vbox
        vBox.getChildren().add(button1);
        vBox.getChildren().add(button2);
        vBox.getChildren().add(label);
        //Позиционируем по центру
        vBox.setAlignment(Pos.CENTER);
        //Ставим расстояние между элементами vbox
        vBox.setSpacing(7);
        myDialogsScene = new Scene(vBox);

        //Ставим размеры нашего окна(ширина и длина)
        stage.setWidth(480);
        stage.setHeight(250);
        //Ставим название нашего окна
        stage.setTitle("Attention!!!");
        //Ставим возможность изменения размера окна на false
        stage.setResizable(false);
        //Ставим и запускаем наше окно
        stage.setScene(myDialogsScene);
        stage.show();
    }
}
