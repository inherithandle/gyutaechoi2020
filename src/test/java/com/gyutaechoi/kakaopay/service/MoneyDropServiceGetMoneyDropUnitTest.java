package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.dto.MoneyDropResponse;
import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import com.gyutaechoi.kakaopay.exception.BadRequestException;
import com.gyutaechoi.kakaopay.exception.ForbiddenException;
import com.gyutaechoi.kakaopay.exception.NotFoundException;
import com.gyutaechoi.kakaopay.repository.MoneyDropRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

/**
 * "돈뿌리기" 정보 조회 API 유닛 테스트
 */
@ExtendWith(MockitoExtension.class)
public class MoneyDropServiceGetMoneyDropUnitTest {

    @Mock
    private MoneyDropRepository moneyDropRepository;

    @InjectMocks
    private MoneyDropService moneyDropService = new MoneyDropService();

    private static final long mockUserNo = 1L;
    private static final String mockToken = "aB_";

    @Test
    public void 토큰_정보가_유효하지_않으면_예외를_던진다() {
        // token으로 돈뿌리기를 조회시도 했지만, 존재하지 않는다고 가정한다.
        given(moneyDropRepository.findMoneyDropAndMoneyGetterByToken(mockToken))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            moneyDropService.getMoneyDrop(mockUserNo, mockToken);
        });
    }

    @Test
    public void 돈을_뿌린사람만_조회가능_아니면_예외를_던진다() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime threeDaysLater = now.plusDays(3L);
        final long mockUserNo2 = 2L;

        // DB에서 토큰으로 돈뿌리기 정보를 조회할수 있다고 가정한다. 돈을뿌린 사람은 유저번호 1이다.
        given(moneyDropRepository.findMoneyDropAndMoneyGetterByToken(mockToken))
                .willReturn(Optional.of(getMoneyDrop(1L, threeDaysLater, 500)));

        // 돈을 유저번호1이 뿌렸는데 유저번호2가 조회시도한다. 익셉션 던진다.
        assertThrows(ForbiddenException.class, () -> {
            moneyDropService.getMoneyDrop(mockUserNo2, mockToken);
        });

    }

    @Test
    public void 유효기간이_지나서_조회할수없다_예외를_던진다() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneSecondEarlier = now.minusSeconds(1L); // 지금시간으로부터 1초전

        // DB에서 토큰으로 돈뿌리기 정보를 조회할수 있다고 가정한다. 근데 유효기간이 만료된 데이터이다.
        given(moneyDropRepository.findMoneyDropAndMoneyGetterByToken(mockToken))
                .willReturn(Optional.of(getMoneyDrop(1L, oneSecondEarlier, 500)));

        assertThrows(BadRequestException.class, () -> {
            moneyDropService.getMoneyDrop(mockUserNo, mockToken);
        });

    }

    @Test
    public void 조회_성공() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime threeDaysLater = now.plusDays(3L); // 지금시간으로부터 3일뒤

        // 유저가 500원 뿌린 정보가 DB에서 발견 가능한다고 가정한다.
        // 어떤 유저도 줍지 않았다고 가정한다.
        given(moneyDropRepository.findMoneyDropAndMoneyGetterByToken(mockToken))
                .willReturn(Optional.of(getMoneyDrop(1L, threeDaysLater, 500)));

        MoneyDropResponse response = moneyDropService.getMoneyDrop(mockUserNo, mockToken);

        // 예외가 발생하지 않고 500원을 정상적으로 리턴받았다.
        assertEquals(500, response.getMoneyToDrop());
        assertEquals(0, response.getDroppedMoney());
    }

    private MoneyDrop getMoneyDrop(long dropperUserNo, LocalDateTime viewExpiredAfter, int firstBalance) {
        KakaoPayUser dropper = new KakaoPayUser();
        dropper.setUserNo(dropperUserNo);
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setDropper(dropper);
        moneyDrop.setFirstBalance(firstBalance);
        moneyDrop.setCurrentBalance(firstBalance);
        moneyDrop.setViewExpiredAfter(viewExpiredAfter);

        return moneyDrop;
    }


}
