package space.yjeong.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.room.RoomRepository;
import space.yjeong.domain.salespost.HashTag;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.user.User;
import space.yjeong.exception.ExpectedException;
import space.yjeong.exception.RoomNotFoundException;
import space.yjeong.service.salespost.HashTagService;
import space.yjeong.service.salespost.ImageService;
import space.yjeong.service.salespost.SalesPostService;
import space.yjeong.service.user.UserService;
import space.yjeong.web.dto.MessageResponseDto;
import space.yjeong.web.dto.room.RoomRequestDto;
import space.yjeong.web.dto.room.RoomResponseDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final UserService userService;
    private final SalesPostService salesPostService;
    private final HashTagService hashTagService;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<RoomResponseDto> readRooms(SessionUser sessionUser) {
        User user = userService.findUserBySessionUser(sessionUser);

        List<SalesPost> salesPosts = salesPostService.findSalesPostsByUser(user.getId());

        List<RoomResponseDto> roomResponseDtoList = RoomResponseDto.listOf(salesPosts);

        return roomResponseDtoList;
    }

    @Transactional
    public MessageResponseDto saveRoom(RoomRequestDto requestDto, SessionUser sessionUser) {
        try {
            User user = userService.findUserBySessionUser(sessionUser);
            /* 현재 user 권한 : guest, 휴대폰 인증 후 : user 변경 */
            // userService.checkUserAuthority(user);

            Room room = roomRepository.save(requestDto.toRoomEntity());

            SalesPost salesPost = salesPostService.saveSalesPost(requestDto.toSalesPostEntity(user, room));

            List<HashTag> hashTags = new ArrayList<>();
            if (hashTagService.isHashTagNotNull(requestDto.getHashTags()))
                hashTags = hashTagService.saveHashTags(requestDto.toHashTagEntity(salesPost));
            salesPost.setHashTags(hashTags);

            return new MessageResponseDto("등록이 완료되었습니다.", room.getId());
        } catch (ExpectedException e) {
            return MessageResponseDto.builder()
                    .message("등록에 실패하였습니다.")
                    .subMessage(e.getMessage())
                    .build();
        }
    }

    @Transactional
    public MessageResponseDto updateRoom(Long roomId, RoomRequestDto requestDto, SessionUser sessionUser) {
        try {
            User user = userService.findUserBySessionUser(sessionUser);

            Room room = roomRepository.findById(roomId).orElseThrow(
                    () -> new RoomNotFoundException(roomId)
            );
            room.update(requestDto.toRoomEntity());

            SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);
            userService.checkSameUser(salesPost.getSalesUser(), user);
            salesPost.update(requestDto.toSalesPostEntity(user, room));

            hashTagService.deleteHashTags(salesPost.getId());

            List<HashTag> hashTags = new ArrayList<>();
            if (hashTagService.isHashTagNotNull(requestDto.getHashTags()))
                hashTags = hashTagService.saveHashTags(requestDto.toHashTagEntity(salesPost));
            salesPost.setHashTags(hashTags);

            imageService.deleteImages(salesPost.getId());

//            List<Image> images = imageService.saveImages(imageList, salesPost);
//            salesPost.setImages(images);

            return new MessageResponseDto("수정이 완료되었습니다.", room.getId());
        } catch (ExpectedException e) {
            return MessageResponseDto.builder()
                    .message("수정에 실패하였습니다.")
                    .subMessage(e.getMessage())
                    .build();
        }
    }

    @Transactional
    public MessageResponseDto deleteRoom(Long roomId, SessionUser sessionUser) {
        try {
            User user = userService.findUserBySessionUser(sessionUser);

            Room room = roomRepository.findById(roomId).orElseThrow(
                    () -> new RoomNotFoundException(roomId)
            );

            SalesPost salesPost = salesPostService.findSalesPostByRoom(roomId);
            userService.checkSameUser(salesPost.getSalesUser(), user);

            imageService.deleteImages(salesPost.getId());

            hashTagService.deleteHashTags(salesPost.getId());

            salesPostService.deleteSalesPost(salesPost);

            roomRepository.delete(room);

            return new MessageResponseDto("삭제가 완료되었습니다.", room.getId());
        } catch (ExpectedException e) {
            return MessageResponseDto.builder()
                    .message("삭제에 실패하였습니다.")
                    .subMessage(e.getMessage())
                    .build();
        }
    }

    @Transactional
    public MessageResponseDto updatePostStatus(Long salesPostId, String status, SessionUser sessionUser) {
        try {
            User user = userService.findUserBySessionUser(sessionUser);

            PostStatus postStatus;
            status = status.replaceAll("\\\"", "");

            switch (status) {
                case "게시종료":
                    postStatus = PostStatus.POSTING_END;
                    break;
                case "게시금지":
                    postStatus = PostStatus.POSTING_BAN;
                    break;
                case "거래완료":
                    postStatus = PostStatus.DEAL_COMPLETED;
                    break;
                case "게시중":
                default:
                    postStatus = PostStatus.POSTING;
                    break;
            }

            SalesPost salesPost = salesPostService.findSalesPostById(salesPostId);
            userService.checkSameUser(salesPost.getSalesUser(), user);
            salesPost.updatePostStatus(postStatus);

            return new MessageResponseDto("게시상태 변경이 완료되었습니다.", postStatus.name(), salesPost.getId());
        } catch (ExpectedException e) {
            return MessageResponseDto.builder()
                    .message("게시상태 변경에 실패하였습니다.")
                    .subMessage(e.getMessage())
                    .build();
        }
    }

    public MessageResponseDto setImagesByUser(List<MultipartFile> imageFiles, SessionUser sessionUser) {
        try {
            User user = userService.findUserBySessionUser(sessionUser);

            SalesPost salesPost = salesPostService.findLatestSalesPostByUser(user.getId());

            List<Image> images = imageService.saveImages(imageFiles, salesPost);
            salesPost.setImages(images);

            Room room = roomRepository.findBySalesPostId(salesPost.getId());
            room.setSalesPost(salesPost);

            return new MessageResponseDto("이미지 업로드가 완료되었습니다.", room.getId());
        } catch (ExpectedException e) {
            return MessageResponseDto.builder()
                    .message("이미지 업로드에 실패하였습니다.")
                    .subMessage(e.getMessage())
                    .build();
        }
    }
}
