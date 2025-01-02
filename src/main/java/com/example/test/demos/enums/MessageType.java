package com.example.test.demos.enums;

public enum MessageType {
    TEXT,
    SYSTEM_EVENT_LOGIN,
    SYSTEM_EVENT_PUSH_NOTIFY;

    public static MessageType fromString(String type) {
        if (type == null) return null;
        return switch (type.toUpperCase()) {
            case "TEXT" -> TEXT;
            case "SYSTEM_EVENT_LOGIN" -> SYSTEM_EVENT_LOGIN;
            case "SYSTEM_EVENT_PUSH_NOTIFY" -> SYSTEM_EVENT_PUSH_NOTIFY;
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }
}