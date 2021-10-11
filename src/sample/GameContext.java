package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sample.utils.Utils.*;

public class GameContext {

    public int[][] playingField;
    public int score = 0;
    public boolean gameWin = false;
    public boolean needToContinue = true;

    public GameContext() {
        playingField = new int[FIELD_SIZE][FIELD_SIZE];
        Random random = new Random();
        //Создаётся первая клетка на рандомном месте, с вероятностью 90% создаётся со значением 2
        createCellAt(random.nextInt(FIELD_SIZE), random.nextInt(FIELD_SIZE), Math.random() < 0.90 ? CELL_TWO : CELL_FOUR);
    }

    //Создание клетки на любой ещё не занятой клетке
    public boolean createCellAt(int row, int column, int number) {
        if (getCell(row, column) != 0)
            return false;

        playingField[row][column] = number;
        return true;
    }

    //Двигает наши значения вверх
    //Действия:
    //1) Транспонируем нашу матрицу с помощью transpose()
    //2) Переворачиваем на 180 нашу матрицу с помощью reverse()
    //Пункт 1-2 могут быть выполнены и в другом порядке
    //3) Двигаем наши значения вправо
    //4) Возвращаем наши значения с помощью reverse() и transpose()[могут идти в любом порядке]
    //Пример1:
    //Порядок действий, который сейчас показан в коде
    //|0 0 2 0|   |0 0 0 0|   |2 8 0 0|   |0 0 2 8|   |0 0 0 0|   |0 2 2 8|
    //|0 2 0 0| ->|0 2 4 0| ->|0 0 0 2| ->|0 0 0 2| ->|2 4 0 0| ->|0 4 0 2|
    //|0 4 0 8|   |2 0 0 0|   |0 4 2 0|   |0 0 4 2|   |2 0 0 0|   |0 0 0 0|
    //|0 0 0 2|   |0 0 8 2|   |0 0 0 0|   |0 0 0 0|   |8 2 0 0|   |0 0 0 0|
    //Пример2:
    //Порядок действий в другом порядке
    //|0 0 2 0|   |2 0 0 0|   |2 8 0 0|   |0 0 2 8|   |0 0 0 0|   |0 2 2 8|
    //|0 2 0 0| ->|8 0 4 0| ->|0 0 0 2| ->|0 0 0 2| ->|0 0 0 0| ->|0 4 0 2|
    //|0 4 0 8|   |2 0 2 0|   |0 4 2 0|   |0 0 4 2|   |2 0 4 0|   |0 0 0 0|
    //|0 0 0 2|   |0 2 0 0|   |0 0 0 0|   |0 0 0 0|   |8 2 2 0|   |0 0 0 0|
    //Двигаем наши значения вправо
    public void up() {
        int[][] transposeArray = transpose(playingField);
        int[][] reversedTransposedArray = reverse(transposeArray);
        int[][] shiftedArray = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            int[] arrayToShift = reversedTransposedArray[i];
            shiftedArray[i] = moveLine(arrayToShift);
        }
        reversedTransposedArray = reverse(shiftedArray);
        transposeArray = transpose(reversedTransposedArray);
        playingField = transposeArray;
    }

    //Двигает наши значения влево
    //Действия:
    //1) Переворачиаем наши значения на 180 с помощью reverse()
    //2) Двигаем наши значения вправо
    //3) Возвращаем значения обрато с помощью reverse()
    //Пример:
    //|0 0 2 0|   |2 0 0 0|   |0 0 0 2|   |2 0 0 0|
    //|0 2 0 0| ->|8 0 4 0| ->|0 0 8 4| ->|2 0 0 0|
    //|0 4 0 8|   |0 0 2 0|   |0 0 0 2|   |4 8 0 0|
    //|0 0 0 2|   |0 2 0 0|   |0 0 0 2|   |2 0 0 0|
    public void left() {
        int[][] reverseArray = reverse(playingField);
        int[][] shiftedArray = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            int[] arrayToShift = reverseArray[i];
            shiftedArray[i] = moveLine(arrayToShift);
        }
        playingField = reverse(shiftedArray);
    }

    //Двигает наши значения вправо
    //Пример:
    //|0 0 2 0|   |0 0 0 2|
    //|0 2 0 0| ->|0 0 0 2|
    //|0 4 0 8|   |0 0 4 8|
    //|0 0 0 2|   |0 0 0 2|
    public void right() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            int[] arrayToShift = playingField[i];
            playingField[i] = moveLine(arrayToShift);
        }
    }

    //Двигает наши значения вверх
    //Действия:
    //1) Транспонируем матрицу с помощью transpose()
    //2) Двигаем наши значения вправо
    //3) Возврашаем значения обратно в помощью transpose()
    //Пример:
    //|0 0 2 0|   |0 0 0 0|   |0 0 0 0|   |0 0 0 0|
    //|0 2 0 0| ->|0 2 4 0| ->|0 0 2 4| ->|0 0 0 0|
    //|0 4 0 8|   |2 0 0 0|   |0 0 0 2|   |0 2 0 8|
    //|0 0 0 2|   |0 0 8 2|   |0 0 8 2|   |0 4 2 2|
    public void down() {
        int[][] transposeArray = transpose(playingField);
        int[][] shiftedArray = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            int[] arrayToShift = transposeArray[i];
            shiftedArray[i] = moveLine(arrayToShift);
        }
        shiftedArray = transpose(shiftedArray);
        playingField = shiftedArray;
    }

    //Переворот на 180 градусов
    //Пример:
    //|0 0 2 0|   |2 0 0 0|
    //|0 2 0 0| ->|8 0 4 0|
    //|0 4 0 8|   |0 0 2 0|
    //|0 0 0 2|   |0 2 0 0|
    private int[][] reverse(int[][] inputMatrix) {
        int[][] resultArray = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                resultArray[i][j] = inputMatrix[(FIELD_SIZE - 1) - i][(FIELD_SIZE - 1) - j];
            }
        }
        return resultArray;
    }

    //Транспонирование матрицы
    //Переворачивает наши значения на 90 градусов влево и на(от) нас на 180 градусов.
    //Пример:
    //|0 0 2 0|   |0 0 0 0|
    //|0 2 0 0| ->|0 2 4 0|
    //|0 4 0 8|   |2 0 0 0|
    //|0 0 0 2|   |0 0 8 2|
    private int[][] transpose(int[][] inputArray) {
        int[][] resultArray = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                resultArray[i][j] = inputArray[j][i];
            }
        }
        return resultArray;
    }

    //Алгоритм для движения вправо
    private int[] moveLine(int[] movingLine) {
        boolean merged = false;
        //Если весь ряд с одинаковыми значениями, то просто заменяем последний и предпоследний элемент на удвоенные
        //Для очков умножаем на 2 уже изменённую клетку
        if (movingLine[0] == movingLine[1] && movingLine[1] == movingLine[2] && movingLine[2] == movingLine[3]) {
            movingLine[3] = movingLine[0] + movingLine[1];
            movingLine[2] = movingLine[0] + movingLine[1];
            movingLine[1] = 0;
            movingLine[0] = 0;
            score += movingLine[3] * 2;
            return movingLine;
        }
        //Если ряд в виде 2 2 4 4 -> 0 0 4 8 или 4 4 2 2 -> 0 0 8 4
        if (movingLine[0] == movingLine[1] && movingLine[2] == movingLine[3] && movingLine[1] != 0 && movingLine[2] != 0) {
            movingLine[2] = movingLine[1] * 2;
            movingLine[3] = movingLine[3] * 2;
            movingLine[0] = 0;
            movingLine[1] = 0;
            score += movingLine[2] + movingLine[3];
            return movingLine;
        }

        //Идем с конца строки
        for (int j = FIELD_SIZE - 2; j >= 0; j--) {
            if (movingLine[j] == 0)
                continue;
            int currentCell = j;
            int next = currentCell + 1;

            while (next < FIELD_SIZE) {

                //Если ячейки с одинаковым значением, то соединяем
                if ((movingLine[currentCell] == movingLine[next]) && (!merged)) {
                    movingLine[next] += movingLine[currentCell];
                    //Увеличение очков
                    score += movingLine[next];
                    //То что соединили, обнуляем
                    movingLine[currentCell] = 0;
                    merged = true;
                }

                //Смещаем вправо, если следующей ячейки нет
                else if (movingLine[next] == 0) {
                    movingLine[next] = movingLine[currentCell];
                    movingLine[currentCell] = 0;
                    currentCell++;
                    next++;
                } else {
                    break;
                }
            }
        }
        return movingLine;
    }

    //Получаем клетку на определённом месте
    public int getCell(int row, int column) {
        return playingField[row][column];
    }

    //Получить очки
    public int getScore() {
        return score;
    }

    //Получаем игровое поле
    public int[][] getPlayingField() {
        int[][] res = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            //arraycopy: 1) Откуда берём значение, 2) С какой позиции начинаем брать значения, 3) Куда записываем значение
            //4) С какой позиции начинаем записывать значения, 5) Количество копируемых элементов
            System.arraycopy(playingField[i], 0, res[i], 0, FIELD_SIZE);
        }
        return res;
    }

    //Проверка на окончание игры если проигрыш(если на поле 16 клеток и нет клеток, которые можно соединить, то заканчивать игру)
    public boolean checkEndOfTheGameLose() {
        if (isPlayingFieldFill()) {
            return !hasAnySurroundings();
        }
        return false;
    }

    //Проверяем все клетки на наличие 2048
    public void check2048() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (playingField[i][j] == SCORE2048) {
                    if (!needToContinue) break;
                    gameWin = true;
                    break;
                }
            }
        }
    }

    //Проверяем для каждой клетки соседние клетки[если есть хоть пара одинаковых, то возвращаем true, иначе false]
    private boolean hasAnySurroundings() {

        int[][] directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                List<Integer> surroundings = new ArrayList<>();
                for (int[] direction : directions) {
                    int x = i + direction[0];
                    int y = j + direction[1];
                    if (y >= 0 && y < playingField.length) {
                        if (x >= 0 && x < playingField[y].length) {
                            surroundings.add(playingField[x][y]);
                        }
                    }
                }
                if (surroundings.contains(playingField[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    //Проверка на заполненность игрового поля(если хоть 1 элемент равен 0, то поле не заполнено)
    private boolean isPlayingFieldFill() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (playingField[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
