package space.yjeong.web;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import space.yjeong.config.auth.dto.SessionUser;
import space.yjeong.service.room.RoomService;
import space.yjeong.service.salespost.ImageService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold 	= 1024,
        maxFileSize 		= -1,
        maxRequestSize		= -1
)
@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FileController {
    private final ImageService imageService;
    private final RoomService roomService;
    private final HttpSession httpSession;

    @ApiOperation("다중 이미지 파일 업로드")
    @ResponseBody
    @PostMapping(value = "/upload/images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadImageFiles(@RequestParam("images") List<MultipartFile> imageFiles) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.setImagesByUser(imageFiles, user));
    }
}