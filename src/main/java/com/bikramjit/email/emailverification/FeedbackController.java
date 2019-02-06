package com.bikramjit.email.emailverification;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailConfigration emailConfigration;


    public FeedbackController(EmailConfigration emailConfigration){
        this.emailConfigration=emailConfigration;
    }


    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback,
                                        BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.out.println("this is not a valid feedback ");
        }

        //create e mail sender

        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(emailConfigration.getHost());
        javaMailSender.setPort(emailConfigration.getPort());
        javaMailSender.setUsername(emailConfigration.getUsername());
        javaMailSender.setPassword(emailConfigration.getPassword());
        javaMailSender.getJavaMailProperties();

        //this is for no use just added to see the changes in the file in the github repository

        //create simple email message

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("bikramjitsingh@feedback.com");
        mailMessage.setTo(feedback.getEmail());
        mailMessage.setSubject("new feedback from "+feedback.getName());
        mailMessage.setText(feedback.getFeedback());


        Properties mailProps = new Properties();
        mailProps.put("mail.smtps.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.debug", "true");

        javaMailSender.setJavaMailProperties(mailProps);

        //send the email
        javaMailSender.send(mailMessage);


    }


}
