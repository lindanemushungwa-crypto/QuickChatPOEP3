/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchatpart3;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class QuickChat {
    
    private Message[] sentMessages = new Message[1000];
    private Message[] disregardedMessages = new Message[1000];
    private Message[] storedMessages = new Message[1000];
    private String[] messageHashes = new String[1000];
    private String[] messageIDs = new String[1000];

    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;

    private Scanner input = new Scanner(System.in);

    private final String FILE_NAME = "stored_messages.json";

    public static void main(String[] args) {
        QuickChat app = new QuickChat();
        app.start();
    }

    public void start() {

        loadStoredMessages();

        int choice;

        do {
            System.out.println("\n===== QUICKCHAT =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            choice = readNumber();

            switch (choice) {

                case 1:
                    sendMessage();
                    break;

                case 2:
                    displaySentMessages();
                    break;

                case 3:
                    System.out.println("Thank you for using QuickChat.");
                    break;

                case 4:
                    storedMessagesMenu();
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 3);
    }

    private int readNumber() {
        while (!input.hasNextInt()) {
            System.out.print("Please enter a number: ");
            input.nextLine();
        }

        int number = input.nextInt();
        input.nextLine();
        return number;
    }

    private void sendMessage() {

        System.out.print("Enter message ID: ");
        String id = input.nextLine();

        System.out.print("Enter recipient cellphone number: ");
        String recipient = input.nextLine();

        System.out.print("Enter your message: ");
        String text = input.nextLine();

        if (text.length() > 250) {
            System.out.println("Message is too long. Maximum is 250 characters.");
            return;
        }

        System.out.println("\nChoose an action:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");

        int action = readNumber();

        String flag;

        if (action == 1) {
            flag = "Sent";
        } else if (action == 2) {
            flag = "Disregard";
        } else if (action == 3) {
            flag = "Stored";
        } else {
            System.out.println("Invalid action.");
            return;
        }

        Message message = new Message(id, recipient, text, flag);

        if (flag.equals("Sent")) {
            sentMessages[sentCount] = message;
            messageIDs[sentCount] = message.getMessageID();
            messageHashes[sentCount] = message.getMessageHash();
            sentCount++;

            System.out.println("Message successfully sent.");

        } else if (flag.equals("Disregard")) {
            disregardedMessages[disregardedCount] = message;
            disregardedCount++;

            System.out.println("Message disregarded.");

        } else {
            storedMessages[storedCount] = message;
            storedCount++;
            saveStoredMessages();

            System.out.println("Message successfully stored.");
        }
    }

    private void displaySentMessages() {

        if (sentCount == 0) {
            System.out.println("No sent messages available.");
            return;
        }

        for (int i = 0; i < sentCount; i++) {
            displayMessage(sentMessages[i]);
        }
    }

    private void storedMessagesMenu() {

        int choice;

        do {
            System.out.println("\n===== STORED MESSAGES =====");
            System.out.println("1. Display sender and recipient");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search messages by recipient");
            System.out.println("5. Delete a message using its hash");
            System.out.println("6. Display full message report");
            System.out.println("7. Back to main menu");
            System.out.print("Choose an option: ");

            choice = readNumber();

            switch (choice) {

                case 1:
                    displaySendersAndRecipients();
                    break;

                case 2:
                    displayLongestMessage();
                    break;

                case 3:
                    searchByID();
                    break;

                case 4:
                    searchByRecipient();
                    break;

                case 5:
                    deleteByHash();
                    break;

                case 6:
                    displayReport();
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 7);
    }

    private void displaySendersAndRecipients() {

        if (storedCount == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        for (int i = 0; i < storedCount; i++) {
            System.out.println("Sender: " + storedMessages[i].getSender());
            System.out.println("Recipient: " + storedMessages[i].getRecipient());
            System.out.println("-------------------------");
        }
    }

    private void displayLongestMessage() {

        if (storedCount == 0) {
            System.out.println("No stored messages available.");
            return;
        }

        Message longest = storedMessages[0];

        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].getMessage().length()
                    > longest.getMessage().length()) {
                longest = storedMessages[i];
            }
        }

        System.out.println("Longest stored message:");
        System.out.println(longest.getMessage());
    }

    private void searchByID() {

        System.out.print("Enter message ID: ");
        String id = input.nextLine();

        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getMessageID().equals(id)) {
                System.out.println("Recipient: " + storedMessages[i].getRecipient());
                System.out.println("Message: " + storedMessages[i].getMessage());
                return;
            }
        }

        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getMessageID().equals(id)) {
                System.out.println("Recipient: " + sentMessages[i].getRecipient());
                System.out.println("Message: " + sentMessages[i].getMessage());
                return;
            }
        }

        System.out.println("Message ID not found.");
    }

    private void searchByRecipient() {

        System.out.print("Enter recipient cellphone number: ");
        String recipient = input.nextLine();

        boolean found = false;

        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].getRecipient().equals(recipient)) {
                System.out.println(sentMessages[i].getMessage());
                found = true;
            }
        }

        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getRecipient().equals(recipient)) {
                System.out.println(storedMessages[i].getMessage());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }

    private void deleteByHash() {

        System.out.print("Enter message hash: ");
        String hash = input.nextLine();

        for (int i = 0; i < storedCount; i++) {

            if (storedMessages[i].getMessageHash().equals(hash)) {

                System.out.println("Message: " + storedMessages[i].getMessage()
                        + " successfully deleted.");

                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                }

                storedMessages[storedCount - 1] = null;
                storedCount--;

                saveStoredMessages();
                return;
            }
        }

        System.out.println("Message hash not found.");
    }

    private void displayReport() {

        if (sentCount == 0) {
            System.out.println("No sent messages available.");
            return;
        }

        System.out.println("\n===== SENT MESSAGE REPORT =====");

        for (int i = 0; i < sentCount; i++) {
            displayMessage(sentMessages[i]);
        }
    }

    private void displayMessage(Message message) {

        System.out.println("\nMessage ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Sender: " + message.getSender());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessage());
        System.out.println("Status: " + message.getFlag());
    }

    private void saveStoredMessages() {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            writer.write("[\n");

            for (int i = 0; i < storedCount; i++) {

                Message m = storedMessages[i];

                writer.write("  {");
                writer.write("\"messageID\":\"" + escapeJSON(m.getMessageID()) + "\",");
                writer.write("\"recipient\":\"" + escapeJSON(m.getRecipient()) + "\",");
                writer.write("\"message\":\"" + escapeJSON(m.getMessage()) + "\",");
                writer.write("\"flag\":\"Stored\"");
                writer.write("}");

                if (i < storedCount - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

        } catch (IOException e) {
            System.out.println("Could not save messages: " + e.getMessage());
        }
    }

    private void loadStoredMessages() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (Scanner fileInput = new Scanner(file)) {

            StringBuilder json = new StringBuilder();

            while (fileInput.hasNextLine()) {
                json.append(fileInput.nextLine());
            }

            Pattern pattern = Pattern.compile(
                    "\\{\"messageID\":\"(.*?)\",\"recipient\":\"(.*?)\","
                    + "\"message\":\"(.*?)\",\"flag\":\"Stored\"\\}"
            );

            Matcher matcher = pattern.matcher(json.toString());

            while (matcher.find() && storedCount < storedMessages.length) {

                String id = unescapeJSON(matcher.group(1));
                String recipient = unescapeJSON(matcher.group(2));
                String message = unescapeJSON(matcher.group(3));

                storedMessages[storedCount] =
                        new Message(id, recipient, message, "Stored");

                storedCount++;
            }

        } catch (IOException e) {
            System.out.println("Could not load stored messages.");
        }
    }

    private String escapeJSON(String text) {
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }

    private String unescapeJSON(String text) {
        return text.replace("\\n", "\n")
                   .replace("\\r", "\r")
                   .replace("\\\"", "\"")
                   .replace("\\\\", "\\");
    }
}

