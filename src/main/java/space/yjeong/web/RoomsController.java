package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.service.RoomsService;
import space.yjeong.web.dto.rooms.RoomsSaveRequestDto;
import space.yjeong.web.dto.rooms.RoomsUpdateRequestDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsController {
    private final RoomsService roomsService;
    private final HttpSession httpSession;

    @ApiOperation("방 등록")
    @PostMapping
    public ResponseEntity saveRoom(@RequestBody RoomsSaveRequestDto requestDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(roomsService.saveRoom(requestDto, user));
    }

    @ApiOperation("방 수정")
    @PutMapping("/{roomId}")
    public ResponseEntity updateRoom(@PathVariable Long roomId, @RequestBody RoomsUpdateRequestDto requestDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomsService.updateRoom(roomId, requestDto, user));
    }

}
