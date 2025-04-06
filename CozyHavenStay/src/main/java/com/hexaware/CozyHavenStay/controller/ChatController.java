package com.hexaware.CozyHavenStay.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @PostMapping("/message")
    public Map<String, String> handleMessage(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String reply = generateReply(userMessage.toLowerCase());

        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return response;
    }

    private String generateReply(String message) {
        if (message.contains("room") || message.contains("availability")) {
            return "We currently have Deluxe and Suite rooms available. Would you like to book one?";
        } else if (message.contains("check-in") || message.contains("check out")) {
            return "Check-in is from 12 PM and check-out is until 11 AM.";
        } else if (message.contains("amenities")) {
            return "We offer free Wi-Fi, a swimming pool, gym, and complimentary breakfast.";
        } else if (message.contains("book")) {
            return "You can book a room through the 'Book Now' page. Need help navigating?";
        } else if (message.contains("contact")) {
            return "You can reach us at +91-9876543210 or hotel@example.com.";
        } else {
            return "I'm sorry, could you please rephrase your question?";
        }
    }
}
