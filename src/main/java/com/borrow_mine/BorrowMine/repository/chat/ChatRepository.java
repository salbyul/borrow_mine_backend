package com.borrow_mine.BorrowMine.repository.chat;

import com.borrow_mine.BorrowMine.domain.chat.Chat;
import com.borrow_mine.BorrowMine.domain.chat.ChatRoom;
import com.borrow_mine.BorrowMine.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {

    @Query("select cr.to.nickname from ChatRoom cr where cr.from = :from")
    List<String> findChatRoomList(@Param("from") Member from);

    @Query("select cr from ChatRoom cr where cr.from = :from and cr.to = :to")
    Optional<ChatRoom> findChatRoomByFromAndTo(@Param("from") Member from, @Param("to") Member to);

    @Query("select c from Chat c left join fetch c.to where (c.from = :from and c.to = :to) or (c.from = :to and c.to = :from) order by c.sentTime")
    List<Chat> findChatByFromAndTo(@Param("from") Member from, @Param("to") Member to);
}
