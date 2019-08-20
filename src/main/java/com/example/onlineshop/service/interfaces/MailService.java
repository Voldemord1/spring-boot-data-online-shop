package com.example.onlineshop.service.interfaces;

import com.example.onlineshop.model.Code;

public interface MailService {

    void sendConfirmCode(Code code);
}
