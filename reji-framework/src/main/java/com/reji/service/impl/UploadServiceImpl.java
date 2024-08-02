package com.reji.service.impl;

import com.reji.domain.ResponseResult;
import com.reji.enums.AppHttpCodeEnum;
import com.reji.handler.exception.SystemException;
import com.reji.service.UploadService;
import com.reji.utils.PathUtils;
import io.minio.*;
import io.minio.http.Method;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public ResponseResult uploadImg(MultipartFile img)  {
        // 判断文件类型或者文件大小
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();
        // 对原始文件名进行判断
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg"))
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        // 如果判断通过，上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    private String uploadOss(MultipartFile imgFile, String filePath) {
        try {
            InputStream inputStream = imgFile.getInputStream();
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endPoint)
                            .credentials(accessKey, secretKey)
                            .build();
            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
            // 上传
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .stream(inputStream, -1, 10485760)
                            .contentType("image/png")
                            .build());
            // 生成外链
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucket)
                            .object(filePath)
                            .expiry(2, TimeUnit.HOURS)
                            .build());
            System.out.println("previewUrl == " + url);
            System.out.println("pictureUrl == " + endPoint + "/" + bucket + "/" + filePath);
            return endPoint + "/" + bucket + "/" + filePath;
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
        return "有问题";
    }
}
