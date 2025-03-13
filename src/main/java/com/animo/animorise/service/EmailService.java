package com.animo.animorise.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}