package com.borrow_mine.BorrowMine.dto.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomResponse {

    private List<String> chatRoomList;

    public static ChatRoomResponse assembleChatResponse(List<String> chatRoomList) {
        return new ChatRoomResponse(chatRoomList);
    }
}
