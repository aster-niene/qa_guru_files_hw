package com.file.test;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.test.model.JsonData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipFile;

import static com.codeborne.pdftest.PDF.containsText;
import static com.file.test.utils.FileUtils.getFile;
import static org.assertj.core.api.Assertions.assertThat;

public class FilesTests {

    ClassLoader cl = FilesTests.class.getClassLoader();
    ZipFile file = new ZipFile("src/test/resources/test.zip");

    public FilesTests() throws IOException {
    }

    @Test
    public void jsonTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (
                InputStream resource = cl.getResourceAsStream("test.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            JsonData jsonData = objectMapper.readValue(reader, JsonData.class);
            assertThat(jsonData.id).isEqualTo(1);
            assertThat(jsonData.name).isEqualTo("Ivan");
            assertThat(jsonData.work).isTrue();
            assertThat(jsonData.topics.get(0)).isEqualTo("Fork");
            assertThat(jsonData.topics.get(1)).isEqualTo("Knife");
            assertThat(jsonData.topics.get(2)).isEqualTo("Glas");
            assertThat(jsonData.topics.get(3)).isEqualTo("Spoon");
            assertThat(jsonData.address.city).isEqualTo("Berlin");
            assertThat(jsonData.address.country).isEqualTo("Germany");
        }
    }

    @Test
    public void pdfTest() throws IOException {
        PDF pdf = new PDF(getFile(file, "test.pdf"));
        MatcherAssert.assertThat(pdf, containsText("Test text"));
    }

    @Test
    public void xlsTest() throws IOException {
        XLS xls = new XLS(getFile(file, "test.xls"));
        assertThat(xls.excel.getSheetAt(0).getRow(0).getCell(0).getNumericCellValue()).isEqualTo(1);
        assertThat(xls.excel.getSheetAt(0).getRow(0).getCell(1).getNumericCellValue()).isEqualTo(2);
        assertThat(xls.excel.getSheetAt(0).getRow(0).getCell(2).getNumericCellValue()).isEqualTo(3);

        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("mama");
        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).isEqualTo("papa");
        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue()).isEqualTo("me");
    }

    @Test
    public void csvTest() throws IOException, CsvException {
        CSVReader csvReader = new CSVReader(
                new InputStreamReader(getFile(file, "test.csv"),
                        StandardCharsets.UTF_8));
        List<String[]> content = csvReader.readAll();
        assertThat(content.get(0)[0]).isEqualTo("name");
        assertThat(content.get(0)[1]).isEqualTo("age");
        assertThat(content.get(1)[0]).isEqualTo("Ira");
        assertThat(content.get(1)[1]).isEqualTo("20");
        assertThat(content.get(2)[0]).isEqualTo("Dima");
        assertThat(content.get(2)[1]).isEqualTo("30");
        assertThat(content.get(3)[0]).isEqualTo("Karl");
        assertThat(content.get(3)[1]).isEqualTo("40");
    }
}
