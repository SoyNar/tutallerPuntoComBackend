package com.tutallerPuntoCom.generation.Services.Contracts;

import java.util.Map;
import java.util.Objects;

public interface EmailServiceI {

    void sendTemplate(String to, String subject, String templateName, Map<String, Object> variable);
}
