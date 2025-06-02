package com.districnet.service.Impl;



import com.districnet.dto.DataChunkDto;
import com.districnet.service.CsvSplitterService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsvSplitterServiceImpl implements CsvSplitterService {

    @Override
    public List<DataChunkDto> splitCsv(String originalCsvPath, int chunkSize) throws IOException {
        List<DataChunkDto> chunkFiles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(originalCsvPath))) {
            String header = reader.readLine();
            if (header == null) {
                throw new IOException("Пустой CSV-файл");
            }

            int chunkIndex = 0;
            List<String> buffer = new ArrayList<>(chunkSize);
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.add(line);

                if (buffer.size() >= chunkSize) {
                    File file = writeChunkToFile(header, buffer, chunkIndex++);
                    chunkFiles.add(new DataChunkDto(file.getAbsolutePath(), file.length()));
                    buffer.clear();
                }
            }

            // последний чанк, если остались строки
            if (!buffer.isEmpty()) {
                File file = writeChunkToFile(header, buffer, chunkIndex);
                chunkFiles.add(new DataChunkDto(file.getAbsolutePath(), file.length()));
            }
        }

        return chunkFiles;
    }

    private File writeChunkToFile(String header, List<String> lines, int index) throws IOException {
        File chunkFile = File.createTempFile("chunk_" + index + "_", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFile))) {
            writer.write(header);
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }

        log.info("Создан чанк-файл: {}", chunkFile.getAbsolutePath());
        return chunkFile;
    }
}

