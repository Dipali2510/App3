package com.cg.TestProject1.repository;

import com.cg.TestProject1.domain.Table1;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Table1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Table1Repository extends JpaRepository<Table1, Long> {
}
