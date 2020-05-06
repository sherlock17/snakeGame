package hu.abarsi.snake.engine.api;

public class GameStateTO
{
    private int life;
    private char[][] scene;
    private int scores;

    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public char[][] getScene()
    {
        return scene;
    }

    public void setScene(char[][] scene)
    {
        this.scene = scene;
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
