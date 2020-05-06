package hu.abarsi.snake.engine.test;

import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.engine.api.GameStateTO;

public interface SnakeGameTest
{
    boolean isOver();

    GameStateTO getState();

    void addInput(Direction direction);

    void load(TestStateTO state);

    void newGame(int rows, int colums);
}
