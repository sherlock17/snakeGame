package hu.abarsi.snake.gui.api;

import hu.abarsi.snake.MainMenuCommand;
import hu.abarsi.snake.dao.api.ResultTO;
import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.gui.InvalidDimensionException;

import java.io.IOException;

import java.util.List;

public interface SnakeGui
{
    Direction getSnakeDirection() throws IOException;

    String askUserName(int scores);

    void close() throws IOException;

    DimensionWrapper initGame() throws IOException, InvalidDimensionException;

    MainMenuCommand mainMenu();

    void printError(String errorMsg);

    void printRecordBreakingMessage(int scores);

    void printTop10Result(List<ResultTO> results);

    void refresh(LanternaModelTO model) throws IOException;
}
