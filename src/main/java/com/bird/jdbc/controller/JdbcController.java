package com.bird.jdbc.controller;

import com.bird.jdbc.bean.ChatPhrase;
import com.bird.jdbc.service.ChatPhraseService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class JdbcController {
    @Autowired
    private ChatPhraseService chatPhraseService;

    @PostMapping("save/phrase")
    public void saveChatPhrase(@RequestBody ChatPhrase chatPhrase) {
        chatPhraseService.saveChatPhrase(chatPhrase);
    }

    @GetMapping("set/phrase")
    public void setChatPhrase(HttpServletRequest request) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
    }

    @GetMapping("show/phrase")
    public String showPhrase() {
        return chatPhraseService.getChatPhrase().toString();
    }
}
