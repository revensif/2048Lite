package sample.tests;

import org.junit.Test;
import sample.GameContext;

import static org.junit.Assert.*;

public class GameContextTest {
    GameContext gameContext;
    final static int FIELD_SIZE = 4;
    final static int CELL_TWO = 2;
    final static int CELL_FOUR = 4;

    @Test
    public void up1() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {0, 0, 0, 2},
                {0, 0, 2, 0},
                {2, 4, 4, 0},
                {0, 2, 8, 16}
        };
        gameContext.up();
        int[][] modified1PlayingField = new int[][]{
                {2, 4, 2, 2},
                {0, 2, 4, 16},
                {0, 0, 8, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void up2() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {8, 4, 1024, 2},
                {16, 0, 1024, 2},
                {16, 4, 64, 4},
                {16, 2, 8, 16}
        };
        gameContext.up();
        int[][] modified1PlayingField = new int[][]{
                {8, 8, 2048, 4},
                {32, 2, 64, 4},
                {16, 0, 8, 16},
                {0, 0, 0, 0}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void left1() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {16, 2, 1024, 1024},
                {8, 4, 2, 4},
                {8, 8, 2, 2},
                {2, 2, 8, 16}
        };
        gameContext.left();
        int[][] modified1PlayingField = new int[][]{
                {16, 2, 2048, 0},
                {8, 4, 2, 4},
                {16, 4, 0, 0},
                {4, 8, 16, 0}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void left2() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {0, 0, 0, 2},
                {0, 0, 2, 0},
                {2, 4, 4, 0},
                {0, 2, 8, 16}
        };
        gameContext.left();
        int[][] modified1PlayingField = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 8, 0, 0},
                {2, 8, 16, 0}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void right1() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {1024, 0, 1024, 2},
                {8, 4, 2, 8},
                {16, 4, 4, 16},
                {4, 4, 4, 4}
        };
        gameContext.right();
        int[][] modified1PlayingField = new int[][]{
                {0, 0, 2048, 2},
                {8, 4, 2, 8},
                {0, 16, 8, 16},
                {0, 0, 8, 8}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void right2() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {2, 2, 0, 2},
                {64, 4, 2, 0},
                {64, 64, 64, 0},
                {128, 2, 4, 4}
        };
        gameContext.right();
        int[][] modified1PlayingField = new int[][]{
                {0, 0, 2, 4},
                {0, 64, 4, 2},
                {0, 0, 64, 128},
                {0, 128, 2, 8}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void down1() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {2, 2, 8, 2},
                {64, 4, 2, 512},
                {64, 2, 32, 4},
                {128, 2, 4, 4}
        };
        gameContext.down();
        int[][] modified1PlayingField = new int[][]{
                {0, 0, 8, 0},
                {2, 2, 2, 2},
                {128, 4, 32, 512},
                {128, 4, 4, 8}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void down2() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {4, 1024, 1024, 2},
                {4, 1024, 32, 4},
                {4, 512, 256, 4},
                {2, 512, 256, 4}
        };
        gameContext.down();
        int[][] modified1PlayingField = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 1024, 2},
                {8, 2048, 32, 4},
                {2, 1024, 512, 8}
        };
        assertArrayEquals(modified1PlayingField, gameContext.getPlayingField());
    }

    @Test
    public void checkEndOfTheGameLose1() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {2, 4, 8, 2},
                {4, 8, 16, 8},
                {8, 16, 8, 16},
                {2, 8, 16, 16}
        };
        gameContext.up();
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                gameContext.createCellAt(i, j, Math.random() < 0.90 ? CELL_TWO : CELL_FOUR);
            }
        }
        boolean gameEnd = gameContext.checkEndOfTheGameLose();
        assertTrue(gameEnd);
    }

    @Test
    public void checkEndOfTheGameLose2() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {2, 4, 4, 2},
                {4, 8, 16, 4},
                {8, 16, 8, 16},
                {2, 8, 16, 16}
        };
        gameContext.down();
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                gameContext.createCellAt(i, j, Math.random() < 0.90 ? CELL_TWO : CELL_FOUR);
            }
        }
        boolean gameEnd = gameContext.checkEndOfTheGameLose();
        assertFalse(gameEnd);
    }

    @Test
    public void checkFirst2048() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {256, 4, 8, 2},
                {4, 8, 16, 2},
                {4, 32, 64, 16},
                {2, 8, 16, 16}
        };
        gameContext.down();
        gameContext.check2048();
        assertFalse(gameContext.gameWin);
    }

    @Test
    public void checkSecond2048() {
        gameContext = new GameContext();
        gameContext.playingField = new int[][]{
                {256, 256, 512, 512},
                {2, 0, 2, 512},
                {8, 1024, 0, 1024},
                {16, 1024, 0, 16}
        };
        gameContext.down();
        gameContext.check2048();
        assertTrue(gameContext.gameWin);
    }

}