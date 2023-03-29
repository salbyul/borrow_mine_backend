package com.borrow_mine.BorrowMine.service.chat;

import com.borrow_mine.BorrowMine.ServerEndpointConfig;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.chat.ChatDto;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@ServerEndpoint("/chat/room/{nickname}")
public class WebSocketService {

    private final static Map<String, Session> clients = Collections.synchronizedMap(new HashMap<>());

    private final ChatService chatService = ServerEndpointConfig.getBean(ChatService.class);
    private final MemberRepository memberRepository = ServerEndpointConfig.getBean(MemberRepository.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname) {
        log.info("open session : {}, clients={}", session.toString(), clients);

        if (!clients.containsKey(nickname)) {
            clients.put(nickname, session);
            log.info("session open: {}", session);
        } else {
            log.info("이미 연결된 session");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("receive message : {}", message);

        ObjectMapper objectMapper = new ObjectMapper();
        ChatDto dto = objectMapper.readValue(message, ChatDto.class);
        System.out.println("dto = " + dto);
        System.out.println("from: " + dto.getFrom());
        System.out.println("memberRepository = " + memberRepository);
        Optional<Member> from = memberRepository.findMemberByNickname(dto.getFrom());
        Optional<Member> to = memberRepository.findMemberByNickname(dto.getTarget());
        chatService.saveChatMessage(from.orElseThrow(), to.orElseThrow(), dto.getMessage());

//        if (clients.containsKey(dto.getTarget())) {
//            Session target = clients.get(dto.getTarget());
//            target.getBasicRemote().sendText(dto.getMessage());
//        }
        for (String s : clients.keySet()) {
            Session session1 = clients.get(s);
            session1.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("nickname") String nickname) {
        log.info("session close : {}", session);
        clients.remove(nickname);
    }
}
