package space.yjeong.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalesPostRepository extends JpaRepository<SalesPost, Long> {
    Optional<SalesPost> findByRoomId(Long roomId);
    List<SalesPost> findAllBySalesUserId(Long salesUserId);
}
