package hu.abarsi.snake.engine.api;

public interface SnakeGame
{
    boolean isOver();

    GameStateTO getState();

    void addInput(Direction direction);

    void newGame(int rows, int colums);
}
