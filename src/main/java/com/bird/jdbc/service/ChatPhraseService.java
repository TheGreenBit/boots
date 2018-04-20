package com.bird.jdbc.service;

import com.bird.jdbc.bean.ChatPhrase;

import java.util.List;

public interface ChatPhraseService {
    void saveChatPhrase(ChatPhrase chatPhrase);

    List<ChatPhrase> getChatPhrase();
}
