package com.districnet.service;


import com.districnet.dto.TaskCreateDto;

public interface DataDistributorService {
    void distribute(TaskCreateDto taskCreateDto);
}