package com.borrow_mine.BorrowMine.dto.chat;

import com.borrow_mine.BorrowMine.domain.chat.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatDto {

    private String type;
    private String message;
    private String imageName;
    private String from;
    private String target;
    private LocalDateTime sentTime;
    private byte[] image;

    public static ChatDto chatToDto(Chat chat) {
        return new ChatDto(chat.getType().toString(), chat.getContent(), chat.getImageName(), chat.getFrom().getNickname(), chat.getTo().getNickname(), chat.getSentTime(), null);
    }

    public static ChatDto transformFromTo(ChatDto chatDto) {
        return new ChatDto(chatDto.getType(), chatDto.message, chatDto.imageName, chatDto.getTarget(), chatDto.getFrom(), chatDto.getSentTime(), chatDto.getImage());
    }
}
