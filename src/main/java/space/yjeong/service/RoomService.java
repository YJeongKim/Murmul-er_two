package space.yjeong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.room.*;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
import space.yjeong.web.dto.MessageResponseDto;
import space.yjeong.web.dto.room.RoomSaveRequestDto;
import space.yjeong.web.dto.room.RoomUpdateRequestDto;
import space.yjeong.web.dto.room.RoomResponseDto;

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
        List<RoomResponseDto> roomResponseDtos = new ArrayList<>();

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        List<SalesPost> salesPosts = salesPostRepository.findAllBySalesUserId(user.getId());
        for(SalesPost posts : salesPosts) {
            Room room = roomRepository.findById(posts.getRoom().getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글에 대한 등록된 방 정보가 없습니다.")
            );
            roomResponseDtos.add(RoomResponseDto.builder()
                    .roomId(room.getId())
                    .salesPostId(posts.getId())
                    .postStatus(posts.getPostStatus())
                    .createDate(posts.getCreateDate())
                    .modifiedDate(posts.getModifiedDate())
                    .views(posts.getViews())
                    .title(posts.getTitle())
                    .address(room.getRoadAddress())
                    .lease(posts.getLease().getTitle())
                    .leasePeriod(posts.getLeasePeriod() + posts.getPeriodUnit().getTitle())
                    .leaseDeposit(posts.getLeaseDeposit())
                    .leaseFee(posts.getLeaseFee())
                    .maintenanceFee(posts.getMaintenanceFee())
                    .image(imageRepository.findFirstByRoomId(room.getId()).getSrc())
                    .build());
        }
        return roomResponseDtos;
    }

    @Transactional
    public MessageResponseDto saveRoom(RoomSaveRequestDto requestDto, SessionUser sessionUser) {
        List<String> imagesSrc = new ArrayList<>();
        // TODO : 이미지 검사 및 업로드
//        if (requestDto.getImages().size()<2) throw new NullPointerException();

        Room room = roomRepository.save(requestDto.toRoomsEntity());
        room.setImages(imageRepository.saveAll(requestDto.toImagesEntity(imagesSrc, room)));

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );
        // TODO : if user null or role is guest, throw exception

        SalesPost salesPost = salesPostRepository.save(requestDto.toSalesPostEntity(user, room));
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPost.setHashTags(hashTagRepository.saveAll(requestDto.toHashTagEntity(salesPost)));
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

        List<Image> images = imageRepository.findAllByRoomId(roomId);
        // TODO : 기존 등록된 이미지(url)과 새로 등록된 이미지(File) 검사 후 저장

        SalesPost salesPost = salesPostRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );
        if(!salesPost.getSalesUser().getId().equals(user.getId())) throw new SecurityException();
        salesPost.update(requestDto.toSalesPostEntity());

        if(hashTagRepository.existsBySalesPostId(salesPost.getId()))
            hashTagRepository.deleteAllBySalesPost(salesPost);
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPost.setHashTags(hashTagRepository.saveAll(requestDto.toHashTagEntity(salesPost)));

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

        List<Image> images = imageRepository.findAllByRoomId(roomId);
        // TODO : 서버에 업로드된 이미지 파일 삭제
        imageRepository.deleteAllByRoomId(roomId);

        SalesPost salesPost = salesPostRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );

        if(!salesPost.getSalesUser().getId().equals(user.getId())) throw new SecurityException();

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
