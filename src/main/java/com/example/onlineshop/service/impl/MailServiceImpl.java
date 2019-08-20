package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.Code;
import com.example.onlineshop.service.interfaces.MailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = Logger.getLogger(MailService.class);

    @Override
    public void sendConfirmCode(Code code) {
        final String mailFrom = "mate.acamemy.shop@gmail.com";
        final String password = "test12345test";
        final String mailTo = "myrvadim@gmail.com";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailFrom, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.setSubject("Confirm code");
            message.setText("Confirm code from mate.acamemy.shop " + code.getValue());

            Transport.send(message);
            logger.info("Message with confirm code send to..." + mailTo);
        } catch (MessagingException e) {
            logger.error("MessagingException " + e.getMessage());
        }
    }
}
