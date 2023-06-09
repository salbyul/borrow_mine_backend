package com.borrow_mine.BorrowMine.dto.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatResponse {

    private List<ChatDto> chatList = new ArrayList<>();

    public static ChatResponse assembleChatResponse(List<ChatDto> chatList) {
        return new ChatResponse(chatList);
    }
}
