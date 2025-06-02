package com.districnet.service;

import com.districnet.dto.DataChunkDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CsvSplitterService {
    List<DataChunkDto> splitCsv(String originalCsvPath, int chunkSize) throws IOException;
}
