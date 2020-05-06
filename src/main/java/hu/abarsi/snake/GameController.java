package hu.abarsi.snake;

import hu.abarsi.snake.dao.ResultsDaoImpl;
import hu.abarsi.snake.dao.api.ResultTO;
import hu.abarsi.snake.dao.api.ResultsDao;
import hu.abarsi.snake.engine.SnakeGameImpl;
import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.engine.api.GameStateTO;
import hu.abarsi.snake.engine.api.SnakeGame;
import hu.abarsi.snake.gui.InvalidDimensionException;
import hu.abarsi.snake.gui.SnakeGuiImpl;
import hu.abarsi.snake.gui.api.DimensionWrapper;
import hu.abarsi.snake.gui.api.LanternaModelTO;
import hu.abarsi.snake.gui.api.SnakeGui;

import java.io.IOException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController
{

    private static final int SLEEP_INTERVALL_IN_MILLIS = 500;
    private ResultsDao dao;
    private SnakeGame game;
    private SnakeGui gui;

    private Logger LOGGER = Logger.getLogger(GameController.class.getName());
    private int record;

    public GameController()
    {
        game = new SnakeGameImpl();
        dao = new ResultsDaoImpl();
        gui = new SnakeGuiImpl();
    }

    public void play() throws IOException, InvalidDimensionException
    {
        init();
        mainLoop();
        finalizeGame();
    }

    private LanternaModelTO convert(GameStateTO state)
    {
        LanternaModelTO retValue = new LanternaModelTO();
        retValue.setRecord(record);
        retValue.setLife(state.getLife());
        retValue.setScores(state.getScores());

        String[] rows = new String[state.getScene().length];

        for (int i = 0; i < state.getScene().length; i++)
        {
            rows[i] = String.valueOf(state.getScene()[i]);
        }

        retValue.setRows(rows);

        return retValue;
    }

    private void finalizeGame() throws IOException
    {
        int scores = game.getState().getScores();
        if (scores > record)
        {
            gui.printRecordBreakingMessage(scores);
        }

        String name = gui.askUserName(scores);
        ResultTO to = new ResultTO();
        to.setScore(scores);
        to.setName(name);

        dao.saveResult(to);
    }

    private void init() throws IOException, InvalidDimensionException
    {
        record = dao.getMaxScore();
        DimensionWrapper dimensions = gui.initGame();
        game.newGame(dimensions.getRows(), dimensions.getColumns());
    }

    private void mainLoop() throws IOException
    {
        while (!game.isOver())
        {
            Direction direction = gui.getSnakeDirection();
            game.addInput(direction);
            GameStateTO state = game.getState();

            gui.refresh(convert(state));

            sleep();

        }

        gui.close();
    }

    private void sleep()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(SLEEP_INTERVALL_IN_MILLIS);
        }
        catch (InterruptedException e)
        {
            LOGGER.log(Level.FINEST, "MainLoop sleep interrupted", e);
        }
    }
}
