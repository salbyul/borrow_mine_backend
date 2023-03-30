package com.borrow_mine.BorrowMine.web.controller.chat;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.dto.chat.ChatResponse;
import com.borrow_mine.BorrowMine.dto.chat.ChatRoomResponse;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.chat.ChatPresentationService;
import com.borrow_mine.BorrowMine.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatPresentationService chatPresentationService;
    private final MemberRepository memberRepository;

    @GetMapping("/chat-room")
    public ChatRoomResponse getChatRooms(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");

        List<String> chatRooms = chatService.getChatRooms(memberRepository.findMemberByNickname(nickname).orElseThrow());

        return ChatRoomResponse.assembleChatResponse(chatRooms);
    }

    @PutMapping("/chat-room/create")
    public ResponseEntity<Object> createChatRoom(HttpServletRequest request, @RequestParam("to") String target) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findFromMember = memberRepository.findMemberByNickname(nickname);
        Optional<Member> findToMember = memberRepository.findMemberByNickname(target);
        chatService.createChatRoom(findFromMember.orElseThrow(), findToMember.orElseThrow());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/log")
    public ChatResponse chattingHistory(HttpServletRequest request, @RequestParam("to") String target) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        Optional<Member> targetMember = memberRepository.findMemberByNickname(target);

        List<ChatDto> chatDtoList = chatPresentationService.getChatDtoList(findMember.orElseThrow(), targetMember.orElseThrow());
        return ChatResponse.assembleChatResponse(chatDtoList);
    }

    @DeleteMapping("/chat-room/delete/{nickname}")
    public ResponseEntity<Object> removeChatRoom(HttpServletRequest request, @PathVariable String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname((String) request.getAttribute("nickname"));
        Optional<Member> target = memberRepository.findMemberByNickname(nickname);
        chatService.removeChatRoom(findMember.orElseThrow(), target.orElseThrow());
        return ResponseEntity.ok().build();
    }
}
