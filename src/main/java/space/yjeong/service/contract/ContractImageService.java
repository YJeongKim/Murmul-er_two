package space.yjeong.service.contract;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.domain.salespost.Image;
import space.yjeong.exception.FileUploadException;
import space.yjeong.exception.UnableSaveImageException;
import space.yjeong.util.FileHelper;
import space.yjeong.util.FilePath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ContractImageService {

    private final FileHelper fileHelper;

    @Transactional
    public String saveContractImage(MultipartFile file, Long contractId, Long sublessor, Long sublessee) {
        List<Image> images = new ArrayList<>();
        String path = FilePath.ROOT_PATH + FilePath.CONTRACT_PATH;
        File folder = fileHelper.createFolder(path);

        if (folder == null) throw new FileUploadException();

        if (checkImageExtension(file.getOriginalFilename()) == false)
            throw new UnableSaveImageException(UnableSaveImageException.MESSAGE_EXTENSION);
        if (checkImageMimeType(file) == false)
            throw new UnableSaveImageException(UnableSaveImageException.MESSAGE_MIME_TYPE);

        String fileName = "contract" + contractId + "_" + sublessor + "-" + sublessee;

        File imageFile = fileHelper.uploadContract(path, fileName, file);

        return imageFile.getName();
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
