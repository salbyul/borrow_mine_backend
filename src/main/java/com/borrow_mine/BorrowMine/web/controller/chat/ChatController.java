package com.borrow_mine.BorrowMine.web.controller.chat;

import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.dto.chat.ChatResponse;
import com.borrow_mine.BorrowMine.dto.chat.ChatRoomResponse;
import com.borrow_mine.BorrowMine.service.chat.ChatPresentationService;
import com.borrow_mine.BorrowMine.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatPresentationService chatPresentationService;

    @GetMapping("/chat-room")
    public ChatRoomResponse getChatRooms(@CookieValue String nickname) {
        List<String> chatRooms = chatService.getChatRooms(nickname);

        return ChatRoomResponse.assembleChatResponse(chatRooms);
    }

    @PutMapping("/chat-room/create")
    public ResponseEntity<Object> createChatRoom(@CookieValue String nickname, @RequestParam("to") String targetMemberNickname) {
        chatService.createChatRoom(nickname, targetMemberNickname);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/log")
    public ChatResponse chattingHistory(@CookieValue String nickname, @RequestParam("to") String targetMemberNickname) {

        List<ChatDto> chatDtoList = chatPresentationService.getChatDtoList(nickname, targetMemberNickname);
        return ChatResponse.assembleChatResponse(chatDtoList);
    }

    @DeleteMapping("/chat-room/delete/{nickname}")
    public ResponseEntity<Object> removeChatRoom(@CookieValue String nickname, @PathVariable("nickname") String targetMemberNickname) {
        chatService.removeChatRoom(nickname, targetMemberNickname);
        return ResponseEntity.ok().build();
    }
}
