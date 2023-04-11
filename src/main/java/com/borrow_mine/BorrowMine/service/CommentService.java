package com.borrow_mine.BorrowMine.service;

import com.borrow_mine.BorrowMine.domain.borrow.BorrowPost;
import com.borrow_mine.BorrowMine.domain.comment.Comment;
import com.borrow_mine.BorrowMine.domain.member.Member;
import com.borrow_mine.BorrowMine.dto.comment.CommentDto;
import com.borrow_mine.BorrowMine.dto.comment.CommentSaveDto;
import com.borrow_mine.BorrowMine.exception.BorrowPostException;
import com.borrow_mine.BorrowMine.exception.MemberException;
import com.borrow_mine.BorrowMine.repository.CommentRepository;
import com.borrow_mine.BorrowMine.repository.MemberRepository;
import com.borrow_mine.BorrowMine.repository.borrow.BorrowPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BorrowPostRepository borrowPostRepository;

    @Transactional
    public void saveComment(CommentSaveDto commentSaveDto, String nickname) {
        Optional<Member> optionalMember = memberRepository.findMemberByNickname(nickname);
        Optional<BorrowPost> findBorrowPost = borrowPostRepository.findById(commentSaveDto.getBorrowPostId());
        commentRepository.save(new Comment(commentSaveDto, findBorrowPost.orElseThrow(BorrowPostException::new), optionalMember.orElseThrow(MemberException::new)));
    }

    public List<CommentDto> getCommentDtoList(Long borrowPostId) {
        List<Comment> findComments = commentRepository.findCommentsByBorrowPostId(borrowPostId);
        return findComments.stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
