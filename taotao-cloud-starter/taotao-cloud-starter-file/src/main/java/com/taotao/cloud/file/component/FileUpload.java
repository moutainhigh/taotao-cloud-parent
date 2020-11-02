package com.taotao.cloud.file.component;


import com.taotao.cloud.file.pojo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传接口
 *
 * @author dengtao
 * @date 2020/10/26 10:42
 * @since v1.0
 */
public interface FileUpload {
    FileInfo upload(File file) throws Exception;

    FileInfo upload(File file, String fileKey) throws Exception;

    FileInfo upload(MultipartFile file) throws Exception;

    FileInfo upload(MultipartFile file, String fileKey) throws Exception;

    FileInfo delete(FileInfo fileInfo);
}
