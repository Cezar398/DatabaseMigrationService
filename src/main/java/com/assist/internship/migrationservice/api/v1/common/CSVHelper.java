package com.assist.internship.migrationservice.api.v1.common;

import lombok.SneakyThrows;
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
public class CSVHelper {
    private CSVFormat csvFormat;
    private PrintWriter printWriter;
    private CSVPrinter csvPrinter;

    public CSVHelper() {
    }

    public void setCsvFormat(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void setCsvPrinter(CSVPrinter csvPrinter) {
        this.csvPrinter = csvPrinter;
    }

    @SneakyThrows
    public void writeHeader(Class<?> c) {
        List<String> headers = getHeaders(c);
        setCsvPrinter(new CSVPrinter(printWriter, csvFormat));
        headers.forEach(header -> {
            try {
                csvPrinter.print(header);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    public void writeToCsv(List<?> list, Class<?> c) {
        writeHeader(c);
        List<String> headers = getHeaders(c);
        setCsvPrinter(new CSVPrinter(printWriter, csvFormat));

        list.forEach(o -> {
            writeNewLine();
            headers.forEach(header -> {
                try {
                    Method method = c.getMethod("get" + header.substring(0, 1).toUpperCase() + header.substring(1));
                    String value = String.valueOf(method.invoke(o));
                    writeRow(value);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private static List<String> getHeaders(Class<?> c) {
        List<Field> fields = List.of(c.getDeclaredFields());
        List<String> headers = fields.stream().map(Field::getName).collect(Collectors.toList());
        return headers;
    }

    private void writeRow(String s) {
        try {
            csvPrinter.print(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void writeNewLine() {
        csvPrinter.println();
    }
}
