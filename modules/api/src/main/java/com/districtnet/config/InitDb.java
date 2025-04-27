package com.districtnet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import com.districtnet.repository.NodeRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class InitDb {

    @Autowired
    private NodeRepository nodeRepository;

    @PostConstruct
    public void initDb() {
        
    }
}
