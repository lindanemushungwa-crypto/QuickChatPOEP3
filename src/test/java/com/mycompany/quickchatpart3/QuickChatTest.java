/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchatpart3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class QuickChatTest {
    
    @Test
    public void testMessageID() {
        Message message = new Message(
                "0838884567",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertEquals("0838884567", message.getMessageID());
    }

    @Test
    public void testRecipient() {
        Message message = new Message(
                "0838884567",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertEquals("+27834557896", message.getRecipient());
    }

    @Test
    public void testMessageText() {
        Message message = new Message(
                "0838884567",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertEquals("Did you get the cake?", message.getMessage());
    }

    @Test
    public void testMessageFlag() {
        Message message = new Message(
                "0838884567",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertEquals("Sent", message.getFlag());
    }

    @Test
    public void testMessageHash() {
        Message message = new Message(
                "0838884567",
                "+27834557896",
                "Did you get the cake?",
                "Sent"
        );

        assertNotNull(message.getMessageHash());
    }

    @Test
    public void testStoredMessageFlag() {
        Message message = new Message(
                "0838884567",
                "+278338884567",
                "Where are you? You are late! I have asked you to be on time.",
                "Stored"
        );

        assertEquals("Stored", message.getFlag());
    }

    @Test
    public void testDisregardedMessageFlag() {
        Message message = new Message(
                "0838884568",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                "Disregard"
        );

        assertEquals("Disregard", message.getFlag());
    }
}
