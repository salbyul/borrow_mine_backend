package com.borrow_mine.BorrowMine.service.chat;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.repository.chat.ChatRepository;
import com.borrow_mine.BorrowMine.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatPresentationService {

    private final ChatRepository chatRepository;
    private final MemberService memberService;

    public List<ChatDto> getChatDtoList(Member from, Member to) {
        Optional<Deny> deny = memberService.findDeny(from, to);
        if (deny.isPresent()) {
            List<Chat> findChatList = chatRepository.findChatListByFromAndToBeforeTime(from, to, deny.get().getCreatedDate());
            List<ChatDto> result = new ArrayList<>();

            for (Chat chat : findChatList) {
                result.add(ChatDto.chatToDto(chat));
            }
            return result;
        }
        List<Chat> findChatList = chatRepository.findChatListByFromAndToBeforeTime(from, to, LocalDateTime.now());
        List<ChatDto> result = new ArrayList<>();

        for (Chat chat : findChatList) {
            result.add(ChatDto.chatToDto(chat));
        }

        return result;
    }
}
