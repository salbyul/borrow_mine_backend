package com.borrow_mine.BorrowMine.service.chat;

import com.borrow_mine.BorrowMine.domain.Deny;
import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private final MemberService memberService;

    public List<ChatDto> getChatDtoList(String fromMemberNickname, String toMemberNickname) {
        Optional<Member> optionalFromMember = memberRepository.findMemberByNickname(fromMemberNickname);
        Optional<Member> optionalToMember = memberRepository.findMemberByNickname(toMemberNickname);
        Member fromMember = optionalFromMember.orElseThrow(MemberException::new);
        Member toMember = optionalToMember.orElseThrow(MemberException::new);

        Optional<Deny> deny = memberService.findDeny(fromMember, toMember);
        if (deny.isPresent()) {
            List<Chat> findChatList = chatRepository.findChatListByFromAndToBeforeTime(fromMember, toMember, deny.get().getCreatedDate());
            List<ChatDto> result = new ArrayList<>();

            for (Chat chat : findChatList) {
                result.add(ChatDto.chatToDto(chat));
            }
            return result;
        }
        List<Chat> findChatList = chatRepository.findChatListByFromAndToBeforeTime(fromMember, toMember, LocalDateTime.now());
        List<ChatDto> result = new ArrayList<>();

        for (Chat chat : findChatList) {
            result.add(ChatDto.chatToDto(chat));
        }

        return result;
    }
}
