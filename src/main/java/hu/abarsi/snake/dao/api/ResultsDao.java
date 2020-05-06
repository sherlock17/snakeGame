package hu.abarsi.snake.dao.api;

import java.io.IOException;

import java.util.List;

public interface ResultsDao
{
    int getMaxScore() throws IOException;

    List<ResultTO> getTop10Result() throws IOException;

    void saveResult(ResultTO result) throws IOException;
}
