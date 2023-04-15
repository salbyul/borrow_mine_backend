package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.Bookmark;
import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.exception.BorrowPostException;
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.BookmarkRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final BorrowPostRepository borrowPostRepository;

    @Transactional
    public void saveBookmark(String nickname, Long borrowPostId) {
        if (isBookmark(nickname, borrowPostId)) {
            throw new MemberException(MemberException.DUPLICATE_BOOKMARK);
        }
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Member member = optionalMember.orElseThrow(MemberException::new);

        Optional<BorrowPost> optionalBorrowPost = borrowPostRepository.findById(borrowPostId);
        BorrowPost borrowPost = optionalBorrowPost.orElseThrow(BorrowPostException::new);
        Bookmark bookmark = new Bookmark(member, borrowPost);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(String nickname, Long borrowPostId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByMemberNicknameAndBorrowPostId(nickname, borrowPostId);
        Bookmark bookmark = optionalBookmark.orElseThrow();
        bookmarkRepository.delete(bookmark);
    }

    public Boolean isBookmark(String nickname, Long borrowPostId) {
        if (nickname == null) return null;
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByMemberNicknameAndBorrowPostId(nickname, borrowPostId);
        return optionalBookmark.isPresent();
    }
}
