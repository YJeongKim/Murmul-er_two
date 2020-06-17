package space.yjeong.service.salespost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.yjeong.domain.salespost.HashTag;
import space.yjeong.domain.salespost.HashTagRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public List<HashTag> saveHashTags(List<HashTag> hashTags) {
        return hashTagRepository.saveAll(hashTags);
    }

    public void deleteHashTags(Long salesPostId) {
        if (hashTagRepository.existsBySalesPostId(salesPostId))
            hashTagRepository.deleteAllBySalesPost(salesPostId);
    }

    public boolean isHashTagNotNull(List<String> hashTags) {
        if (hashTags.isEmpty()) return false;
        for (String hashTag : hashTags) {
            if (hashTag == null) return false;
        }
        return true;
    }
}
