package com.borrow_mine.BorrowMine.dto.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatResponse {

    private List<String> chatRoomList;

    public static ChatResponse assembleChatResponse(List<String> chatRoomList) {
        return new ChatResponse(chatRoomList);
    }
}
