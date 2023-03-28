package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.chat.ChatRoom;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.repository.ChatRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final EntityManager em;

    public List<String> getChatRooms(Member member) {
        return chatRepository.findChattingRooms(member);
    }

    @Transactional
    public void createChatRoom(Member from, Member to) {
        validate(from, to);
        em.persist(ChatRoom.createChatRoom(from, to));
    }

    private void validate(Member from, Member to) {
        if (from == to) throw new IllegalStateException("자신과의 채팅");
        Optional<ChatRoom> findChatRoom = chatRepository.findByFromAndTo(from, to);
        if (findChatRoom.isPresent()) throw new DuplicateRequestException("중복");

    }
}
