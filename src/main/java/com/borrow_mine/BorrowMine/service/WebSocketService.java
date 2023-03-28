package com.borrow_mine.BorrowMine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@ServerEndpoint("/chat")
public class WebSocketService {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        log.info("open session : {}, clients={}", session.toString(), clients);

        if (!clients.contains(session)) {
            clients.add(session);
            log.info("session open: {}", session);
        } else {
            log.info("이미 연결된 session");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("receive message : {}", message);

//        for (Session s : clients) {
//            log.info("send data : {}", message);
//
//            s.getBasicRemote().sendText(message);
//        }
    }

    @OnClose
    public void onClose(Session session) {
        log.info("session close : {}", session);
        clients.remove(session);
    }
}
