package space.yjeong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.domain.rooms.*;
import space.yjeong.domain.user.User;
import space.yjeong.domain.user.UserRepository;
import space.yjeong.web.dto.rooms.RoomsReadResponseDto;
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

    @Transactional(readOnly = true)
    public List<RoomsReadResponseDto> readRooms(SessionUser sessionUser) {
        List<RoomsReadResponseDto> roomsReadResponseDtos = new ArrayList<>();

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        List<SalesPosts> salesPosts = salesPostsRepository.findAllBySalesUserId(user.getId());
        for(SalesPosts posts : salesPosts) {
            Rooms room = roomsRepository.findById(posts.getRoom().getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글에 대한 등록된 방 정보가 없습니다.")
            );
            roomsReadResponseDtos.add(RoomsReadResponseDto.builder()
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
                    .image(imagesRepository.findFirstByRoomId(room.getId()).getSrc())
                    .build());
        }
        return roomsReadResponseDtos;
    }

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

        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        Rooms room = roomsRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 없습니다. roomId=" + roomId)
        );
        room.update(requestDto.toRoomsEntity());

        List<Images> images = imagesRepository.findAllByRoom_Id(roomId);
        // TODO : 기존 등록된 이미지(url)과 새로 등록된 이미지(File) 검사 후 저장

        SalesPosts salesPosts = salesPostsRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );
        if(!salesPosts.getSalesUser().getId().equals(user.getId())) throw new SecurityException();
        salesPosts.update(requestDto.toSalesPostsEntity());

        if(hashTagsRepository.existsBySalesPostsId(salesPosts.getId()))
            hashTagsRepository.deleteAllBySalesPostsId(salesPosts.getId());
        if(!requestDto.getHashTags().isEmpty() && requestDto.getHashTags().get(0)!=null)
            salesPosts.setHashTags(hashTagsRepository.saveAll(requestDto.toHashTagsEntity(salesPosts)));

        return new RoomsResponseDto("수정이 완료되었습니다.");
    }

    @Transactional
    public RoomsResponseDto deleteRoom(Long roomId, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        Rooms room = roomsRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방이 없습니다. roomId=" + roomId)
        );

        List<Images> images = imagesRepository.findAllByRoom_Id(roomId);
        // TODO : 서버에 업로드된 이미지 파일 삭제
        imagesRepository.deleteAllByRoomId(roomId);

        SalesPosts salesPosts = salesPostsRepository.findByRoomId(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당 방에 대한 글이 없습니다. roomId=" + roomId)
        );

        if(!salesPosts.getSalesUser().getId().equals(user.getId())) throw new SecurityException();

        if(hashTagsRepository.existsBySalesPostsId(salesPosts.getId()))
            hashTagsRepository.deleteAllBySalesPostsId(salesPosts.getId());

        salesPostsRepository.delete(salesPosts);
        roomsRepository.delete(room);

        return new RoomsResponseDto("삭제가 완료되었습니다.");
    }

    @Transactional
    public RoomsResponseDto updatePostStatus(Long salesPostsId, PostStatus postStatus, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다.")
        );

        SalesPosts salesPosts = salesPostsRepository.findById(salesPostsId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 없습니다. salesPostsId=" + salesPostsId)
        );

        if(!salesPosts.getSalesUser().getId().equals(user.getId())) throw new SecurityException();

        salesPosts.updatePostStatus(postStatus);

        return new RoomsResponseDto("게시상태 수정이 완료되었습니다.");
    }
}
