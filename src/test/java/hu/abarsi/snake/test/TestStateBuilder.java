package hu.abarsi.snake.test;

import hu.abarsi.snake.engine.api.Direction;
import hu.abarsi.snake.engine.test.TestStateTO;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

public class TestStateBuilder
{
    private int columns;
    private Point fruit;
    private int life;
    private Direction prevDirection;
    private int rows;
    private int scores;
    private List<Point> snake;

    private TestStateBuilder()
    {
        //defaults

        columns = 10;
        rows = 10;
        life = 5;
        fruit = new Point(1, 2);
        snake = new ArrayList<>();
        snake.add(new Point(1, 1));
    }

    public static TestStateBuilder getInstance()
    {
        return new TestStateBuilder();
    }

    public TestStateTO build()
    {
        TestStateTO retValue = new TestStateTO();
        retValue.setColumns(columns);
        retValue.setFruit(fruit);
        retValue.setLife(life);
        retValue.setPrevDirection(prevDirection);
        retValue.setRows(rows);
        retValue.setScores(scores);
        retValue.setSnake(snake);

        return retValue;

    }

    public TestStateBuilder columns(int columns)
    {
        this.columns = columns;

        return this;
    }

    public TestStateBuilder fruit(Point fruit)
    {
        this.fruit = fruit;

        return this;
    }

    public TestStateBuilder life(int life)
    {
        this.life = life;

        return this;
    }

    public TestStateBuilder prevDirection(Direction prevDirection)
    {
        this.prevDirection = prevDirection;

        return this;
    }

    public TestStateBuilder rows(int rows)
    {
        this.rows = rows;

        return this;
    }

    public TestStateBuilder scores(int scores)
    {
        this.scores = scores;

        return this;
    }

    public TestStateBuilder snake(List<Point> snake)
    {
        this.snake = snake;

        return this;
    }

}
