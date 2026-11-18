/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

/**
 *
 * @author Student
 */
public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String flag;
    private String sender;

    public Message(String messageID, String recipient, String message, String flag) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
        this.sender = "Developer";
        this.messageHash = createMessageHash();
    }

    public String createMessageHash() {
        String firstTwo = messageID.length() >= 2
                ? messageID.substring(0, 2) : messageID;

        String[] words = message.trim().split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return (firstTwo + ":" + messageID + ":" + firstWord + lastWord)
                .toUpperCase();
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getFlag() {
        return flag;
    }

    public String getSender() {
        return sender;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

