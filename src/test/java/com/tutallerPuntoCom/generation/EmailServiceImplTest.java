package com.tutallerPuntoCom.generation;

import com.tutallerPuntoCom.generation.Services.Contracts.EmailServiceI;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Map;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailServiceImplTest {


    @Autowired
    private  EmailServiceI emailServiceI;
    @MockitoBean
    private JavaMailSender javaMailSender;

    @Test
    void sendEmailTest() {
        MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailServiceI.sendTemplate(
                "cliente@ejemplo.com",
                "Prueba de correo",
                "appointment-confirmed",
                Map.of(
                        "name", "Juan",
                        "workshop", "Tu Taller Medellín",
                        "date", "20 de noviembre, 10:00 a.m."
                )
        );

            verify(javaMailSender, timeout(1000)).send(any(MimeMessage.class));
    }



}
