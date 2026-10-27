package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static Object[][] getData(String filePath) {
        List<Object[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // пропускаем первую строку с заголовками
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",", -1);

                // защитная проверка: если строка пустая, пропускаем
                if (values.length > 0 && values[0].trim().isEmpty() && values.length == 1) {
                    continue;
                }

                data.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения CSV файла: " + filePath, e);
        }

        return data.toArray(new Object[0][]);
    }
}