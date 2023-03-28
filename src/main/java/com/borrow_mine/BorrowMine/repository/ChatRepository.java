package com.borrow_mine.BorrowMine.repository;

import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.chat.ChatRoom;
import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select cr.to.nickname from ChatRoom cr where cr.from = :from")
    List<String> findChattingRooms(@Param("from") Member from);

    @Query("select cr from ChatRoom cr where cr.from = :from and cr.to = :to")
    Optional<ChatRoom> findByFromAndTo(@Param("from") Member from, @Param("to") Member to);
}
