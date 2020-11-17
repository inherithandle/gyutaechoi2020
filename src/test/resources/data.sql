INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('원더코딩우먼', '12345', 'user1', 'Ada Lovelace');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('윈도우맨', '12345', 'user2', 'Bill Gates');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('테슬라맨', '12345', 'user3', 'Elon Musk');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('제로투원맨', '12345', 'user4', 'Peter Thiel');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('이더리움맨', '12345', 'user5', 'Vitalik Buterin');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('비트코인맨', '12345', 'user6', 'Satoshi Nakamoto');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('루비맨', '12345', 'user7', 'Yukihiro Matsumoto');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('파이썬맨', '12345', 'user8', 'Guido van Rossum');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('구글맨', '12345', 'user9', 'Jeffrey Dean');
INSERT INTO kakao_pay_user (nickname, password, user_id, username) values ('검색왕', '12345', 'user10', 'Larry Page');

INSERT INTO chat_room (chat_room_name, creator_user_no) values ('러브레이스 외 9명', 1);
INSERT INTO chat_room (chat_room_name, creator_user_no) values ('러브레이스 외 2명', 1);
INSERT INTO chat_room (chat_room_name, creator_user_no) values ('채팅방3', 8);

-- 첫번째 채팅방에는 모든 유저가 있다. 에이다가 채팅방을 만들었다.
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (1, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (2, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (3, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (4, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (5, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (6, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (7, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (8, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (9, 1);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (10, 1);

-- 두번째 채팅방에는 에이다 러브레이스, 일론 머스크, 래리 페이지가 채팅중이다. 에이다가 채팅방을 만들었다.
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (1, 2);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (3, 2);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (10, 2);

-- 세번째 채팅방에는 귀도 반 로썸, 피터 틸, 에이다 러브레이스가 채팅중이다. 귀도가 채팅방을 만들었다.
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (8, 3);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (4, 3);
INSERT INTO kakao_pay_user_chat_rooms (user_no, chat_room_no) values (1, 3);