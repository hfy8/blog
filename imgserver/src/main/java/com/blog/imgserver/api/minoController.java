package com.blog.imgserver.api;

import com.blog.common.model.ReturnMessage;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * minoController
 *
 * @author jeffrey
 * @description
 * @date created in 18:42 2020/12/7
 * @modifyBy
 */
@RestController
@RequestMapping("/minio")
public class minoController {

    @Autowired
    MinioClient minioClient;
    @Value("${minio.bucketName}")
    String bucketName;

    /**
     * 上传文件
     *
     * @param
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public ReturnMessage uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        if (file.isEmpty()) {
            ReturnMessage message = new ReturnMessage();
            message.setContent("空文件不能上传");
            return message;
        }
        File toFile = new File(fileName);
        InputStream in = file.getInputStream();
        Thumbnails.of(in).scale(0.5f).outputQuality(0.5f).toFile(toFile);
        FileInputStream fileInputStream=new FileInputStream(toFile);

        //文件上传到minio上的Name把文件后缀带上，不然下载出现格式问题
        //创建头部信息
        HashMap<String, String> headers = new HashMap<>(10);
        //添加自定义内容类型
        headers.put("Content-Type", "application/octet-stream");
        //添加存储类
        headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
        //上传
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                        fileInputStream, fileInputStream.available(), -1)
                        .headers(headers)
                        .build());
        in.close();
        return new ReturnMessage();
    }


    /**
     * 上传文件
     *
     * @param
     * @return
     */
    @GetMapping("/down/{fileName}")
    @ResponseBody
    public void downLoadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {

        InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        //响应输出流
        OutputStream outputStream = response.getOutputStream();

        int length = 0;
        //桶
        byte[] b = new byte[1024];

        while ((length = stream.read(b)) != -1) {
            outputStream.write(b, 0, length);
        }
        outputStream.close();
        stream.close();
    }

}
