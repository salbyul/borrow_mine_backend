package com.borrow_mine.BorrowMine.web.controller.chat;

import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatResponse;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.service.ChatService;
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
    private final MemberRepository memberRepository;

    @GetMapping("/chat-room")
    public ChatResponse getChatRooms(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");

        List<String> chatRooms = chatService.getChatRooms(memberRepository.findMemberByNickname(nickname).orElseThrow());

        return ChatResponse.assembleChatResponse(chatRooms);
    }

    @PutMapping("/chat-room/create")
    public ResponseEntity<Object> createChatRoom(HttpServletRequest request, @RequestParam("to") String target) {
        String nickname = (String) request.getAttribute("nickname");
        Optional<Member> findFromMember = memberRepository.findMemberByNickname(nickname);
        Optional<Member> findToMember = memberRepository.findMemberByNickname(target);
        chatService.createChatRoom(findFromMember.orElseThrow(), findToMember.orElseThrow());
        return ResponseEntity.ok().build();
    }
}
