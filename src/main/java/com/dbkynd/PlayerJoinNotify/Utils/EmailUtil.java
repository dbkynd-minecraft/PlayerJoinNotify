package com.dbkynd.playerjoinnotify.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    public static void sendEmail(org.bukkit.configuration.Configuration config, String player, Boolean isTest) {
        parseConfig(player, isTest, config.getString("toEmail"), config.getString("fromEmail"), config.getString("emailSubject"), config.getString("smtpHost"), config.getInt("port"), config.getBoolean("useTLS"), config.getString("smtpPassword"));
    }

    public static void sendEmail(net.md_5.bungee.config.Configuration config, String player, Boolean isTest) {
        parseConfig(player, isTest, config.getString("toEmail"), config.getString("fromEmail"), config.getString("emailSubject"), config.getString("smtpHost"), config.getInt("port"), config.getBoolean("useTLS"), config.getString("smtpPassword"));
    }

    private static void parseConfig(String player, Boolean isTest, String toEmail, String fromEmail, String subject, String host, Integer port, boolean useTLS, String password) {
        String body = player + " has joined the server!";
        if (isTest) body = "This is a test message. TLS: " + (useTLS ? "True" : "False");
        send(toEmail, fromEmail, subject, body, host, port, useTLS, password);
    }

    private static void send(String toEmail, String fromEmail, String subject, String body, String host, Integer port, Boolean useTLS, String password) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            if (useTLS) props.put("mail.smtp.starttls.enable", "true");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
