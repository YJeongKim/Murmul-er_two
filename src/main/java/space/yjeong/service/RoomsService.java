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
import space.yjeong.web.dto.rooms.RoomsUpdateRequestDto;

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
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPost.setHashTags(hashTagsRepository.saveAll(requestDto.toHashTagsEntity(salesPost)));
        room.setSalesPosts(salesPost);

        return new RoomsResponseDto("등록이 완료되었습니다.");
    }

    @Transactional
    public RoomsResponseDto updateRoom(Long roomId, RoomsUpdateRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        Rooms room = roomsRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 없습니다. roomId=" + roomId)
        );
        room.update(requestDto.toRoomsEntity());

        List<Images> images = imagesRepository.findAllByRoom_Id(roomId);
        // TODO : 기존 등록된 이미지(url)과 새로 등록된 이미지(File) 검사 후 저장

        SalesPosts salesPosts = salesPostsRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 없습니다. roomId=" + roomId)
        );
        salesPosts.update(requestDto.toSalesPostsEntity());

        if(hashTagsRepository.existsBySalesPostsId(salesPosts.getId()))
            hashTagsRepository.deleteAllBySalesPostsId(salesPosts.getId());
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPosts.setHashTags(hashTagsRepository.saveAll(requestDto.toHashTagsEntity(salesPosts)));

        return new RoomsResponseDto("수정이 완료되었습니다.");
    }
}
