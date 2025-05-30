package com.districtnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.districtnet.entity.Node;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node,Long> {
    Optional<Node> findByHostname(String hostname);

}

