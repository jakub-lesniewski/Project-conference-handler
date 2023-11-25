package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.model.Participant;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${accountCreationMessage.subject}")
    private String accountCreationSubject;

    public void sendAccountCreationMessage(Participant participant) {

        Context context = new Context();
        context.setVariable("participant", participant);

        sendEmailWithHtmlTemplate(participant.getEmail(), accountCreationSubject, "accountCreationMessage", context);
    }

    private void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
        LOGGER.info("Sending email to {}", to);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            LOGGER.info("Successfully sent an email to {}", to);
        } catch (MessagingException e) {
            LOGGER.error("Could not send email to {}", to, e);
        }
    }
}
