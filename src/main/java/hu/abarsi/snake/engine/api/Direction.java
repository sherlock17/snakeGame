package hu.abarsi.snake.engine.api;

import java.awt.Point;

public enum Direction
{
    UP(new Point(-1, 0)),
    DOWN(new Point(1, 0)),
    LEFT(new Point(0, -1)),
    RIGHT(new Point(0, 1));

    private Point directionPoint;

    Direction(Point directionPoint)
    {
        this.directionPoint = directionPoint;
    }

    public Point getDirectionPoint()
    {
        return directionPoint;
    }

    public Direction opposite()
    {
        switch (this)
        {

            case UP:
                return DOWN;

            case DOWN:
                return UP;

            case LEFT:
                return RIGHT;

            case RIGHT:
                return LEFT;

            default:
                throw new EnumConstantNotPresentException(Direction.class, this.name());
        }

    }
}
