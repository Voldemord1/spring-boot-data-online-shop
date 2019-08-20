package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.Code;
import com.example.onlineshop.repository.CodeJpaRepository;
import com.example.onlineshop.service.interfaces.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeJpaRepository codeJpaRepository;

    @Autowired
    public CodeServiceImpl(CodeJpaRepository codeJpaRepository) {
        this.codeJpaRepository = codeJpaRepository;
    }

    @Override
    @Transactional
    public void saveCode(Code code) {
        codeJpaRepository.save(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Code> getAllCodes() {
        return codeJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Code> getCodeById(Long codeId) {
        return codeJpaRepository.findById(codeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Code> getCodeByUserId(Long userId) {
        return codeJpaRepository.getCodeByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteCode(Long codeId) {
        codeJpaRepository.deleteById(codeId);
    }
}
