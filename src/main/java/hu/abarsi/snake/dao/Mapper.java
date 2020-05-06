package hu.abarsi.snake.dao;

import hu.abarsi.snake.dao.api.ResultTO;

public class Mapper
{

    private static final String SEPARATOR = ";";

    public static String getLine(ResultTO result)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(result.getName().trim());
        builder.append(SEPARATOR);
        builder.append(result.getScore());

        return builder.toString();
    }

    public static int getPoint(String line)
    {
        int idx = line.lastIndexOf(SEPARATOR);

        return Integer.parseInt(line.substring(idx + 1));
    }

    public static ResultTO getTO(String line)
    {
        int idx = line.lastIndexOf(SEPARATOR);
        ResultTO ret = new ResultTO();
        ret.setName(line.substring(0, idx));
        ret.setScore(Integer.parseInt(line.substring(idx + 1)));

        return ret;
    }
}
