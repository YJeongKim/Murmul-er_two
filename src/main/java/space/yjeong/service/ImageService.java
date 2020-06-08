package space.yjeong.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.image.ImageRepository;
import space.yjeong.util.FilePath;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private static final int MINSIZE = 2;

    @Transactional
    public String saveImages(List<MultipartFile> imageFiles, Long roomId) {
        List<String> imagesSrc = new ArrayList<>();
        String path = FilePath.REPOSITORY_PATH + FilePath.ROOM_IMG_PATH + roomId;

        if(imageFiles.size() < MINSIZE) return "FAIL_SIZE";

        for(MultipartFile image : imageFiles) {
            if (checkImageExtension(image.getOriginalFilename()).equals("false")) throw new IllegalArgumentException();
            if (checkImageMimeType(image) == false) throw new IllegalArgumentException();
        }

        return "SUCCESS";
    }

    private String checkImageExtension(String imageName) {
        String extension = imageName.substring(imageName.lastIndexOf(".") + 1);
        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
            return extension;
        else return "false";
    }

    private boolean checkImageMimeType(MultipartFile image) {
        Tika tika = new Tika();
        return true;
    }

}
