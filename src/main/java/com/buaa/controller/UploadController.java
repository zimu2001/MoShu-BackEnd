package com.buaa.controller;

import com.buaa.configs.UploadConfig;
import com.buaa.controller.utils.R;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadConfig uploadConfig;

    @ApiOperation(value = "文件上传")
    @CrossOrigin
    @PostMapping("/cos")
    public R upload(MultipartFile file) throws IOException {
        if(file == null){
            System.out.println("空文件");
            return new R(false, null, "文件为空");
        }
        // 生成随机的新文件名
        String oldFileName = file.getOriginalFilename();
        assert oldFileName != null;
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID()+eName;

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);
        // 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(uploadConfig.getAccessKey(), uploadConfig.getSecretKey());
        // 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(uploadConfig.getRegionName()));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        String bucketName = uploadConfig.getBucketName();

        File localFile;
        try {
            localFile = File.createTempFile("temp",null);
            file.transferTo(localFile);
            // 指定要上传到cos上的路径
            String key = "/"+uploadConfig.getFolderPrefix()+"/"+year+"/"+month+"/"+day+"/"+newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
//            System.out.println(uploadConfig.getBaseUrl() + putObjectRequest.getKey());
            return new R(true, uploadConfig.getBaseUrl() + putObjectRequest.getKey(), "上传成功");
        } finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }
    }
}
