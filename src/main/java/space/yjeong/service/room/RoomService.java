package space.yjeong.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.hashtag.HashTag;
import space.yjeong.domain.image.Image;
import space.yjeong.domain.image.ImageRepository;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.room.RoomRepository;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.user.User;
import space.yjeong.exception.RoomNotFoundException;
import space.yjeong.service.salespost.HashTagService;
import space.yjeong.service.salespost.SalesPostService;
import space.yjeong.service.user.UserService;
import space.yjeong.web.dto.MessageResponseDto;
import space.yjeong.web.dto.room.RoomResponseDto;
import space.yjeong.web.dto.room.RoomSaveRequestDto;
import space.yjeong.web.dto.room.RoomUpdateRequestDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final SalesPostService salesPostService;
    private final HashTagService hashTagService;

    @Transactional(readOnly = true)
    public List<RoomResponseDto> readRooms(SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);

        List<SalesPost> salesPosts = salesPostService.findSalesPostsByUser(user.getId());

        List<RoomResponseDto> roomResponseDtoList = RoomResponseDto.listOf(salesPosts);

        return roomResponseDtoList;
    }

    @Transactional
    public MessageResponseDto saveRoom(RoomSaveRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        Room room = roomRepository.save(requestDto.toRoomsEntity());

        User user = userService.findUserBySessionUser(sessionUser);
        userService.checkUserAuthority(user);

        SalesPost salesPost = salesPostService.saveSalesPost(requestDto.toSalesPostEntity(user, room));

        List<HashTag> hashTags = new ArrayList<>();
        if(hashTagService.isHashTagNotNull(requestDto.getHashTags()))
            hashTags = hashTagService.saveHashTags(requestDto.toHashTagEntity(salesPost));
        salesPost.setHashTags(hashTags);

        salesPost.setImages(imageRepository.saveAll(requestDto.toImagesEntity(imagesSrc, salesPost)));
        room.setSalesPost(salesPost);

        return new MessageResponseDto("등록이 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto updateRoom(Long roomId, RoomUpdateRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        User user = userService.findUserBySessionUser(sessionUser);

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException(roomId)
        );
        room.update(requestDto.toRoomEntity());

        SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);
        userService.checkSameUser(salesPost.getSalesUser(), user);
        salesPost.update(requestDto.toSalesPostEntity());

        hashTagService.deleteHashTags(salesPost);

        List<HashTag> hashTags = new ArrayList<>();
        if(hashTagService.isHashTagNotNull(requestDto.getHashTags()))
            hashTags = hashTagService.saveHashTags(requestDto.toHashTagEntity(salesPost));
        salesPost.setHashTags(hashTags);

        List<Image> images = imageRepository.findAllBySalesPostId(salesPost.getId());
        // TODO : 기존 등록된 이미지(url)과 새로 등록된 이미지(File) 검사 후 저장

        return new MessageResponseDto("수정이 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto deleteRoom(Long roomId, SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException(roomId)
        );

        SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);
        userService.checkSameUser(salesPost.getSalesUser(), user);

        // TODO : 서버에 업로드된 이미지 파일 삭제
        imageRepository.deleteAllBySalesPostId(salesPost.getId());

        hashTagService.deleteHashTags(salesPost);

        salesPostService.deleteSalesPost(salesPost);
        roomRepository.delete(room);

        return new MessageResponseDto("삭제가 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto updatePostStatus(Long salesPostId, PostStatus postStatus, SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);

        SalesPost salesPost = salesPostService.findSalesPostById(salesPostId);
        userService.checkSameUser(salesPost.getSalesUser(), user);
        salesPost.updatePostStatus(postStatus);

        return new MessageResponseDto("게시상태 수정이 완료되었습니다.");
    }
}
