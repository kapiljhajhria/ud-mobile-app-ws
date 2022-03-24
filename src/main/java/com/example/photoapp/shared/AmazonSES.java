package com.example.photoapp.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.example.photoapp.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;


public class AmazonSES {

    @Value("${ses}")
    private String FROM;

    final String SUBJECT = "PhotoApp - Confirm your Email";

    final String HTML_BODY = "<h1>Welcome to PhotoApp</h1>"
            + "<p>Please confirm your email by clicking on the link below.</p>"
            + "<a href='http://localhost:8080/verification-service/email-verification.html?token=$token"
            + "&email=$email'>Confirm Email</a>"
            + "<p>Thank you!</p>";


    final String TEXT_BODY = "Welcome to PhotoApp\n"
            + "Please confirm your email by clicking on the link below.\n"
            + "http://localhost:8080/verification-service/email-verification.html?token=$token"
            + "&email=$email\n"
            + "Thank you!\n";

    public void verifyEmail(UserDto userDto) {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();

        String htmlBodyWithTokens = HTML_BODY.replace("$token", userDto.getEmailVerificationToken());
        String textBodyWithTokens = TEXT_BODY.replace("$token", userDto.getEmailVerificationToken());

        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                .withMessage(new Message().withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithTokens)).withText(
                        new Content().withCharset("UTF-8").withData(textBodyWithTokens))).withSubject(new Content().withCharset("UTF-8").withData(SUBJECT))).withSource(FROM);

        client.sendEmail(request);
//        log.info("Confirmation Email sent to: " + userDto.getEmail());

    }
}