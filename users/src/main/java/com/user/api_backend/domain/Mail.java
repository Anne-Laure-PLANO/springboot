package com.example.users.domain;

public class Mail {

    private String mail;

    public Mail(String mail){
        this.mail = mail.toLowerCase();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
