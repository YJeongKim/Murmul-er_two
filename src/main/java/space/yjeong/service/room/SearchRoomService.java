package space.yjeong.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.room.RoomRepository;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.service.salespost.SalesPostService;
import space.yjeong.web.dto.salespost.SummaryResponseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchRoomService {

    private final RoomRepository roomRepository;

    private final SalesPostService salesPostService;

    @Transactional(readOnly = true)
    public List<SummaryResponseDto> searchRooms(String southWest, String northEast) {

        String[] temp = southWest.substring(1, southWest.length() - 1).split(", ");
        BigDecimal south = new BigDecimal(temp[0]);
        BigDecimal west = new BigDecimal(temp[1]);

        temp = northEast.substring(1, northEast.length() - 1).split(", ");
        BigDecimal north = new BigDecimal((temp[0]));
        BigDecimal east = new BigDecimal((temp[1]));

        List<Room> rooms = roomRepository.findAllByLocation(south, west, north, east);
        List<SalesPost> salesPosts = new ArrayList<>();

        for (Room room : rooms) {
            SalesPost salesPost = salesPostService.findSalesPostByRoom(room.getId());
            if (salesPost.getPostStatus().equals(PostStatus.POSTING)) {
                salesPosts.add(salesPost);
            }
        }

        List<SummaryResponseDto> summaryResponseDtoList = SummaryResponseDto.listOf(salesPosts);

        return summaryResponseDtoList;
    }
}
