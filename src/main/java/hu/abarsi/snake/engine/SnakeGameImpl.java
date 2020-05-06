package hu.abarsi.snake.engine;

import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.engine.api.GameStateTO;
import hu.abarsi.snake.engine.api.SnakeGame;
import hu.abarsi.snake.engine.items.Cell;
import hu.abarsi.snake.engine.items.Field;
import hu.abarsi.snake.engine.items.Snake;
import hu.abarsi.snake.engine.items.Wall;
import hu.abarsi.snake.engine.test.SnakeGameTest;
import hu.abarsi.snake.engine.test.TestStateTO;

import java.awt.Point;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGameImpl implements SnakeGame, SnakeGameTest
{

    private static final char FRUIT_CHAR = 'F';
    private static final char SNAKE_CHAR = 'S';
    private static final char SNAKE_HEAD_CHAR = 'H';

    private int columns;
    private Point fruit;
    private int life;
    private Direction prevDirection;
    private Random random = new SecureRandom();
    private int rows;

    private Cell[][] scene;
    private int scores;
    private Snake snake;

    @Override public boolean isOver()
    {
        return life <= 0;
    }

    @Override public GameStateTO getState()
    {
        GameStateTO retValue = new GameStateTO();

        retValue.setLife(life);
        retValue.setScores(scores);

        char[][] map = new char[rows][columns];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                map[i][j] = scene[i][j].getCharacter();
            }
        }

        map[fruit.x][fruit.y] = FRUIT_CHAR;

        for (Point p : snake.getPositions())
        {
            if (snake.isHead(p))
            {
                map[p.x][p.y] = SNAKE_HEAD_CHAR;
            }
            else
            {
                map[p.x][p.y] = SNAKE_CHAR;
            }

        }

        retValue.setScene(map);

        return retValue;

    }

    @Override public void addInput(Direction direction)
    {

        Direction newDirection = calculateNewDirection(direction);
        if (newDirection != null)
        {
            makeMove(newDirection);
        }
    }

    @Override public void load(TestStateTO state)
    {
        initScene(state.getRows(), state.getColumns());

        this.snake = new Snake(state.getSnake());

        this.fruit = state.getFruit();

        this.prevDirection = state.getPrevDirection();

        this.life = state.getLife();

        this.scores = state.getScores();
    }

    @Override public void newGame(int rows, int columns)
    {
        initScene(rows, columns);
        generateSnake();
        generateNewFruit();
        initVariables();
    }

    private List<Point> getAccessableCells()
    {
        List<Point> elements = new ArrayList<>(rows * columns);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                Point p = new Point(i, j);
                if (scene[i][j].isVisitable())
                {
                    elements.add(p);
                }
            }
        }

        return elements;
    }

    private boolean isCollision(Point newPos)
    {
        return (!get(newPos).isVisitable() || snake.isCollision(newPos));
    }

    private boolean isBorder(int i, int j)
    {
        if ((i == 0) || (j == 0) || (i == (rows - 1)) || (j == (columns - 1)))
        {
            return true;
        }

        return false;
    }

    private Direction calculateNewDirection(Direction direction)
    {
        if ((prevDirection == null) && (direction == null))
        {
            return null;
        }

        if (prevDirection == null)
        {
            return direction;
        }

        if (direction == null)
        {
            return prevDirection;
        }

        if (prevDirection.opposite().equals(direction))
        {
            return prevDirection;
        }

        return direction;
    }

    private void decreaseLife()
    {
        life--;
    }

    private void eatFruit()
    {
        scores++;
        generateNewFruit();
    }

    private boolean fruitFound(Point newPos)
    {
        return newPos.equals(fruit);

    }

    private void generateNewFruit()
    {
        java.util.List<Point> elements = getAccessableCells();

        elements.removeAll(snake.getPositions());

        fruit = elements.get(random.nextInt(elements.size()));
    }

    private void generateSnake()
    {
        List<Point> elements = getAccessableCells();

        Point snakePosition = elements.get(random.nextInt(elements.size()));

        snake = new Snake(snakePosition);
    }

    private Cell get(Point p)
    {
        return scene[p.x][p.y];
    }

    private void initScene(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;

        this.scene = new Cell[rows][columns];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                scene[i][j] = isBorder(i, j) ? new Wall() : new Field();
            }
        }
    }

    private void initVariables()
    {
        life = 5;
        scores = 0;
    }

    private void makeMove(Direction direction)
    {
        Point newPos = snake.getNewPosition(direction);

        if (isCollision(newPos))
        {
            decreaseLife();
        }
        else
        {
            if (fruitFound(newPos))
            {
                snake.makeMoveAndGrow(newPos);
                eatFruit();
            }
            else
            {
                snake.makeMove(newPos);
            }
        }

        prevDirection = direction;
    }
}
