# borrow_mine_backend
(개인 프로젝트) 물건을 빌려주고 빌릴 수 있는 서비스입니다.

## 사용한 기술스택
- JAVA 11
- Spring Boot
- Spring Data Jpa
- Querydsl
- MySQL
- [React](https://github.com/salbyul/borrow_mine_frontend)

## 구현
- 계정
  - 회원가입, 로그인, 로그아웃
  - 정보 페이지
    - 회원 수정, 비밀번호 변경, 쓴 글 확인, 즐겨찾기, 거래 내역, 받은 요청, 보낸 요청, 차단 목록
  - 회원 차단
    - 요청과 채팅이 불가능하게 됨
- 글
  - 글쓰기, 글 삭제, 즐겨찾기, 글 신고, 요청
- 댓글
  - 댓글 작성, 댓글 신고
- 채팅
  - 채팅방 생성, 채팅방 삭제
- 인기 제품
  - 주간, 월간, 무기한 범위의 게시글로 올라온 제품의 순위를 볼 수 있음