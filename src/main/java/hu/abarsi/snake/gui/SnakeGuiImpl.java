package hu.abarsi.snake.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import hu.abarsi.snake.MainMenuCommand;
import hu.abarsi.snake.dao.api.ResultTO;
import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.gui.api.DimensionWrapper;
import hu.abarsi.snake.gui.api.LanternaModelTO;
import hu.abarsi.snake.gui.api.SnakeGui;

import java.io.IOException;

import java.util.List;
import java.util.Scanner;

public class SnakeGuiImpl implements SnakeGui
{
    private static final int HEADER_SIZE = 4;

    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_PLAY = "play";
    public static final String COMMAND_RESULTS = "results";
    public static final String RESULT_STRING = "%s, %s | %s";
    private Screen screen;
    private Terminal terminal;
    private TextGraphics textGraphics;

    private static String getRanking(int idx)
    {
        if (idx < 10)
        {
            return " " + idx;
        }
        else
        {
            return String.valueOf(idx);
        }
    }

    private static String makeFixLenght(String name, int length)
    {
        if (name.length() > length)
        {
            return name.substring(0, length);
        }

        int whiteSpaces = length - name.length();

        StringBuilder builder = new StringBuilder(name);

        for (int i = 0; i < whiteSpaces; i++)
        {
            builder.append(" ");
        }

        return builder.toString();
    }

    @Override public Direction getSnakeDirection() throws IOException
    {
        KeyStroke s = terminal.pollInput();
        Direction d = null;

        if (s != null)
        {
            switch (s.getKeyType())
            {

                case ArrowDown:
                    d = Direction.DOWN;
                    break;

                case ArrowUp:
                    d = Direction.UP;
                    break;

                case ArrowLeft:
                    d = Direction.LEFT;
                    break;

                case ArrowRight:
                    d = Direction.RIGHT;
                    break;

                default:
                    d = null;
            }
        }

        return d;
    }

    @Override public String askUserName(int scores)
    {
        System.out.println("Your final score is: " + scores);
        System.out.println("Please type your name: ");

        Scanner sc = new Scanner(System.in);

        String name = sc.nextLine();

        while (name.isEmpty())
        {
            System.out.println("Please type a non empty name: ");
            name = sc.nextLine();
        }

        return name;
    }

    @Override public void close() throws IOException
    {
        screen.stopScreen();
    }

    @Override public DimensionWrapper initGame() throws IOException, InvalidDimensionException
    {
        DimensionWrapper dimensions = readDimensions();
        terminal = new DefaultTerminalFactory().createTerminal();
        validateDimensions(dimensions.getRows(), dimensions.getColumns());
        screen = new TerminalScreen(terminal);

        textGraphics = screen.newTextGraphics();

        screen.startScreen();

        return dimensions;
    }

    @Override public MainMenuCommand mainMenu()
    {
        String command = readCommand();

        MainMenuCommand retValue;

        if (COMMAND_PLAY.equals(command))
        {
            retValue = MainMenuCommand.PLAY;
        }
        else if (COMMAND_RESULTS.equals(command))
        {
            retValue = MainMenuCommand.SHOW_RESULTS;
        }
        else
        {
            retValue = MainMenuCommand.EXIT;
        }

        return retValue;
    }

    @Override public void printError(String errorMsg)
    {
        System.err.println(errorMsg);
    }

    @Override public void printRecordBreakingMessage(int score)
    {
        System.out.println("Congratulations! You set up a new record! Your score is: " + score);
    }

    @Override public void printTop10Result(List<ResultTO> results)
    {
        System.out.println("Top 10 results: ");
        int nameLength = 20;

        int idx = 1;
        for (ResultTO result : results)
        {
            String fixLenghtName = makeFixLenght(result.getName(), nameLength);
            String rangking = getRanking(idx);
            System.out.println(String.format(RESULT_STRING, rangking, fixLenghtName, String.valueOf(result.getScore())));
            idx++;
        }
    }

    @Override public void refresh(LanternaModelTO model) throws IOException
    {
        screen.clear();
        textGraphics.putString(0, 0, "record: " + model.getRecord());
        textGraphics.putString(0, 1, "life: " + model.getLife());
        textGraphics.putString(0, 2, "scores: " + model.getScores());

        int rows = model.getRows().length;

        for (int i = HEADER_SIZE; i < (rows + HEADER_SIZE); i++)
        {
            String row = model.getRows()[i - HEADER_SIZE];
            textGraphics.putString(0, i, row);
        }

        screen.refresh();
    }

    private String readCommand()
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type one of the following command: (play, results, exit)");

        String command = sc.nextLine();

        while (!validCommand(command))
        {
            System.out.println("Not a valid command: " + command);
            System.out.println("Type one of the following command: (play, results, exit)");
            command = sc.nextLine();
        }

        return command;
    }

    private DimensionWrapper readDimensions() throws InvalidDimensionException
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the row numbers of the map: ");

        String rowNumberStr = sc.nextLine();

        System.out.println("Type the column numbers of the map: ");

        String columnNumberStr = sc.nextLine();

        return validateAndParse(rowNumberStr, columnNumberStr);
    }

    private DimensionWrapper validateAndParse(String rowNumberStr, String columnNumberStr) throws InvalidDimensionException
    {
        try
        {
            int rows = Integer.parseInt(rowNumberStr);
            int columns = Integer.parseInt(columnNumberStr);

            return new DimensionWrapper(rows, columns);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidDimensionException("row and column numbers must be numbers");
        }

    }

    private void validateDimensions(int rows, int columns) throws IOException, InvalidDimensionException
    {
        if ((rows < 3) || (columns < 3))
        {
            terminal.close();
            throw new InvalidDimensionException("Map must contain at least 3 row and 3 column");
        }

        TerminalSize maxSize = terminal.getTerminalSize();

        if ((rows + HEADER_SIZE) > maxSize.getRows())
        {
            int rowSize = maxSize.getRows() - HEADER_SIZE;
            terminal.close();
            throw new InvalidDimensionException("Map can contain maximum " + rowSize + " row");
        }

        if (columns > maxSize.getColumns())
        {
            terminal.close();
            throw new InvalidDimensionException("Map can contain maximum " + maxSize.getColumns() + " column");
        }
    }

    private boolean validCommand(String command)
    {
        return COMMAND_PLAY.equals(command) || COMMAND_RESULTS.equals(command) || COMMAND_EXIT.equals(command);
    }
}
