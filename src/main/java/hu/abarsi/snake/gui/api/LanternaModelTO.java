package hu.abarsi.snake.gui.api;

public class LanternaModelTO
{
    int life;
    int record;
    String[] rows;
    int scores;

    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public int getRecord()
    {
        return record;
    }

    public void setRecord(int record)
    {
        this.record = record;
    }

    public String[] getRows()
    {
        return rows;
    }

    public void setRows(String[] rows)
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
}
