package com.TicketStream.backend.listener;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class NotificationListener {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @KafkaListener(topics = "user-notifications", groupId = "notification-group")
    public void listenNotifications(String message) {
        sendEmail("pentex518@gmail.com", "Ticket Status Update", message);
        
    }

    public String sendEmail(String toEmail, String subject, String body) {
        Email from = new Email("arvindrao100@gmail.com"); // Replace with your verified sender email
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());

            return "Email sent successfully. Status code: " + response.getStatusCode();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error sending email: " + ex.getMessage();
        }
    }
}
