package com.example.onlineshop.service.interfaces;

import com.example.onlineshop.model.Code;

import java.util.List;
import java.util.Optional;

public interface CodeService {

    void saveCode(Code code);

    List<Code> getAllCodes();

    Optional<Code> getCodeByUserId(Long userId);

    Optional<Code> getCodeById(Long codeId);

    void deleteCode(Long codeId);
}
