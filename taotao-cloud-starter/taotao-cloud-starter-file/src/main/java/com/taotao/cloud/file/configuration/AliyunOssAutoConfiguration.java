package com.taotao.cloud.file.configuration;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.file.canstants.FileCanstant;
import com.taotao.cloud.file.component.AbstractFileUpload;
import com.taotao.cloud.file.exception.FileUploadException;
import com.taotao.cloud.file.pojo.FileInfo;
import com.taotao.cloud.file.propeties.AliyunOssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * 阿里云oss自动配置
 *
 * @author dengtao
 * @date 2020/10/26 10:49
 * @since v1.0
 */
@ConditionalOnProperty(name = "taotao.cloud.file.type", havingValue = FileCanstant.DFS_ALIYUN)
public class AliyunOssAutoConfiguration {

    private final AliyunOssProperties properties;

    public AliyunOssAutoConfiguration(AliyunOssProperties properties) {
        super();
        Assert.notNull(properties, "AliyunOssProperties为null");
        this.properties = properties;
    }

    @Bean
    public OSS oss() {
        String endpoint = properties.getEndPoint();
        String accessKey = properties.getAccessKeyId();
        String secretKey = properties.getAccessKeySecret();
        return new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    }

    private String getAccessPath() {
        String accessPath = "";
        String endpoint = properties.getEndPoint();
        if (StrUtil.isNotBlank(endpoint)) {
            String[] split = endpoint.split("//");
            if (split.length == 2) {
                String bucketName = properties.getBucketName();
                accessPath = split[0] + "//" + bucketName + "." + split[1];
            }
        }
        return accessPath;
    }

    @Service
    public class AliossFileUpload extends AbstractFileUpload {

        private final OSS oss;

        public AliossFileUpload(OSS oss) {
            super();
            this.oss = oss;
        }

        @Override
        protected FileInfo uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
            try {
                oss.putObject(properties.getBucketName(), fileInfo.getName(), new ByteArrayInputStream(file.getBytes()));
                fileInfo.setUrl(getAccessPath() + "/" + fileInfo.getName());
                return fileInfo;
            } catch (Exception e) {
                LogUtil.error("[aliyun]文件上传失败:", e);
                throw new FileUploadException("[aliyun]文件上传失败");
            } finally {
                oss.shutdown();
            }
        }

        @Override
        protected FileInfo uploadFile(File file, FileInfo fileInfo) throws Exception {
            try {
                oss.putObject(properties.getBucketName(), fileInfo.getName(), file);
                fileInfo.setUrl(getAccessPath() + "/" + fileInfo.getName());
                return fileInfo;
            } catch (Exception e) {
                LogUtil.error("[aliyun]文件上传失败:", e);
                throw new FileUploadException("[aliyun]文件上传失败");
            } finally {
                oss.shutdown();
            }
        }

        @Override
        public FileInfo delete(FileInfo fileInfo) {
            try {
                oss.deleteObject(properties.getEndPoint(), fileInfo.getUrl());
            } catch (Exception e) {
                LogUtil.error("[aliyun]文件删除失败:", e);
                throw new FileUploadException("[aliyun]文件删除失败");
            }
            return fileInfo;
        }
    }
}
