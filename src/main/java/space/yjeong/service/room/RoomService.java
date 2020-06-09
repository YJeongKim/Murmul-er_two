package space.yjeong.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.hashtag.HashTagRepository;
import space.yjeong.domain.image.Image;
import space.yjeong.domain.image.ImageRepository;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.room.RoomRepository;
import space.yjeong.domain.salespost.PostStatus;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.domain.salespost.SalesPostRepository;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
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
    private final SalesPostRepository salesPostRepository;
    private final ImageRepository imageRepository;
    private final HashTagRepository hashTagRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<RoomResponseDto> readRooms(SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        List<SalesPost> salesPosts = salesPostRepository.findAllBySalesUserId(user.getId());

        List<RoomResponseDto> roomResponseDtoList = RoomResponseDto.listOf(salesPosts);

        return roomResponseDtoList;
    }

    @Transactional
    public MessageResponseDto saveRoom(RoomSaveRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        Room room = roomRepository.save(requestDto.toRoomsEntity());

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        // TODO : if user null or role is guest, throw exception

        SalesPost salesPost = salesPostRepository.save(requestDto.toSalesPostEntity(user, room));
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPost.setHashTags(hashTagRepository.saveAll(requestDto.toHashTagEntity(salesPost)));
        salesPost.setImages(imageRepository.saveAll(requestDto.toImagesEntity(imagesSrc, salesPost)));
        room.setSalesPost(salesPost);

        return new MessageResponseDto("등록이 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto updateRoom(Long roomId, RoomUpdateRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 없습니다. roomId=" + roomId)
        );
        room.update(requestDto.toRoomEntity());

        SalesPost salesPost = salesPostRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );
        if(!salesPost.getSalesUser().getId().equals(user.getId())) throw new SecurityException();
        salesPost.update(requestDto.toSalesPostEntity());

        if(hashTagRepository.existsBySalesPostId(salesPost.getId()))
            hashTagRepository.deleteAllBySalesPost(salesPost);
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPost.setHashTags(hashTagRepository.saveAll(requestDto.toHashTagEntity(salesPost)));

        List<Image> images = imageRepository.findAllBySalesPostId(salesPost.getId());
        // TODO : 기존 등록된 이미지(url)과 새로 등록된 이미지(File) 검사 후 저장

        return new MessageResponseDto("수정이 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto deleteRoom(Long roomId, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 없습니다. roomId=" + roomId)
        );

        SalesPost salesPost = salesPostRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );

        if(!salesPost.getSalesUser().getId().equals(user.getId())) throw new SecurityException();

        // TODO : 서버에 업로드된 이미지 파일 삭제
        imageRepository.deleteAllBySalesPostId(salesPost.getId());

        if(hashTagRepository.existsBySalesPostId(salesPost.getId()))
            hashTagRepository.deleteAllBySalesPost(salesPost);

        salesPostRepository.delete(salesPost);
        roomRepository.delete(room);

        return new MessageResponseDto("삭제가 완료되었습니다.");
    }

    @Transactional
    public MessageResponseDto updatePostStatus(Long salesPostId, PostStatus postStatus, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        SalesPost salesPost = salesPostRepository.findById(salesPostId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 없습니다. salesPostId=" + salesPostId)
        );

        if(!salesPost.getSalesUser().getId().equals(user.getId())) throw new SecurityException();

        salesPost.updatePostStatus(postStatus);

        return new MessageResponseDto("게시상태 수정이 완료되었습니다.");
    }
}
