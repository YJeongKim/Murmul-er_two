package space.yjeong.service.salespost;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.salespost.Image;
import space.yjeong.domain.salespost.ImageRepository;
import space.yjeong.domain.salespost.SalesPost;
import space.yjeong.exception.FileDownloadException;
import space.yjeong.exception.FileUploadException;
import space.yjeong.exception.UnableSaveImageException;
import space.yjeong.util.FileHelper;
import space.yjeong.util.FilePath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final int MIN_SIZE = 2;
    private final int MAX_SIZE = 10;

    private final ImageRepository imageRepository;
    private final FileHelper fileHelper;

    @Transactional
    public List<Image> saveImages(List<MultipartFile> fileList, SalesPost salesPost) {
        List<Image> images = new ArrayList<>();
        String path = FilePath.ROOT_PATH + FilePath.ROOM_IMG_PATH + salesPost.getId();
        File folder = fileHelper.createFolder(path);

        if (folder == null) throw new FileUploadException();

        if (fileList.size() < MIN_SIZE || fileList.size() > MAX_SIZE)
            throw new UnableSaveImageException(UnableSaveImageException.MESSAGE_SIZE);

        for (MultipartFile file : fileList) {
            if (checkImageExtension(file.getOriginalFilename()) == false)
                throw new UnableSaveImageException(UnableSaveImageException.MESSAGE_EXTENSION);
            if (checkImageMimeType(file) == false)
                throw new UnableSaveImageException(UnableSaveImageException.MESSAGE_MIME_TYPE);

            File imageFile = fileHelper.uploadFile(path, file);

            Image image = imageRepository.save(Image.builder()
                    .src(imageFile.getPath())
                    .filename(imageFile.getName())
                    .salesPost(salesPost)
                    .build());
            images.add(image);
        }
        return images;
    }

    public Path readImage(Long salesPostId, String imageName) {
        String filePath = FilePath.ROOT_PATH + FilePath.ROOM_IMG_PATH + salesPostId;

        File file = new File(filePath +  "/" + imageName);
        if(!file.exists()) throw new FileDownloadException();

        Path path = Paths.get(file.getAbsolutePath());

        return path;
    }

    @Transactional
    public void deleteImages(Long salesPostId) {
        String path = FilePath.ROOT_PATH + FilePath.ROOM_IMG_PATH + salesPostId;

        if(imageRepository.existsBySalesPostId(salesPostId))
            imageRepository.deleteAllBySalesPostId(salesPostId);

        fileHelper.deleteFolder(path);
    }

    private boolean checkImageExtension(String imageName) {
        String extension = imageName.substring(imageName.lastIndexOf(".") + 1);

        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("JPG")
                || extension.equals("png") || extension.equals("PNG"))
            return true;
        else return false;
    }

    private boolean checkImageMimeType(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Tika tika = new Tika();
            String mimeType = tika.detect(inputStream);

            if (mimeType.startsWith("image")) return true;
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
