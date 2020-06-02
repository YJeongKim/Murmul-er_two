package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.rooms.PostStatus;
import space.yjeong.service.RoomsService;
import space.yjeong.web.dto.rooms.RoomsSaveRequestDto;
import space.yjeong.web.dto.rooms.RoomsUpdateRequestDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomsApiController {
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

    @ApiOperation("방 삭제")
    @DeleteMapping("/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable Long roomId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomsService.deleteRoom(roomId, user));
    }

    @ApiOperation("방 게시상태 수정")
    @PatchMapping("/{salesPostsId}/post-status")
    public ResponseEntity updatePostStatus(@PathVariable Long salesPostsId, @RequestBody PostStatus postStatus) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomsService.updatePostStatus(salesPostsId, postStatus, user));
    }
}
