package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.service.ImageService;

import javax.servlet.annotation.MultipartConfig;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold 	= 1024,
        maxFileSize 		= -1,
        maxRequestSize		= -1,
        location 		    = "C:/fileRepository"
)
@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FileController {
    private final ImageService imageService;

    @ApiOperation("다중 이미지 파일 업로드")
    @PostMapping("/upload/images")
    public ResponseEntity uploadImageFiles(@RequestParam List<MultipartFile> imageFiles, @RequestParam Long roomId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.saveImages(imageFiles, roomId));
    }
}
