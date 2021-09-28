package sample.utils;

import javafx.scene.paint.Color;

public class Backgrounds {
    //Получаем цвет клетки и заднего фона за клеткой
    public static Color getBackgroundColor(int number) {
        return switch (number) {
//238 228 218 1.0     0xeee4da
            case 2 -> Color.rgb(238, 228, 218, 1.0);
//237 224 200 1.0     0xede0c8
            case 4 -> Color.rgb(237, 224, 200, 1.0);
//242 177 121 1.0     0xf2b179
            case 8 -> Color.rgb(242, 177, 121, 1.0);
//245 149 99  1.0     0xf59563
            case 16 -> Color.rgb(245, 149, 99, 1.0);
//246 124 95  1.0     0xf67c5f
            case 32 -> Color.rgb(246, 124, 95, 1.0);
//246 94  59  1.0     0xf65e3b
            case 64 -> Color.rgb(246, 94, 59, 1.0);
//237 207 114 1.0     0xedcf72
            case 128 -> Color.rgb(237, 207, 114, 1.0);
//237 204 97  1.0     0xedcc61
            case 256 -> Color.rgb(237, 204, 97, 1.0);
//237 200 80  1.0     0xedc850
            case 512 -> Color.rgb(237, 200, 80, 1.0);
//237 197 63  1.0     0xedc53f
            case 1024 -> Color.rgb(237, 197, 63, 1.0);
//237 194 46  1.0     0xedc22e
            case 2048 -> Color.rgb(237, 194, 46, 1.0);
            //Если клетка пустая или больше 2048
            default -> Color.rgb(205, 193, 180, 1.0);
        };
        //0xcdc1b4
    }

}
