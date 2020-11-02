/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.file.configuration;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.*;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.file.canstants.FileCanstant;
import com.taotao.cloud.file.component.AbstractFileUpload;
import com.taotao.cloud.file.component.FtpClient;
import com.taotao.cloud.file.exception.FileUploadException;
import com.taotao.cloud.file.pojo.FileInfo;
import com.taotao.cloud.file.propeties.FtpProperties;
import com.taotao.cloud.file.util.FileUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author dengtao
 * @date 2020/10/26 10:28
 * @since v1.0
 */
@ConditionalOnProperty(name = "taotao.cloud.file.type", havingValue = FileCanstant.DFS_FTP)
public class FtpAutoConfiguration {
    private final FtpProperties properties;

    public FtpAutoConfiguration(FtpProperties properties) {
        super();
        Assert.notNull(properties, "FtpProperties为null");
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public FtpClient ftpClient() {
        FtpClient ftpClient = new FtpClient(properties.getHost(),
                properties.getPort(),
                properties.getUsername(),
                properties.getPassword(),
                properties.getRemoteDicrory());
        return ftpClient;
    }

    @Service
    public class FtpUpload extends AbstractFileUpload {
        private final FtpClient ftpClient;

        public FtpUpload(FtpClient ftpClient) {
            super();
            this.ftpClient = ftpClient;
        }

        @Override
        protected FileInfo uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
            try {
                boolean upload = ftpClient.upload(file.getName(), file.getInputStream());
                if (!upload) {
                    throw new FileUploadException("[Ftp]文件上传失败");
                }
                // todo 此处需要修改
                fileInfo.setUrl("");
                return fileInfo;
            } catch (Exception e) {
                LogUtil.error("[Ftp]文件上传失败:", e);
                throw new FileUploadException("[Ftp]文件上传失败");
            }
        }

        @Override
        protected FileInfo uploadFile(File file, FileInfo fileInfo) throws Exception {
            try {
                Boolean upload = ftpClient.upload(fileInfo.getName(), new FileInputStream(file));
                if (!upload) {
                    throw new FileUploadException("[Ftp]文件上传失败");
                }
                // todo 此处需要修改
                fileInfo.setUrl("");
                return fileInfo;
            } catch (Exception e) {
                LogUtil.error("[Ftp]文件上传失败", e);
                throw new FileUploadException("[Ftp]文件上传失败");
            }
        }

        @Override
        public FileInfo delete(FileInfo fileInfo) {
            try {
                ftpClient.remove(fileInfo.getUrl());
            } catch (Exception e) {
                LogUtil.error("[Ftp]文件删除失败:", e);
                throw new FileUploadException("[Ftp]文件删除失败");
            }
            return fileInfo;
        }
    }
}
