package com.example.onlineshop.repository;

import com.example.onlineshop.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeJpaRepository extends JpaRepository<Code, Long> {

    Optional<Code> getCodeByUserId(Long userId);
}
