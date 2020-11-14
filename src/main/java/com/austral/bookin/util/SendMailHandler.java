package com.austral.bookin.util;

import com.austral.bookin.entity.User;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Properties;

public class SendMailHandler {

    public static Session setProperties() {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "bookin.team.austral");
        props.put("mail.smtp.clave", "bookin123");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        return Session.getDefaultInstance(props);
    }

    public static void sendMail(VelocityEngine velocityEngine, Session session, User recipient, String subject, String template, MailStrategy strategy, String[] redirections) {
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("bookin.team.austral"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient.getEmail()));
            message.setSubject(subject);
            VelocityContext velocityContext = new VelocityContext();
            if (strategy == MailStrategy.REGISTER) {
                velocityContext.put("firstName", redirections[0]);
                velocityContext.put("lastName", redirections[1]);
            } else velocityContext.put("url", redirections[0]);
            StringWriter stringWriter = new StringWriter();
            velocityEngine.mergeTemplate(template, "UTF-8", velocityContext, stringWriter);
            message.setContent(stringWriter.toString(), "text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "bookin.team.austral", "bookin123");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
