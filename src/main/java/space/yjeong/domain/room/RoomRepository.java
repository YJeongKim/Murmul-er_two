package space.yjeong.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findBySalesPostId(Long salesPostId);
}
