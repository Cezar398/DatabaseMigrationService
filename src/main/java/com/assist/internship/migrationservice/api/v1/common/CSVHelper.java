package com.assist.internship.migrationservice.api.v1.common;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Setter
@NoArgsConstructor
public class CSVHelper {
    private CSVFormat csvFormat;
    private PrintWriter printWriter;
    private CSVPrinter csvPrinter;


    public void writeHeader(Class<?> c) {
        List<String> headers = getHeaders(c);
        try {
            setCsvPrinter(new CSVPrinter(printWriter, csvFormat));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        headers.forEach(header -> {
            try {
                csvPrinter.print(header);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void writeToCsv(List<?> list, Class<?> c) {
        writeHeader(c);
        List<String> headers = getHeaders(c);
        try {
            setCsvPrinter(new CSVPrinter(printWriter, csvFormat));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        list.forEach(o -> {
            try {
                csvPrinter.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            headers.forEach(header -> {
                try {
                    Method method = c.getMethod("get" + header.substring(0, 1).toUpperCase() + header.substring(1));
                    String value = String.valueOf(method.invoke(o));
                    csvPrinter.print(value);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private static List<String> getHeaders(Class<?> c) {
        List<Field> fields = List.of(c.getDeclaredFields());
        return fields.stream().map(Field::getName).collect(Collectors.toList());
    }
}
