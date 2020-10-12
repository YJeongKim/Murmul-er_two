package space.yjeong.web.dto;

import lombok.Builder;
import lombok.Getter;
import space.yjeong.domain.room.Room;
import space.yjeong.domain.salespost.SalesPost;

@Getter
public class ContractResponseDto {
    private Long roomId;
    private Long salesPostId;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String buildingName;
    private Double area;
    private String roomType;
    private String lease;
    private Integer leaseDeposit;
    private Integer leaseFee;
    private Integer maintenanceFee;

    @Builder
    public ContractResponseDto(Long roomId, Long salesPostId, String roadAddress, String jibunAddress,
                               String detailAddress, String buildingName, Double area, String roomType,
                               String lease, Integer leaseDeposit, Integer leaseFee, Integer maintenanceFee) {
        this.roomId = roomId;
        this.salesPostId = salesPostId;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.detailAddress = detailAddress;
        this.buildingName = buildingName;
        this.area = area;
        this.roomType = roomType;
        this.lease = lease;
        this.leaseDeposit = leaseDeposit;
        this.leaseFee = leaseFee;
        this.maintenanceFee = maintenanceFee;
    }

    public static ContractResponseDto of(SalesPost salesPost) {
        Room room = salesPost.getRoom();
        return ContractResponseDto.builder()
                .roomId(room.getId())
                .salesPostId(salesPost.getId())
                .roadAddress(room.getRoadAddress())
                .jibunAddress(room.getJibunAddress())
                .detailAddress(room.getDetailAddress())
                .buildingName(room.getBuildingName())
                .area(room.getArea())
                .roomType(room.getRoomType().getTitle())
                .lease(salesPost.getLease().getTitle())
                .leaseDeposit(salesPost.getLeaseDeposit())
                .leaseFee(salesPost.getLeaseFee())
                .maintenanceFee(salesPost.getMaintenanceFee())
                .build();
    }
}
