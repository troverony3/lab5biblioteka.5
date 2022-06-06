package com.laba.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

// класс с реализацией шаблонных методов
public class XlsxReaderServiceImpl extends ExcelReaderService {

    private static XlsxReaderServiceImpl reader;

    private XlsxReaderServiceImpl() {
    }

    // синглтон
    public static XlsxReaderServiceImpl of() {
        if (null == reader) {
            reader = new XlsxReaderServiceImpl();
        }
        return reader;
    }

    @Override
    protected Workbook getWorkbook(String filename) throws IOException {
        return new XSSFWorkbook(new FileInputStream(filename));
    }
}
