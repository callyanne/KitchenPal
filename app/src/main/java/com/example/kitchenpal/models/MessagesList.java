package com.example.kitchenpal.models;

public class MessagesList {

    private String username, lastMessage, profilePic, chatKey;
    private int unseenMessages;

    public MessagesList(String username, String lastMessage, int unseenMessages, String chatKey) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.chatKey = chatKey;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getChatKey() {
        return chatKey;
    }
}
