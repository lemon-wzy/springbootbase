package com.lichi.springbootbase.controller.test;

import com.lichi.springbootbase.annotations.WebLog;
import com.lichi.springbootbase.response.ApiResponse;
import com.lichi.springbootbase.oss.minio.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: minio 接口
 * @author: lychee
 * @Date: 2023/1/30 23:37
 * @Version: 1.0
 * @Since: 2023/1/30
 */
@Slf4j
@RestController
@RequestMapping("/api/minio")
public class TestMinioController {
    @PostMapping("/upload")
    @WebLog(description = "上传文件")
    public ApiResponse<?> upload(List<MultipartFile> file) {
        try {
            return ApiResponse.success("上传成功", MinioService.upload(file,null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResponse.error("上传失败");
        }
    }
}
