package space.yjeong.domain.rooms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findAllByRoom_Id(Long roomId);
}
