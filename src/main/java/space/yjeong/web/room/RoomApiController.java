package space.yjeong.web.room;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.service.room.RoomService;
import space.yjeong.web.dto.room.RoomRequestDto;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("방 목록 조회")
    @GetMapping
    public ResponseEntity readRooms() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.readRooms(user));
    }

    @ApiOperation("방 등록")
    @PostMapping
    public ResponseEntity saveRoom(@RequestBody RoomRequestDto requestDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.saveRoom(requestDto, user));
    }

    @ApiOperation("방 수정")
    @PutMapping("/{roomId}")
    public ResponseEntity updateRoom(@PathVariable Long roomId,
                                     @RequestBody RoomRequestDto requestDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(roomId, requestDto, user));
    }

    @ApiOperation("방 삭제")
    @DeleteMapping("/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable Long roomId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(roomId, user));
    }

    @ApiOperation("방 게시상태 수정")
    @PatchMapping("/{salesPostsId}/post-status")
    public ResponseEntity updatePostStatus(@PathVariable Long salesPostsId, @RequestBody PostStatus postStatus) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(roomService.updatePostStatus(salesPostsId, postStatus, user));
    }
}
