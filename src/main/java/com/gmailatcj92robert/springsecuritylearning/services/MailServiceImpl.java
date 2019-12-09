package com.gmailatcj92robert.springsecuritylearning.services;

import com.gmailatcj92robert.springsecuritylearning.models.User;
import com.gmailatcj92robert.springsecuritylearning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {
    private String urlWeb = "localhost";
    private Random random = new Random();
    private JavaMailSender sender;
    private UserRepository userRepository;

    @Autowired
    public MailServiceImpl(JavaMailSender sender, UserRepository userRepository) {
        this.sender = sender;
        this.userRepository = userRepository;
    }

    @Override
    public void sendActivationKey(long userId) {

        long activationKey = random.nextLong();
        if (userRepository.findById(userId).isPresent()) {

            User user = userRepository.findById(userId).get();
            user.setActivationKey(activationKey);
            userRepository.save(user);

            String message = "Jeśli założyłes konto w serwisie "
                    + urlWeb
                    + " kliknij w link http://" + urlWeb
                    + "/" + user.getId()
                    + "/a/" + activationKey
                    + " by aktywować. /n   Jesli nie zakładałeś u nas konta zignoruj mail.";
            String subject = "Aktywacja użytkownika";
            sendMailToUser(user.getEmail(), message, subject);

        }
    }

    @Override
    public void sendPasswordForgetKey(long userId) {


        long passwordRemindKey = random.nextLong();
        if (userRepository.findById(userId).isPresent()) {

            User user = userRepository.findById(userId).get();
            user.setRemindKey(passwordRemindKey);
            userRepository.save(user);

            String message = "Jeśli chcesz zmienić hasło w "
                    + urlWeb
                    + " kliknij w link http://" + urlWeb
                    + "/" + user.getId()
                    + "/p/" + passwordRemindKey
                    + "    Jesli nie chciałeś zmienć hasła zignoruj mail.";
            String subject = "Aktywacja użytkownika";
            sendMailToUser(user.getEmail(), message, subject);

        }

    }

    @Override
    public void sendMailToUser(String email, String content, String subject) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {

            helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setText(content);
            helper.setSubject(subject);
            sender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
