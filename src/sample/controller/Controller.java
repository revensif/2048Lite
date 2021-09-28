package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.GameContext;

import java.net.URL;
import java.util.*;

import static sample.utils.Backgrounds.getBackgroundColor;
import static sample.utils.Utils.*;

public class Controller implements Initializable {

    public AnchorPane mainPane;
    private Label[][] cells;

    private GameContext gameContext;

    private final Random random = new Random();

    @FXML
    public GridPane mainGrid;

    @FXML
    public Label scoreLabel;

    //Когда происходит нажатие на кнопку, отрабатывает этот метод
    @FXML
    private void keyPressed(KeyEvent event) {
        int[][] prev = gameContext.getPlayingBoard();
        //Если нажимаем на кнопку R перезапускаем игру
        if (event.getCode() == KeyCode.R) {
            init();
        }
        //Если нажимаем на Esc вылезает окно с кнопками
        //Если нажимаем Continue просто продолжаем игру
        //Если нажимаем Exit программа закрывается
        if (event.getCode() == KeyCode.ESCAPE) {
            final Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Button button1 = new Button("Continue");
            Button button2 = new Button("Exit");
            button1.setOnAction(e -> {
                stage.close();
            });
            button2.setOnAction(e -> {
                System.exit(0);
            });
            displayDialogsEscape(stage, button1, button2);
        }

        //Когда мы нажимаем на какие-то стрелочки происходит действие
        if (event.getCode().isNavigationKey()) {
            switch (event.getCode()) {
                case DOWN -> gameContext.down();
                case UP -> gameContext.up();
                case RIGHT -> gameContext.right();
                case LEFT -> gameContext.left();
            }

            //Если мы победили, то открыем окно с кнопками Continue и Restart
            //При нажатии на Continue продолжаем игру до проигрыша
            //При нажатии на Restart просто перезапускаем игру с нуля
            if (gameContext.isGameWin() && !gameContext.isNeedToContinue()) {
                final Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                Button button1 = new Button("Continue");
                Button button2 = new Button("Restart game");
                button1.setOnAction(e -> {
                    stage.close();
                    gameContext.setNeedToContinue(true);
                });
                button2.setOnAction(e -> {
                    stage.close();
                    init();
                });
                displayDialogs(stage, button1, button2);
            }

            //Если еще имеются ходы(поле не поменялось по значениям в каждой клетке) и игра не закончена, генерируем новую клетку
            if (!gameContext.checkEndOfTheGame() && !Arrays.deepEquals(prev, gameContext.getPlayingBoard())) {
                createCell();
            }
            refreshPlayingBoard();
        }
    }

    //Создаем новую клетку
    private void createCell() {
        //Если сгенерировали номер на существующей ячейке, пробуем ещё раз пока не создатся в свободной клетке
        //Создаём клетку на рандомном месте, с вероятностью 90% создаётся со значением 2
        if (!gameContext.createCellAt(
                random.nextInt(BOARD_SIZE),
                random.nextInt(BOARD_SIZE),
                Math.random() < 0.90 ? 1024 : 1024)
        )
            createCell();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Ставим цвет фона(весь кроме клеток)
        mainGrid.setBackground(getColorForBackground(Color.rgb(187, 173, 160)));
        //Отступы клеток от сторон gridPane(Сверху, справа, снизу, слева)
        mainGrid.setPadding(new Insets(10, 0, 10, 20));
        //Выравниваем наше поле по середине
        mainGrid.setAlignment(Pos.CENTER);
        //Создаем клетки для наших наших значений
        cells = createButtons(mainGrid);
        init();
    }

    //Инициализируем игру(начинаем)
    private void init() {
        gameContext = new GameContext();
        refreshPlayingBoard();
    }

    //Обновляем наше поле(меняем все клетки обратно в 0 значение)
    private void refreshPlayingBoard() {
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++) {
                int cell = gameContext.getCell(i, j);

                cells[i][j].setText(cell > 0 ? String.valueOf(cell) : "");
                cells[i][j].setBackground(cell > 0 ? getColorForBackground(getBackgroundColor(cell)) : getColorForBackground(getBackgroundColor(0)));
            }
        if (gameContext.checkEndOfTheGame())
            createDialogBox();
        scoreLabel.setText(String.valueOf(gameContext.getScore()));
    }

    //Создаем окно с кнопкой для перезапуска игры если проиграли
    void createDialogBox() {
        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);
        Button button = new Button("Restart game");
        button.setOnAction(arg0 -> {
            myDialog.close();
            init();
        });
        displayDialog(myDialog, button);
    }
}
