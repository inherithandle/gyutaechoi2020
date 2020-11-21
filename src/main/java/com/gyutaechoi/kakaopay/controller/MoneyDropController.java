package com.gyutaechoi.kakaopay.controller;

import com.gyutaechoi.kakaopay.dto.*;
import com.gyutaechoi.kakaopay.service.MoneyDropService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "돈뿌리기 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "돈뿌리기 정보를 리턴합니다."),
            @ApiResponse(code = 403, message = "타인의 돈뿌리기 조회를 시도했습니다."),
            @ApiResponse(code = 404, message = "토큰에 대한 정보가 DB에 없습니다."),
            @ApiResponse(code = 400, message = "유효하지 않은 인자(토큰, 유저번호 헤더값)를 보냈거나 조회 유효기간이 지났습니다.")
    })
    public ResponseEntity<MoneyDropResponse> getMoneyDrop(@RequestParam("token") String token,
                                                          @ApiParam(defaultValue = "1")
                                                          @RequestHeader(USER_ID) Long xUserId) {
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
    @ApiOperation(value = "돈뿌리기 API")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "채팅방 인원보다 많은 인원을 지정했거나 채팅방에 참여하지 않은 유저가 돈을 뿌리려고 시도했습니다.\n너무 많은 유저에게 돈을 뿌리려고 시도했습니다.")
    })
    public ResponseEntity<MoneyDropPostResponse> addMoneyDrop(@ApiParam(defaultValue = "1") @RequestHeader(USER_ID) Long userNo,
                      @ApiParam(defaultValue = "chatroom_id1") @RequestHeader(ROOM_ID) String chatRoomName,
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
    @ApiOperation(value = "돈 받기 API")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "받기 유효기간이 지났거나, 자신이 뿌린 돈을 자신이 받으려고 했습니다.\nDB에 존재하지 않는 유저입니다.\n유저가 채팅방에 참여하고 있지 않습니다."),
            @ApiResponse(code = 403, message = "이미 돈을 받았거나, 뿌릴 돈을 모두 다른 유저들에게 나눠주었습니다.")
    })
    public ResponseEntity<MoneyGetterPostResponse> addMoneyGetter(@ApiParam(defaultValue = "2") @RequestHeader(USER_ID) Long userNo,
                                            @ApiParam(defaultValue = "chatroom_id1") @RequestHeader(ROOM_ID) String chatRoomName,
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
