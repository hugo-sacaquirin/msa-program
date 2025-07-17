package com.sipeip.repository;

import com.sipeip.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    List<Program> findByResponsible(String responsible);

    List<Program> findByName(String name);
}