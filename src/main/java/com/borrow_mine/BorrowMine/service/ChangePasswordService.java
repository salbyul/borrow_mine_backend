package com.borrow_mine.BorrowMine.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChangePasswordService {

    private static Map<String, Component> session = new ConcurrentHashMap<>();

    @AllArgsConstructor
    static class Component {
        private LocalDateTime time;
        private String nickname;
    }

//    요청이 들어올 때마다 세션을 청소한다.
    public String save(String nickname) {

        cleanSession();
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();
        Component component = new Component(now, nickname);
        session.put(uuid, component);
        return uuid;
    }

    public Optional<String> getMemberNickname(String uuid) {

        Component component = session.get(uuid);
        String nickname = component.nickname;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus = component.time.plusHours(1);
        if (now.isAfter(plus)) {
            session.remove(uuid);
            throw new IllegalStateException("시간 초과");
        }
        session.remove(uuid);

        return Optional.of(nickname);
    }

    public void cleanSession() {

        Set<String> keySet = session.keySet();

        for (String s : keySet) {
            Component component = session.get(s);
            if (component != null) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime plus = component.time.plusHours(1);
                if (now.isAfter(plus)) {
                    session.remove(s);
                }
            }
        }
    }
}
