package com.shui.fastdfs;

import com.shui.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("oss/fastdfs")
@Slf4j
public class UploadController {

    @PostMapping("/upload")
    @ApiOperation("upload")
    public Result<Map<String, Object>> singleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new Result<Map<String, Object>>().error("error");
        }

        try {
            String path = saveFile(file);
            Map<String, Object> data = new HashMap<>(2);
            data.put("src", path);
            return new Result<Map<String, Object>>().ok(data);
        } catch (Exception e) {
            log.error("upload file failed", e);
        }
        return null;
    }

    private String saveFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

        FastDFSFile file = new FastDFSFile();
        file.setContent(multipartFile.getBytes());
        file.setName(fileName);
        file.setExt(ext);

        String[] fileAbsolutePath = {};
        try {
            fileAbsolutePath = FastDFSClient.upload(file);
        } catch (Exception e) {
            log.error("上传文件异常", e);
        }
        if (fileAbsolutePath == null) {
            log.error("上传失败，请重新再试");
            throw new RuntimeException("上传失败");
        }
        return FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
    }
}
