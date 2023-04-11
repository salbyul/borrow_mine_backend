package com.borrow_mine.BorrowMine.service.chat;

import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.chat.ChatRoom;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.exception.ChatException;
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.chat.ChatRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.borrow_mine.BorrowMine.exception.ChatException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private final EntityManager em;

    public List<String> getChatRooms(String nickname) {
        Optional<Member> findMember = memberRepository.findMemberByNickname(nickname);
        return chatRepository.findChatRoomList(findMember.orElseThrow(MemberException::new));
    }

    @Transactional
    public void createChatRoom(String fromMemberNickname, String toMemberNickname) {
        Optional<Member> optionalFromMember = memberRepository.findMemberByNickname(fromMemberNickname);
        Optional<Member> optionalToMember = memberRepository.findMemberByNickname(toMemberNickname);
        Member fromMember = optionalFromMember.orElseThrow(MemberException::new);
        Member toMember = optionalToMember.orElseThrow(MemberException::new);
        validate(fromMember, toMember);
        em.persist(ChatRoom.createChatRoom(fromMember, toMember));
    }

    @Transactional
    public void saveChatMessage(String fromMemberNickname, String toMemberNickname, String message) {
        Optional<Member> optionalFromMember = memberRepository.findMemberByNickname(fromMemberNickname);
        Optional<Member> optionalToMember = memberRepository.findMemberByNickname(toMemberNickname);
        Member fromMember = optionalFromMember.orElseThrow(MemberException::new);
        Member toMember = optionalToMember.orElseThrow(MemberException::new);
        Optional<ChatRoom> findChatRoom = chatRepository.findChatRoomByFromAndTo(toMember, fromMember);
        if (findChatRoom.isEmpty()) {
            createChatRoom(toMemberNickname, fromMemberNickname);
        }
        Chat chat = Chat.assembleChatMessage(fromMember, toMember, message);
        chatRepository.save(chat);
    }

    @Transactional
    public void saveChatImage(Member from, Member to, String imageName) {
        Chat chat = Chat.assembleChatImage(from, to, imageName);
        chatRepository.save(chat);
    }


    @Transactional
    public void removeChatRoom(String fromMemberNickname, String toMemberNickname) {
        Optional<Member> optionalFromMember = memberRepository.findMemberByNickname(fromMemberNickname);
        Optional<Member> optionalToMember = memberRepository.findMemberByNickname(toMemberNickname);
        chatRepository.deleteChatRoomByFromAndTo(optionalFromMember.orElseThrow(MemberException::new), optionalToMember.orElseThrow(MemberException::new));
    }

    private void validate(Member from, Member to) {
        if (from == to) throw new ChatException(ONESELF_ERROR);
        Optional<ChatRoom> findChatRoom = chatRepository.findChatRoomByFromAndTo(from, to);
        if (findChatRoom.isPresent()) throw new ChatException(DUPLICATE_CHAT_ROOM);
    }
}
