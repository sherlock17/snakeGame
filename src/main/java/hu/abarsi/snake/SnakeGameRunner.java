package hu.abarsi.snake;

import hu.abarsi.snake.dao.ResultsDaoImpl;
import hu.abarsi.snake.dao.api.ResultTO;
import hu.abarsi.snake.dao.api.ResultsDao;
import hu.abarsi.snake.gui.InvalidDimensionException;
import hu.abarsi.snake.gui.SnakeGuiImpl;
import hu.abarsi.snake.gui.api.SnakeGui;

import java.io.IOException;

import java.util.List;

public class SnakeGameRunner
{
    private ResultsDao dao;
    private SnakeGui gui;

    public static void main(String[] args) throws IOException
    {
        new SnakeGameRunner().run(args);

    }

    public void init()
    {
        gui = new SnakeGuiImpl();
        dao = new ResultsDaoImpl();
    }

    private void executeCommand(MainMenuCommand command) throws IOException
    {
        if (MainMenuCommand.PLAY.equals(command))
        {
            GameController controller = new GameController();
            try
            {
                controller.play();
            }
            catch (InvalidDimensionException e)
            {
                gui.printError(e.getMessage());
            }
        }
        else if (MainMenuCommand.SHOW_RESULTS.equals(command))
        {
            List<ResultTO> results = dao.getTop10Result();
            gui.printTop10Result(results);
        }
    }

    private MainMenuCommand mainMenu()
    {
        return gui.mainMenu();
    }

    private void run(String[] args) throws IOException
    {
        init();
        MainMenuCommand command = mainMenu();
        executeCommand(command);
    }
}
