package com.gyutaechoi.kakaopay.controller;

import com.gyutaechoi.kakaopay.dto.*;
import com.gyutaechoi.kakaopay.service.MoneyDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class MoneyDropController {

    private MoneyDropService moneyDropService;

    private static final String ROOM_ID = "X-ROOM-ID";
    private static final String USER_ID = "X-USER-ID";

    /**
     * 돈뿌리기 정보 조회 API
     * @param token
     * @param xUserId
     * @return
     */
    @GetMapping("/money-drop")
    public ResponseEntity<MoneyDropResponse> getMoneyDrop(@RequestParam("token") String token,
                                                          @RequestHeader(USER_ID) String xUserId) {
        final MoneyDropResponse moneyDrop = moneyDropService.getMoneyDrop(Long.valueOf(xUserId), token);
        return ResponseEntity.ok(moneyDrop);
    }

    /**
     * 채팅방에 돈뿌리기 API
     * @param userNo, 돈을 뿌리는 유저
     * @param chatRoomName, 돈을 뿌릴 채팅방 이름 (X-ROOM-ID 헤더)
     * @param moneyDropRequest, 뿌릴 금액, 돈을 받을 수 있는 최대 인원 담고 있는 객체
     * @return
     */
    @PostMapping("/money-drop")
    public ResponseEntity<MoneyDropPostResponse> addMoneyDrop(@RequestHeader(USER_ID) Long userNo,
                                                              @RequestHeader(ROOM_ID) String chatRoomName,
                                                              @RequestBody MoneyDropRequest moneyDropRequest) throws UnsupportedEncodingException {
        chatRoomName = URLDecoder.decode(chatRoomName, "UTF-8"); // 헤더에 URL 인코딩된 한글 입력한 경우를 대비해서..
        final String token = moneyDropService.addMoneyDrop(userNo, chatRoomName,
                moneyDropRequest.getMoneyToDrop(), moneyDropRequest.getHowManyUsers());
        return ResponseEntity.ok(new MoneyDropPostResponse(token));
    }

    /**
     * 뿌린 돈 받기 API
     * @param userNo, 돈을 받으려고 하는 유저
     * @param chatRoomName, 채팅방 이름 (X-ROOM-ID 헤더)
     * @return
     */
    @PostMapping("/money-getter")
    public ResponseEntity<MoneyGetterPostResponse> addMoneyGetter(@RequestHeader(USER_ID) Long userNo,
                                            @RequestHeader(ROOM_ID) String chatRoomName,
                                            @RequestBody MoneyGetterRequest req) throws UnsupportedEncodingException {
        chatRoomName = URLDecoder.decode(chatRoomName, "UTF-8"); // 헤더에 URL 인코딩된 한글 입력한 경우를 대비해서..
        final MoneyGetterPostResponse res = moneyDropService.tryToGetMoneyFromMoneyDrop(userNo, chatRoomName, req.getToken());
        return ResponseEntity.ok(res);
    }

    @Autowired
    public void setMoneyDropService(MoneyDropService moneyDropService) {
        this.moneyDropService = moneyDropService;
    }
}
