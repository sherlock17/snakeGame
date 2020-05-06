package hu.abarsi.snake.engine.test;

import hu.abarsi.snake.engine.api.Direction;

import java.awt.Point;

import java.util.List;

public class TestStateTO
{
    private int columns;
    private Point fruit;
    private int life;
    private Direction prevDirection;
    private int rows;
    private int scores;
    private List<Point> snake;

    public int getColumns()
    {
        return columns;
    }

    public void setColumns(int columns)
    {
        this.columns = columns;
    }

    public Point getFruit()
    {
        return fruit;
    }

    public void setFruit(Point fruit)
    {
        this.fruit = fruit;
    }

    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public Direction getPrevDirection()
    {
        return prevDirection;
    }

    public void setPrevDirection(Direction prevDirection)
    {
        this.prevDirection = prevDirection;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public int getScores()
    {
        return scores;
    }

    public void setScores(int scores)
    {
        this.scores = scores;
    }

    public List<Point> getSnake()
    {
        return snake;
    }

    public void setSnake(List<Point> snake)
    {
        this.snake = snake;
    }
}
