package com.districnet.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ChunkDistributorService {
    void distribute(Map<String, List<File>> nodeToChunks, String baseRemotePath);
}
