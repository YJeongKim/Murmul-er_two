package space.yjeong.service.salespost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.yjeong.domain.hashtag.HashTag;
import space.yjeong.domain.hashtag.HashTagRepository;
import space.yjeong.domain.salespost.SalesPost;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public List<HashTag> saveHashTags(List<HashTag> hashTags) {
        return hashTagRepository.saveAll(hashTags);
    }

    public void deleteHashTags(SalesPost salesPost) {
        if (hashTagRepository.existsBySalesPostId(salesPost.getId()))
            hashTagRepository.deleteAllBySalesPost(salesPost);
    }

    public boolean isHashTagNotNull(List<String> hashTags) {
        if (hashTags.isEmpty()) return false;
        for (String hashTag : hashTags) {
            if (hashTag == null) return false;
        }
        return true;
    }
}
