package space.yjeong.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findBySalesPostId(Long salesPostId);

    @Query(nativeQuery = true, value = "select * from rooms r " +
            "where r.latitude >= :south and r.latitude <= :north " +
            "and r.longitude >= :west and r.longitude <= :east")
    List<Room> findAllByLocation(BigDecimal south, BigDecimal west, BigDecimal north, BigDecimal east);
}
