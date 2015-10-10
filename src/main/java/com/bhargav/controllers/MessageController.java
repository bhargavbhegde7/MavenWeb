package com.bhargav.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.bhargav.domain.Message;
import com.bhargav.domain.User;

@RestController
public class MessageController {
	
	@MessageMapping("/hello")
    @SendTo("/users/greetings")
    public Message greeting(User user) throws Exception {
		System.out.println("Came here");
        Thread.sleep(3000); // simulated delay
        return new Message("Hello, " + user.getName() + "!");
    }
}
