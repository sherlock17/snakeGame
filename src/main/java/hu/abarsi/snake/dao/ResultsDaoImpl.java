package hu.abarsi.snake.dao;

import hu.abarsi.snake.dao.api.ResultTO;
import hu.abarsi.snake.dao.api.ResultsDao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsDaoImpl implements ResultsDao
{
    public static final String FILE_NAME = "results.txt";

    @Override public int getMaxScore() throws IOException
    {
        File file = getFile();

        String first = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            first = reader.readLine();
        }

        return (first == null) ? 0 : Mapper.getPoint(first);
    }

    @Override public List<ResultTO> getTop10Result() throws IOException
    {
        int ten = 10;
        File file = getFile();
        List<String> lines = new ArrayList<>(ten);

        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            for (int i = 0; ((line = reader.readLine()) != null) && (i < ten); i++)
            {
                lines.add(line);
            }
        }

        return lines.stream().map(Mapper::getTO).collect(Collectors.toList());
    }

    @Override public void saveResult(ResultTO result) throws IOException
    {
        File file = getFile();
        List<String> lines = new ArrayList<>();

        boolean insertNew = true;

        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while (((line = reader.readLine()) != null))
            {
                if (insertNew && (Mapper.getPoint(line) <= result.getScore()))
                {
                    insertNew = false;
                    lines.add(Mapper.getLine(result));
                }

                lines.add(line);
            }

            if (insertNew)
            {
                lines.add(Mapper.getLine(result));
            }
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {

            for (String line : lines)
            {
                writer.write(line);
                writer.newLine();
            }
        }

    }

    private File getFile() throws IOException
    {
        File file = new File(FILE_NAME);

        if (!file.exists())
        {
            file.createNewFile();
        }

        return file;
    }
}
