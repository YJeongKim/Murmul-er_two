package space.yjeong.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileHelper {

    private Log logger = LogFactory.getLog(FileHelper.class);

    public File createFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists()) { // 폴더가 없는 경우 생성
            if (folder.mkdirs()) {
                logger.info(folder.getPath() + " 폴더 생성");
            } else {
                logger.error(folder.getPath() + " 폴더 생성 실패");
                return null;
            }
        } else {
            logger.info(folder.getPath() + " 폴더가 존재합니다.");
        }
        return folder;
    }

    public File createFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) { // 파일이 없는 경우 생성
            try {
                if (file.createNewFile()) {
                    logger.info(file.getPath() + " 파일 생성");
                } else {
                    logger.error(file.getPath() + " 파일 생성 실패");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info(file.getPath() + " 파일이 존재합니다.");
        }
        return file;
    }

    public boolean deleteFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) { // 폴더가 있는 경우 삭제
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.delete()) {
                    logger.info(file.getName() + " 파일 삭제");
                } else {
                    logger.info(file.getName() + " 파일 삭제 실패");
                    return false;
                }
            }
            if (folder.delete()) {
                logger.info(folder.getPath() + " 폴더 삭제");
            } else {
                logger.error(folder.getPath() + " 폴더 삭제 실패");
                return false;
            }
        } else {
            logger.info(folder.getPath() + " 폴더가 존재하지 않습니다.");
        }
        return true;
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) { // 파일이 있는 경우 삭제
            if (file.delete()) {
                logger.info(file.getPath() + " 파일 삭제");
            } else {
                logger.info(file.getPath() + " 파일 삭제 실패");
                return false;
            }
        } else {
            logger.info(file.getPath() + " 파일이 존재하지 않습니다.");
        }
        return true;
    }

    public File uploadFile(String path, MultipartFile multipartFile) {
        if (multipartFile == null) {
            logger.error("실패: 올바르지 않은 파일입니다.");
            return null;
        }
        logger.info("업로드 경로: " + path);

        String fileName = multipartFile.getOriginalFilename();
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        try {
            File saveFile = new File(path, fileName);
            if (saveFile.isFile()) { // 중복된 이름의 파일이 있는 경우
                int i = 1;
                String saveName = saveFile.getName();
                String name = saveName.substring(0, saveName.lastIndexOf("."));
                String extension = saveName.substring(saveName.lastIndexOf(".") + 1);
                while (true) {
                    if (saveFile.exists()) {
                        saveFile = new File(path, name + "(" + i + ")." + extension);
                        i++;
                    } else {
                        break;
                    }
                }
            }
            multipartFile.transferTo(saveFile);
            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
