package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.dto.MoneyGetterPostResponse;
import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import com.gyutaechoi.kakaopay.entity.KakaoPayUserView;
import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import com.gyutaechoi.kakaopay.entity.MoneyGetter;
import com.gyutaechoi.kakaopay.exception.BadRequestException;
import com.gyutaechoi.kakaopay.exception.ForbiddenException;
import com.gyutaechoi.kakaopay.exception.NotFoundException;
import com.gyutaechoi.kakaopay.repository.KakaoPayUserRepository;
import com.gyutaechoi.kakaopay.repository.KakaoPayUserViewRepository;
import com.gyutaechoi.kakaopay.repository.MoneyDropRepository;
import com.gyutaechoi.kakaopay.repository.MoneyGetterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

/**
 * 뿌린 돈 줍기 API 유닛 테스트
 */
@ExtendWith(MockitoExtension.class)
public class MoneyDropServiceTryToGetMoneyUnitTest {
    @Mock
    private MoneyDropRepository moneyDropRepository;

    @Mock
    private KakaoPayUserViewRepository kakaoPayUserViewRepository;

    @Mock
    private MoneyGetterRepository moneyGetterRepository;

    @Mock
    private KakaoPayUserRepository kakaoPayUserRepository;

    @InjectMocks
    private MoneyDropService moneyDropService = new MoneyDropService();

    private static final long mockUserNo = 1L;
    private static final String mockChatRoomName = "목채팅방1";
    private static final String mockToken = "aB_";

    @Test
    public void 유저가_참여하고있지않은_채팅방에_돈받기_시도하면_익셉션_던진다() {
        // 유저번호 1은 "목채팅방"에 참여하지 않고 있다고 가정. DB 조회 결과 없음이라고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, mockChatRoomName))
                .willReturn(Optional.empty());

        // 존재하지 않으면 예외를 던진다.
        assertThrows(BadRequestException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(mockUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void token_정보를_발견하지못하면_익셉션_던진다() {
        // 유저번호 1은 "목채팅방"에 참여하고 있다고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(1L)));

        // 토큰으로 조회했는데, 토큰에 대한 정보가 없다고 가정한다.
        given(moneyDropRepository.findMoneyDropByToken(mockToken)).willReturn(Optional.empty());

        // "돈뿌리기"에 대한 정보 조회 불가능하면 예외를 던진다.
        assertThrows(NotFoundException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(mockUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void token_길이가_3이_아닌_토큰_익셉션_던진다() {
        // 유저번호 1은 "목채팅방"에 참여하고 있다고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(1L)));

        assertThrows(NotFoundException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(mockUserNo, mockChatRoomName, "Zb");
        });


        assertThrows(NotFoundException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(mockUserNo, mockChatRoomName, mockToken + "abcd");
        });
    }

    @Test
    public void 돈을_뿌린_사람은_자신이_뿌린돈_주울수_없다() {
        final LocalDateTime now = LocalDateTime.now();

        // mock user가 목채팅방에 참여하고 있다고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(mockUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(mockUserNo)));

        // 토큰으로 조회시 "돈뿌리기" 정보가 존재한다고 가정한다. 근데 돈을 뿌린 사람이 mock user이다.
        given(moneyDropRepository.findMoneyDropByToken(mockToken)).willReturn(
                Optional.of(getMoneyDrop(now, now, 1L, 3, 0)));

        // mock user는 자신이 뿌린 돈을 주우려고 시도하지만. 그럴 경우 익셉션을 던진다.
        assertThrows(BadRequestException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(mockUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void 유효기간이_지나면_돈을_주울수_없다() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneSecondEarlier = now.minusSeconds(1); // 지금으로부터 1초전 시간
        final LocalDateTime sevenDaysLater = now.plusDays(7L); // 지금으로부터 7일뒤 시간
        final Long billGatesUserNo = 2L;


        // 빌게이츠가 채팅방에 존재하는지를 DB에 확인. 존재한다고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(billGatesUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(billGatesUserNo)));

        // 토큰으로 "돈뿌리기" 정보를 조회할수 있는지 확인. 조회할수 있다고 가정한다.
        // 근데 돈뿌리기가 방금 만료되었다고 가정한다.
        given(moneyDropRepository.findMoneyDropByToken(mockToken))
                .willReturn(Optional.of(getMoneyDrop(sevenDaysLater, oneSecondEarlier, mockUserNo, 3, 0)));

        // 유효기간이 지났으므로 예외를 던진다.
        assertThrows(BadRequestException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(billGatesUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void 돈을_이미_받은_사람은_돈을_받을수_없다() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tenMinutesLater = now.plusMinutes(10L); // 지금으로부터 10분뒤 시간
        final LocalDateTime sevenDaysLater = now.plusDays(7L); // 지금으로부터 7일뒤 시간
        final Long billGatesUserNo = 2L;

        // 빌게이츠가 채팅방에 존재하는지를 DB에 확인. 존재한다고 가정한다.
        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(billGatesUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(billGatesUserNo)));

        // 토큰으로 "돈뿌리기" 정보를 조회할수 있는지 확인. 조회할수 있다고 가정한다.
        // 현재 3명이 주울 수 있다고 가정한다.
        given(moneyDropRepository.findMoneyDropByToken(mockToken))
                .willReturn(Optional.of(getMoneyDrop(sevenDaysLater, tenMinutesLater, mockUserNo, 3, 0)));

        // 빌게이츠가 돈을 주웠는지 DB에서 조회한다. 돈을 주운 정보가 존재한다고 가정한다.
        given(moneyGetterRepository.findMoneyGetterByMoneyDropNoAndUserNo(1L, billGatesUserNo))
                .willReturn(Optional.of(getMoneyGetter(1L, billGatesUserNo)));

        // 돈을 이미 주웠으면 예외를 던진다.
        assertThrows(ForbiddenException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(billGatesUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void 이미_돈을_다른_사람들이_다_주웠다면_돈을_주울수_없다() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tenMinutesLater = now.plusMinutes(10L); // 지금으로부터 10분뒤 시간
        final LocalDateTime sevenDaysLater = now.plusDays(7L); // 지금으로부터 7일뒤 시간
        final Long billGatesUserNo = 2L;


        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(billGatesUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(billGatesUserNo)));

        // "돈뿌리기" 정보를 DB에서 가져왔는데, 이미 돈받은 사람 3명, 받을 사람 3명이라고 가정.
        given(moneyDropRepository.findMoneyDropByToken(mockToken))
                .willReturn(Optional.of(
                        getMoneyDrop(sevenDaysLater, tenMinutesLater, mockUserNo, 3, 3)));

        assertThrows(ForbiddenException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(billGatesUserNo, mockChatRoomName, mockToken);
        });
    }

    @Test
    public void 돈줍기_성공() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tenMinutesLater = now.plusMinutes(10L); // 지금으로부터 10분뒤 시간
        final LocalDateTime sevenDaysLater = now.plusDays(7L); // 지금으로부터 7일뒤 시간
        final Long billGatesUserNo = 2L;


        given(kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(billGatesUserNo, mockChatRoomName))
                .willReturn(Optional.of(getKakaoPayUserView(billGatesUserNo)));

        // "돈뿌리기" 정보를 DB에서 가져왔는데, 이미 돈받은 사람 1명, 받을 사람 3명이라고 가정.
        // 1번유저가 돈을 뿌린다고 가정한다.
        given(moneyDropRepository.findMoneyDropByToken(mockToken))
                .willReturn(Optional.of(
                        getMoneyDrop(sevenDaysLater, tenMinutesLater, mockUserNo, 3, 1)));

        // 2번 유저, 빌게이츠가 돈을 줍는다.
        MoneyGetterPostResponse response = moneyDropService.tryToGetMoneyFromMoneyDrop(billGatesUserNo, mockChatRoomName, mockToken);

        assertThat(response.getReceivedMoney(), greaterThan(0));
    }

    private KakaoPayUserView getKakaoPayUserView(long userNo) {
        KakaoPayUserView kakaoPayUser = new KakaoPayUserView();
        kakaoPayUser.setUserNo(userNo);
        kakaoPayUser.setNickname("mock nickname");
        kakaoPayUser.setUsername("mock username");
        kakaoPayUser.setUserId("mock user");
        kakaoPayUser.setPassword("1234");
        return kakaoPayUser;
    }

    private MoneyDrop getMoneyDrop(LocalDateTime viewExpiredAfter, LocalDateTime moneyGetExpiredAfter, long userNo,
                                    int howManyUsers, int numOfMoneyGetters) {
        MoneyDrop moneyDrop = new MoneyDrop();
        KakaoPayUser dropper = new KakaoPayUser();
        dropper.setUserNo(userNo);

        moneyDrop.setDropper(dropper);
        moneyDrop.setFirstBalance(500);
        moneyDrop.setCurrentBalance(500);
        moneyDrop.setViewExpiredAfter(viewExpiredAfter);
        moneyDrop.setMoneyGetExpiredAfter(moneyGetExpiredAfter);
        moneyDrop.setHowManyUsers(howManyUsers);
        moneyDrop.setNumOfMoneyGetters(numOfMoneyGetters);
        moneyDrop.setMoneyDropNo(1L);

        DistributeMoneyService distributeService = new DistributeMoneyAllUsersService();
        List<Integer> distribution = distributeService.distributeMoney(500, howManyUsers, new SecureRandom());
        moneyDrop.setDistribution(distribution);
        return moneyDrop;
    }

    private MoneyGetter getMoneyGetter(long moneyDropNo, long moneyGetterUserNo) {
        MoneyGetter moneyGetter = new MoneyGetter();

        KakaoPayUser moneyGetterUser = new KakaoPayUser();
        moneyGetterUser.setUserNo(moneyGetterUserNo);

        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setMoneyDropNo(moneyDropNo);


        moneyGetter.setMoneyGetterUser(moneyGetterUser);
        moneyGetter.setMoneyDrop(moneyDrop);

        return moneyGetter;
    }
}
