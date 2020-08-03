package space.yjeong.web.salespost;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.yjeong.service.room.SearchRoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/salesposts")
public class SalesPostApiController {

    private final SearchRoomService searchRoomService;

    @ApiOperation("방 검색 - 위도, 경도")
    @GetMapping
    public ResponseEntity searchSalesPosts(@RequestParam String southWest,
                                           @RequestParam String northEast) {
        return ResponseEntity.status(HttpStatus.OK).body(searchRoomService.searchRooms(southWest, northEast));
    }
}
