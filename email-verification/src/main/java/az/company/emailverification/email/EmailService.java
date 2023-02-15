package az.company.emailverification.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
private final JavaMailSender mailSender;

    private static final Logger LOGGER= LoggerFactory.getLogger(EmailService.class);

    @Override
    @Async
    public void send(String to, String email) {
try {
    MimeMessage message=mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message,"utf-8");
    mimeMessageHelper.setText(email,true);
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject("qulu2003@gmai.com");
    mailSender.send(message);
}catch (MessagingException ex){
LOGGER.error("failed send to mail",ex);
    throw new IllegalStateException("failed send to mail");
}
    }
}
