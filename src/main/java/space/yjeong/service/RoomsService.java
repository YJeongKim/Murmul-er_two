package space.yjeong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.rooms.*;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
import space.yjeong.web.dto.rooms.RoomsResponseDto;
import space.yjeong.web.dto.rooms.RoomsSaveRequestDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomsService {
    private final RoomsRepository roomsRepository;
    private final SalesPostsRepository salesPostsRepository;
    private final ImagesRepository imagesRepository;
    private final HashTagsRepository hashTagsRepository;
    private final UserRepository userRepository;

    @Transactional
    public RoomsResponseDto saveRoom(RoomsSaveRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        Rooms room = roomsRepository.save(requestDto.toRoomsEntity());
        room.setImages(imagesRepository.saveAll(requestDto.toImagesEntity(imagesSrc, room)));

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        // TODO : if user null or role is guest, throw exception
        SalesPosts salesPost = salesPostsRepository.save(requestDto.toSalesPostsEntity(user, room));
        salesPost.setHashTags(hashTagsRepository.saveAll(requestDto.toHashTagsEntity(salesPost)));
        return new RoomsResponseDto("등록이 완료되었습니다.");
    }
}