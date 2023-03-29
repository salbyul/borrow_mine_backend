package com.borrow_mine.BorrowMine.service.chat;

import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatPresentationService {

    private final ChatRepository chatRepository;

    public List<ChatDto> getChatDtoList(Member from, Member to) {
        List<Chat> findChatList = chatRepository.findChatByFromAndTo(from, to);
        List<ChatDto> result = new ArrayList<>();

        for (Chat chat : findChatList) {
            result.add(ChatDto.chatToDto(chat));
        }

        return result;
    }
}
