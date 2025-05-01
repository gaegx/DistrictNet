package com.districtnet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.districtnet.model.Node;

public interface NodeRepository extends JpaRepository<Node,Long> {

}

