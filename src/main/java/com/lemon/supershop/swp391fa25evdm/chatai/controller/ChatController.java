package com.lemon.supershop.swp391fa25evdm.chatai.controller;

import com.lemon.supershop.swp391fa25evdm.chatai.model.dto.ChatReq;
import com.lemon.supershop.swp391fa25evdm.chatai.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public String chat (@RequestBody ChatReq request) {
        return chatService.chat(request);
    }
}
