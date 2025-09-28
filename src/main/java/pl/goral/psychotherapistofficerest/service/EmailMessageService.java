package pl.goral.psychotherapistofficerest.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.EmailMessageDto;
import pl.goral.psychotherapistofficerest.mapper.EmailMessageMapper;
import pl.goral.psychotherapistofficerest.model.EmailMessage;
import pl.goral.psychotherapistofficerest.repository.EmailMessageRepository;
import pl.goral.psychotherapistofficerest.utils.ConfigProvider;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailMessageService {

    private final EmailMessageRepository emailMessageRepository;
    private final EmailMessageMapper emailMessageMapper;

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String email = ConfigProvider.get("gmailcredentials.email");
        String password = ConfigProvider.get("gmailcredentials.password");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        return Session.getInstance(props, authenticator);
    }

    public String sendResetPasswordMail(String recipientEmail, String resetLink) {
        String subject = "Reset hasła - Gabinet Psychoterapeutyczny";
        String body = "Dzień dobry,\n\nKliknij link poniżej, aby zresetować hasło:\n" + resetLink
                + "\n\nPozdrawiam,\nGabinet Psychoterapeutyczny";
        EmailMessage messageEntity = EmailMessage.builder()
                .recipient(recipientEmail)
                .subject(subject)
                .body(body)
                .type("RESET_PASSWORD")
                .status("pending")
                .build();

        try {
            Message message = new MimeMessage(createSession());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            messageEntity.setStatus("success");
        } catch (Exception e) {
            messageEntity.setStatus("error");
        }
        emailMessageRepository.save(messageEntity);
        return messageEntity.getStatus();
    }

    public void sendVisitReminderMail(String recipientEmail, String visitDate, String visitTime) {
        String subject = "Przypomnienie o wizycie - Gabinet Psychoterapeutyczny";
        String body = String.format(
                "Dzień dobry,\n\nPrzypominamy o wizycie w gabinecie psychoterapeutycznym:\nData: %s\nGodzina: %s\n\nDo zobaczenia!",
                visitDate, visitTime
        );
        EmailMessage messageEntity = EmailMessage.builder()
                .recipient(recipientEmail)
                .subject(subject)
                .body(body)
                .type("VISIT_REMINDER")
                .status("pending")
                .build();

        try {
            Message message = new MimeMessage(createSession());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            messageEntity.setStatus("success");
        } catch (Exception e) {
            messageEntity.setStatus("error");
        }
        emailMessageRepository.save(messageEntity);
    }}