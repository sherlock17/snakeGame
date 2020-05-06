package hu.abarsi.snake.engine.items;

public class Field implements Cell
{
    private static final char FIELD_CHARACTER = ' ';

    @Override public char getCharacter()
    {
        return FIELD_CHARACTER;
    }

    @Override public boolean isVisitable()
    {
        return true;
    }
}
