package hu.abarsi.snake.test;

import hu.abarsi.snake.engine.SnakeGameImpl;
import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.engine.api.GameStateTO;
import hu.abarsi.snake.engine.test.SnakeGameTest;
import hu.abarsi.snake.engine.test.TestStateTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

import java.util.Arrays;
import java.util.List;

public class EngineTest
{

    private static final char SNAKE_HEAD = 'H';
    private static final char SNAKE = 'S';
    private static final char FRUIT = 'F';
    SnakeGameTest engine;

    @Before public void before()
    {
        engine = new SnakeGameImpl();
    }

    @Test public void eatFruit()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(3, 3))).fruit(new Point(4, 3)).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.DOWN);

        //THEN

        GameStateTO state = engine.getState();

        assertEquals(SNAKE_HEAD, state.getScene()[4][3]);
        assertEquals(SNAKE, state.getScene()[3][3]);
        assertEquals(1, state.getScores());
        assertTrue(newFruitGenerated(state.getScene()));
    }

    @Test public void gameOver()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(1, 1))).fruit(new Point(1, 2)).life(1).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.LEFT);

        //THEN
        assertTrue(engine.isOver());
    }

    @Test public void move()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(3, 3))).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.DOWN);

        //THEN

        GameStateTO state = engine.getState();

        assertNotEquals(SNAKE_HEAD, state.getScene()[3][3]);
        assertNotEquals(SNAKE, state.getScene()[3][3]);
        assertEquals(SNAKE_HEAD, state.getScene()[4][3]);
    }

    @Test public void oppositeDirection()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(3, 3))).prevDirection(Direction.RIGHT).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.LEFT);

        //THEN
        GameStateTO state = engine.getState();
        assertEquals(SNAKE_HEAD, state.getScene()[3][4]);
    }

    @Test public void snakeCollision()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(3, 3), new Point(2, 3), new Point(2, 4),
                    new Point(3, 4), new Point(4, 4))).prevDirection(Direction.DOWN).life(2).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.RIGHT);

        //THEN
        GameStateTO state = engine.getState();
        assertEquals(1, state.getLife());
        assertEquals(SNAKE_HEAD, state.getScene()[3][3]);

    }

    @Test public void snakeEscape()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(3, 3), new Point(2, 3), new Point(2, 4),
                    new Point(3, 4))).prevDirection(Direction.DOWN).life(2).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.RIGHT);

        //THEN
        GameStateTO state = engine.getState();
        assertEquals(2, state.getLife());
        assertEquals(SNAKE_HEAD, state.getScene()[3][4]);

    }

    @Test public void wallCollision()
    {

        //GIVEN
        TestStateTO inputState = TestStateBuilder.getInstance().snake(toList(new Point(1, 1))).fruit(new Point(1, 2)).life(2).build();

        //WHEN
        engine.load(inputState);
        engine.addInput(Direction.LEFT);

        //THEN
        GameStateTO state = engine.getState();
        assertEquals(1, state.getLife());
        assertEquals(SNAKE_HEAD, state.getScene()[1][1]);
    }

    private boolean newFruitGenerated(char[][] scene)
    {
        for (int i = 0; i < scene.length; i++)
        {
            for (int j = 0; j < scene[i].length; j++)
            {
                if (FRUIT == scene[i][j])
                {
                    return true;
                }
            }
        }

        return false;
    }

    private List<Point> toList(Point... point)
    {

        return Arrays.asList(point);
    }
}
