package com.example.attendxbackendv2.ai.controller;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AIController {

    private final OpenAiChatClient chatClient;

    @Autowired
    public AIController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        UserMessage userMessage = new UserMessage(message);
        ChatResponse chatResponse = chatClient.call(new Prompt(List.of(userMessage),
                OpenAiChatOptions.builder().withFunction("DepartmentService").build()));
//        Prompt promot = new Prompt(List.of(new UserMessage(message)), OpenAiChatOptions.builder().withFunction("DepartmentService").build());
//        return chatClient.stream(promot).;
//        new Prompt(message, OpenAiChatOptions.builder().)

        return Map.of("generation", chatResponse.getResults());
    }
}
