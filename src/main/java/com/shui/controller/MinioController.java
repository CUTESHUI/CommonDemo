package com.shui.controller;

import com.alibaba.fastjson.JSON;
import com.shui.exception.BaseException;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author shui
 */
@Api(tags = "minio测试")
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioClient minioClient;

    private static final String MINIO_BUCKET = "mybucket";

    @GetMapping("/list")
    @ApiOperation("获取列表")
    public List<Object> list() {
        try {
            Iterable<Result<Item>> myObjects = minioClient.listObjects(MINIO_BUCKET);
            Iterator<Result<Item>> iterator = myObjects.iterator();
            List<Object> items = new ArrayList<>();
            String format = "{'fileName':'%s','fileSize':'%s'}";
            while (iterator.hasNext()) {
                Item item = iterator.next().get();
                items.add(JSON.parse(String.format(format, item.objectName(), formatFileSize(item.size()))));
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public com.shui.common.Result<Map<String, Object>> upload(@RequestParam(name = "file", required = false) MultipartFile[] file) {
        if (file == null || file.length == 0) {
            throw new BaseException("上传文件不能为空");
        }

        List<String> orgfileNameList = new ArrayList<>(file.length);

        for (MultipartFile multipartFile : file) {
            String orgfileName = multipartFile.getOriginalFilename();
            orgfileNameList.add(orgfileName);

            try {
                InputStream in = multipartFile.getInputStream();
                minioClient.putObject(MINIO_BUCKET, orgfileName, in, new PutObjectOptions(in.available(), -1));
                in.close();
            } catch (Exception e) {
                throw new BaseException("上传失败");
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("bucketName", MINIO_BUCKET);
        data.put("fileName", orgfileNameList);
        return new com.shui.common.Result<Map<String, Object>>().ok(data);
    }

    @GetMapping("/download/{fileName}")
    @ApiOperation("下载文件")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        InputStream in = null;
        try {
            ObjectStat stat = minioClient.statObject(MINIO_BUCKET, fileName);
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            in = minioClient.getObject(MINIO_BUCKET, fileName);
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @DeleteMapping("/delete/{fileName}")
    @ApiOperation("删除文件")
    public com.shui.common.Result delete(@PathVariable("fileName") String fileName) {
        try {
            minioClient.removeObject(MINIO_BUCKET, fileName);
        } catch (Exception e) {
            return new com.shui.common.Result().error(e.getMessage());
        }
        return new com.shui.common.Result();
    }

    private static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + " GB";
        }
        return fileSizeString;
    }

}
