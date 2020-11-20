package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.dto.MoneyDropResponse;
import com.gyutaechoi.kakaopay.dto.MoneyGetterPostResponse;
import com.gyutaechoi.kakaopay.dto.MoneyGetterResponse;
import com.gyutaechoi.kakaopay.entity.KakaoPayUserView;
import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import com.gyutaechoi.kakaopay.entity.MoneyGetter;
import com.gyutaechoi.kakaopay.repository.*;
import com.gyutaechoi.kakaopay.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MoneyDropService {

    private MoneyDropRepository moneyDropRepository;

    private KakaoPayUserRepository kakaoPayUserRepository;

    private KakaoPayUserViewRepository kakaoPayUserViewRepository;

    private ChatRoomRepository chatRoomRepository;

    private MoneyGetterRepository moneyGetterRepository;

    /**
     * 유저가 채팅방에 돈을 뿌립니다.
     * @param userNo, 돈을 뿌리려는 유저
     * @param chatRoomName, 채팅방 이름
     * @param moneyToDrop, 뿌릴 금액
     * @param howManyUsers, 뿌린 돈을 받을 수 있는 인원수
     * @return 토큰 (String), 길이가 3인 랜덤 스트링
     */
    @Transactional
    public String addMoneyDrop(long userNo, String chatRoomName, int moneyToDrop, int howManyUsers) {
        final KakaoPayUserView user = kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(userNo, chatRoomName)
                                    .orElseThrow(() -> new RuntimeException("유저가 존재하지 않거나 유저가 채팅방이 참여하고 있지 않습니다."));

        final Long chatRoomNo = user.getChatRooms().get(0).getChatRoomNo();
        final String token = RandomUtil.generateRandomString(3);

        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setDropper(kakaoPayUserRepository.getOne(userNo));
        moneyDrop.setChatRoom(chatRoomRepository.getOne(chatRoomNo));
        moneyDrop.setToken(token);
        moneyDrop.setFirstBalance(moneyToDrop);
        moneyDrop.setCurrentBalance(moneyDrop.getFirstBalance());
        moneyDrop.setHowManyUsers(howManyUsers);
        moneyDrop.setNumOfMoneyGetters(0);
        LocalDateTime now = LocalDateTime.now();
        moneyDrop.setCreatedDateTime(now);
        moneyDrop.setMoneyGetExpiredAfter(now.plus(10L, ChronoUnit.MINUTES));
        moneyDrop.setViewExpiredAfter(now.plus(7L, ChronoUnit.DAYS));

        List<Integer> distribution = distributeMoney(moneyDrop, new SecureRandom());
        moneyDrop.setDistribution(distribution);

        moneyDropRepository.save(moneyDrop);
        return token;
    }

    /**
     * 유저가 뿌린 돈을 줍습니다.
     * @param userNo, 돈 줍기를 시도하는 유저
     * @param chatRoomName, 채팅방 이름
     * @param token, "돈 뿌리기"에 대한 토큰
     * @return Integer, 유저가 받은 금액을 리턴합니다.
     */
    @Transactional
    public MoneyGetterPostResponse tryToGetMoneyFromMoneyDrop(long userNo, String chatRoomName, String token) {
        kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(userNo, chatRoomName)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않거나 유저가 채팅방이 참여하고 있지 않습니다."));
        MoneyDrop moneyDrop = moneyDropRepository.findMoneyDropByToken(token)
                                    .orElseThrow(() -> new RuntimeException("Token에 대한 정보가 없습니다."));

        if (userNo == moneyDrop.getDropper().getUserNo()) {
            throw new RuntimeException("돈을 뿌린 사람은 받을 수 없습니다!");
        }

        final LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(moneyDrop.getMoneyGetExpiredAfter())) {
            throw new RuntimeException("유효기간이 지났습니다.");
        }

        if (moneyDrop.getNumOfMoneyGetters() == moneyDrop.getHowManyUsers()) {
            throw new RuntimeException("이미 모든 인원에게 돈을 주었습니다. 다음 기회에!");
        }

        Optional<MoneyGetter> optional = moneyGetterRepository.findMoneyGetterByMoneyDropNoAndUserNo(moneyDrop.getMoneyDropNo(), userNo);
        if (optional.isPresent()) {
            throw new RuntimeException("이미 돈을 지급 받았습니다.");
        }

        final int index = moneyDrop.getNumOfMoneyGetters();
        final List<Integer> distribution = moneyDrop.getDistribution();
        final int moneyToGive = distribution.get(index);

        MoneyGetter moneyGetter = new MoneyGetter();
        moneyGetter.setAmount(moneyToGive);
        moneyGetter.setMoneyGetterUser(kakaoPayUserRepository.getOne(userNo));
        moneyGetter.setMoneyDrop(moneyDrop);
        moneyGetterRepository.save(moneyGetter);

        // 뿌리기 잔액에서 제공한 금액을 차감
        moneyDrop.decrementCurrentBalanceBy(moneyToGive);
        // 돈 받은 사람수 증가 시킴 (+1)
        moneyDrop.incrementNumOfMoneyGetters();

        // etc: 뿌린 금액을 받는데 성공했으므로, 돈을 주운 사람의 잔액 증가 시킴 (구현 요구 사항은 아님)

        return new MoneyGetterPostResponse(moneyToGive);
    }

    /**
     * 자신이 뿌린 돈의 정보, 주운 사람의 정보를 확인합니다.
     * @param userNo, 유저번호
     * @param token, "돈뿌리기"에 대한 토큰
     */
    @Transactional(readOnly = true)
    public MoneyDropResponse getMoneyDrop(final long userNo, final String token) {
        final MoneyDrop moneyDrop = moneyDropRepository.findMoneyDropAndMoneyGetterByToken(token)
                .orElseThrow(() -> new RuntimeException("토큰 정보가 없습니다."));

        if (userNo != moneyDrop.getDropper().getUserNo()) {
            throw new RuntimeException("403 권한이 없습니다.");
        }

        final LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(moneyDrop.getViewExpiredAfter())) {
            throw new RuntimeException("유효기간이 지났습니다.");
        }

        return generateMoneyDropResponse(moneyDrop);
    }

    private MoneyDropResponse generateMoneyDropResponse(final MoneyDrop moneyDrop) {
        MoneyDropResponse response = new MoneyDropResponse();
        List<MoneyGetterResponse> moneyGetters = new ArrayList<>();

        response.setCreatedDateTime(moneyDrop.getCreatedDateTime());
        response.setDroppedMoney(moneyDrop.getFirstBalance() - moneyDrop.getCurrentBalance());
        response.setMoneyToDrop(moneyDrop.getFirstBalance());

        for (MoneyGetter moneyGetter : moneyDrop.getMoneyGetters()) {
            MoneyGetterResponse moneyGetterResponse = new MoneyGetterResponse();
            moneyGetterResponse.setAmount(moneyGetter.getAmount());
            moneyGetterResponse.setUserNo(moneyGetter.getMoneyGetterUser().getUserNo());
            moneyGetterResponse.setNickname(moneyGetter.getMoneyGetterUser().getNickname());
            moneyGetterResponse.setUsername(moneyGetter.getMoneyGetterUser().getUsername());
            moneyGetters.add(moneyGetterResponse);
        }

        response.setMoneyGetters(moneyGetters);
        return response;
    }

    // 돈 분배 로직을 담당하는 메서드
    public List<Integer> distributeMoney(MoneyDrop moneyDrop, Random r) {
        int currentBalance = moneyDrop.getFirstBalance();
        final int howManyUsers = moneyDrop.getHowManyUsers();

        ArrayList<Integer> result = new ArrayList<>();
        boolean distributionFinished = false;
        while(!distributionFinished) {
            int money = 0;
            for (int i = 0; i < howManyUsers; i++) {
                money = RandomUtil.generateRandomInteger(r, 1, currentBalance);
                currentBalance -= money;
                if (currentBalance == 0 && i != howManyUsers - 1) {
                    break;
                }
                result.add(money);
            }

            if (result.size() == howManyUsers)
                distributionFinished = true;
            else {
                currentBalance = moneyDrop.getFirstBalance();
                result.clear();
            }

        }
        return result;
    }


    @Autowired
    public void setMoneyDropRepository(MoneyDropRepository moneyDropRepository) {
        this.moneyDropRepository = moneyDropRepository;
    }

    @Autowired
    public void setKakaoPayUserRepository(KakaoPayUserRepository kakaoPayUserRepository) {
        this.kakaoPayUserRepository = kakaoPayUserRepository;
    }

    @Autowired
    public void setKakaoPayUserViewRepository(KakaoPayUserViewRepository kakaoPayUserViewRepository) {
        this.kakaoPayUserViewRepository = kakaoPayUserViewRepository;
    }

    @Autowired
    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Autowired
    public void setMoneyGetterRepository(MoneyGetterRepository moneyGetterRepository) {
        this.moneyGetterRepository = moneyGetterRepository;
    }
}