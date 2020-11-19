package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.entity.ChatRoomView;
import com.gyutaechoi.kakaopay.entity.KakaoPayUserView;
import com.gyutaechoi.kakaopay.repository.ChatRoomRepository;
import com.gyutaechoi.kakaopay.repository.KakaoPayUserRepository;
import com.gyutaechoi.kakaopay.repository.KakaoPayUserViewRepository;
import com.gyutaechoi.kakaopay.repository.MoneyDropRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

/**
 * 돈 뿌리기 API 유닛 테스트
 */
@ExtendWith(MockitoExtension.class)
class MoneyDropServiceAddMoneyDropUnitTest {
    @Mock
    private MoneyDropRepository moneyDropRepository;

    @Mock
    private KakaoPayUserViewRepository kakaoPayUserViewRepository;

    @Mock
    private KakaoPayUserRepository kakaoPayUserRepository;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private MoneyDropService moneyDropService = new MoneyDropService();

    @Test
    public void 유저가_참여하고있지않은_채팅방에_돈뿌리기_시도하면_익셉션_던진다() {
        final long mockUserNo = 1L;
        final String chatRoomName = "목채팅방";

        // 유저번호 1은 "목채팅방"에 참여하지 않고 있다고 가정
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, chatRoomName))
                .willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            moneyDropService.addMoneyDrop(mockUserNo, chatRoomName, 500, 3);
        });
    }

    @Test
    public void 돈뿌리기_성공() {
        ChatRoomView chatRoom = new ChatRoomView();
        chatRoom.setChatRoomNo(1L);
        KakaoPayUserView user = new KakaoPayUserView();
        user.getChatRooms().add(chatRoom);
        final long mockUserNo = 1L;
        final String chatRoomName = "목채팅방";
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, chatRoomName))
                .willReturn(Optional.of(user));

        final String token = moneyDropService.addMoneyDrop(mockUserNo, chatRoomName, 500, 3);

        assertEquals(3, token.length());
        System.out.println("Token : " + token);
    }

}