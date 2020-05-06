package hu.abarsi.snake.gui.api;

public class DimensionWrapper
{

    private int columns;
    private int rows;

    public DimensionWrapper(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;

    }

    public int getColumns()
    {
        return columns;
    }

    public int getRows()
    {
        return rows;
    }
}
