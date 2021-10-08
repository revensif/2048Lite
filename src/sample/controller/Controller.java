package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.GameContext;

import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import static sample.utils.Backgrounds.getBackgroundColor;
import static sample.utils.Utils.*;

public class Controller implements Initializable {

    public AnchorPane mainPane;
    public Label[][] cells;

    public GameContext gameContext;

    public final Random random = new Random();

    @FXML
    public GridPane mainGrid;

    @FXML
    public Label scoreLabel;

    //Когда происходит нажатие на кнопку, отрабатывает этот метод
    @FXML
    private void keyPressed(KeyEvent event) {
        int[][] prev = gameContext.getPlayingField();
        //Если нажимаем на кнопку R перезапускаем игру
        if (event.getCode() == KeyCode.R) {
            init();
        }
        //Если нажимаем на Esc вылезает окно с кнопками
        //Если нажимаем Continue просто продолжаем игру
        //Если нажимаем Exit программа закрывается
        if (event.getCode() == KeyCode.ESCAPE) {
            final Stage stage = new Stage();
            //Добавляем иконку для нашего окна
            stage.getIcons().add(new Image("/sample/pictures/attention.png"));
            //Ставим модальность нашего окна(Application, Window, None) в нашем случае Application
            //Это означает, что окно блокирует все события в другие окна
            stage.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("If you want to restart press R during the game");
            Button button1 = new Button("Continue");
            Button button2 = new Button("Exit");
            button1.setOnAction(e -> stage.close());
            button2.setOnAction(e -> System.exit(0));
            displayDialogs(stage, button1, button2, label);
        }

        //Когда мы нажимаем на какие-то стрелочки происходит действие
        if (event.getCode().isNavigationKey()) {
            switch (event.getCode()) {
                case DOWN -> gameContext.down();
                case UP -> gameContext.up();
                case RIGHT -> gameContext.right();
                case LEFT -> gameContext.left();
            }
            //Проверяем все клетки на наличие 2048
            gameContext.check2048();

            //Если еще имеются ходы(поле не поменялось по значениям в каждой клетке) и игра не закончена, генерируем новую клетку
            //Если клетка у стенок и мы нажимаем на кнопку, направленную в эту стенку, то клетка не добавляется
            if (!gameContext.checkEndOfTheGameLose() && !Arrays.deepEquals(prev, gameContext.getPlayingField()) && !gameContext.gameWin) {
                createCell();
            }
            //Обновляем наше поле после каждого хода
            refreshPlayingBoard();

            //Если мы победили, то открыем окно с кнопками Continue и Restart
            //При нажатии на Continue продолжаем игру до проигрыша
            //При нажатии на Restart просто перезапускаем игру с нуля
            //Показываемс текст с нашими очками
            if (gameContext.gameWin && gameContext.needToContinue) {
                final Stage stage = new Stage();
                //Добавляем иконку для нашего окна
                stage.getIcons().add(new Image("/sample/pictures/attention.png"));
                //Ставим модальность нашего окна(Application, Window, None) в нашем случае Application
                //Это означает, что окно блокирует все события в другие окна
                stage.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("Your final score is: " + gameContext.getScore());
                Button button1 = new Button("Continue");
                Button button2 = new Button("Restart game");
                button1.setOnAction(e -> {
                    gameContext.needToContinue = false;
                    gameContext.gameWin = false;
                    stage.close();
                });
                button2.setOnAction(e -> {
                    stage.close();
                    init();
                });
                displayDialogs(stage, button1, button2, label);
            }

        }
    }

    //Создаем новую клетку
    public void createCell() {
        //Если сгенерировали номер на существующей ячейке, пробуем ещё раз пока не создатся в свободной клетке
        //Создаём клетку на рандомном месте, с вероятностью 90% создаётся со значением 2
        if (!gameContext.createCellAt(
                random.nextInt(FIELD_SIZE),
                random.nextInt(FIELD_SIZE),
                Math.random() < 0.90 ? 512 : 512)
        )
            createCell();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ставим цвет фона(вокруг клеток)
        mainGrid.setBackground(getColorForBackground(Color.rgb(187, 173, 160)));
        //Отступы клеток от сторон gridPane(Сверху, справа, снизу, слева)
        mainGrid.setPadding(new Insets(10, 0, 10, 20));
        //Создаем клетки для наших наших значений
        cells = createButtons(mainGrid);
        init();
    }

    //Инициализируем игру(начинаем)
    private void init() {
        gameContext = new GameContext();
        refreshPlayingBoard();
    }

    //Обновляем наше поле после каждого хода
    private void refreshPlayingBoard() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                int cell = gameContext.getCell(i, j);
                //Ставим текст для каждой клетки(Если клетка > 0, то ставим текст значения клетки, если 0, то пусто)
                cells[i][j].setText(cell > 0 ? String.valueOf(cell) : "");
                //Ставим цвет клетки(Если клетка > 0, то ставим значение которое записано в BackGrounds, если 0, то значение по умолчанию)
                cells[i][j].setBackground(cell > 0 ? getColorForBackground(getBackgroundColor(cell)) : getColorForBackground(getBackgroundColor(0)));
            }
        //Проверяем на окончание игры в результате проигрыша
        if (gameContext.checkEndOfTheGameLose()) {
            //Создаем новое окно если проиграли
            final Stage stage = new Stage();
            //Добавляем иконку для нашего окна
            stage.getIcons().add(new Image("/sample/pictures/attention.png"));
            //Ставим модальность нашего окна(Application, Window, None) в нашем случае Application
            //Это означает, что окно блокирует все события в другие окна
            stage.initModality(Modality.APPLICATION_MODAL);
            //Текст про выигранные очки
            Label label = new Label("Your final score is: " + gameContext.getScore());
            //Кнопка которая перезапускает нам игру
            Button button = new Button("Restart game");
            button.setOnAction(e -> {
                stage.close();
                init();
            });
            //Мы открываем окно с кнопкой и текстом
            displayDialog(stage, button, label);
        }
        //Меняем текст для наших очков
        scoreLabel.setText(String.valueOf(gameContext.getScore()));
    }
}
