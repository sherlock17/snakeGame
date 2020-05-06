package hu.abarsi.snake.engine.items;

public class Wall implements Cell
{
    private static final char WALL_CHARACTER = 'X';

    @Override public char getCharacter()
    {
        return WALL_CHARACTER;
    }

    @Override public boolean isVisitable()
    {
        return false;
    }
}
