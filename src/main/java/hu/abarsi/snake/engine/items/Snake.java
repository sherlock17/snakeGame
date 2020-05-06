package hu.abarsi.snake.engine.items;

import hu.abarsi.snake.engine.api.Direction;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Snake
{
    private Deque<Point> positions;

    public Snake(Point position)
    {
        positions = new LinkedList<>();

        positions.add(position);
    }

    public Snake(java.util.List<Point> positions)
    {
        this.positions = new LinkedList<>();

        for (Point position : positions)
        {
            this.positions.add(position);
        }

    }

    public boolean isHead(Point p)
    {
        return positions.getFirst().equals(p);
    }

    public boolean isCollision(Point newPos)
    {
        Point tail = positions.getLast();
        if (newPos.equals(tail))
        {
            return false;
        }

        return positions.contains(newPos);
    }

    public Point getNewPosition(Direction direction)
    {
        Point head = positions.getFirst();

        return calculateNextPoint(head, direction);

    }

    public List<Point> getPositions()
    {
        return new ArrayList<>(positions);
    }

    public void makeMove(Point newPos)
    {
        positions.addFirst(newPos);
        positions.pollLast();
    }

    public void makeMoveAndGrow(Point newPos)
    {
        positions.addFirst(newPos);
    }

    private Point calculateNextPoint(Point current, Direction direction)
    {
        Point directionPoint = direction.getDirectionPoint();

        return new Point(current.x + directionPoint.x, current.y + directionPoint.y);

    }
}
