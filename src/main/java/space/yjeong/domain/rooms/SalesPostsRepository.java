package space.yjeong.domain.rooms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesPostsRepository extends JpaRepository<SalesPosts, Long> {
    Optional<SalesPosts> findByRoomId(Long roomId);
}
