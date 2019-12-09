package com.gmailatcj92robert.springsecuritylearning.services;

public interface MailService {
    void sendActivationKey(long userId);

    void sendPasswordForgetKey(long userId);

    void sendMailToUser(String email, String content, String subject);
}
