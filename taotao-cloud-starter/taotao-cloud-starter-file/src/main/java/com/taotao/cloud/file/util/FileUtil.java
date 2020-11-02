package com.taotao.cloud.file.util;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.taotao.cloud.file.exception.FileTypeException;
import com.taotao.cloud.file.pojo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author dengtao
 * @date 2020/10/26 11:10
 * @since v1.0
 */
public class FileUtil {

    private final static Logger log = LoggerFactory.getLogger(File.class);

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取文件信息
     *
     * @param file
     * @return com.taotao.cloud.file.pojo.FileInfo
     * @author dengtao
     * @date 2020/10/26 10:43
     * @since v1.0
     */
    public static FileInfo getFileInfo(MultipartFile file) throws Exception {
        String md5 = fileMd5(file.getInputStream());
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(md5);
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        //fileInfo.setImg(isImage(file));
        fileInfo.setSize(file.getSize());
        fileInfo.setCreateTime(new Date());
        fileInfo.setFileType(FileTypeUtil.getType(file.getInputStream()));
        return fileInfo;
    }

    /**
     * 获取文件信息
     *
     * @param file
     * @return com.taotao.cloud.file.pojo.FileInfo
     * @author dengtao
     * @date 2020/10/26 10:43
     * @since v1.0
     */
    public static FileInfo getFileInfo(File file) throws Exception {
        String md5 = fileMd5(new FileInputStream(file));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(md5);
        fileInfo.setName(file.getName());
        fileInfo.setContentType(cn.hutool.core.io.FileUtil.getType(file));
        //fileInfo.setImg(isImage(file));
        fileInfo.setSize(cn.hutool.core.io.FileUtil.size(file));
        fileInfo.setCreateTime(new Date());
        fileInfo.setFileType(cn.hutool.core.io.FileUtil.getType(file));
        return fileInfo;
    }

    /**
     * 文件的md5
     *
     * @param inputStream
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:43
     * @since v1.0
     */
    public static String fileMd5(InputStream inputStream) {
        return DigestUtil.md5Hex(inputStream);
    }

    /**
     * 保存文件到本地
     *
     * @param file
     * @param path
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:43
     * @since v1.0
     */
    public static String saveFile(MultipartFile file, String path) {
        try {
            File targetFile = new File(path);
            if (targetFile.exists()) {
                return path;
            }
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            return path;
        } catch (Exception e) {
            log.error("saveFile-error", e);
            return null;
        }
    }


    /**
     * 删除本地文件
     *
     * @param pathname
     * @return boolean
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            boolean flag = file.delete();
            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }
            return flag;
        }
        return false;
    }

    /**
     * 判断文件格式
     *
     * @param file
     * @param acceptTypes
     * @return com.taotao.cloud.file.pojo.ResultBody
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    public static Boolean validType(MultipartFile file, String[] acceptTypes) {
        if (ArrayUtil.isEmpty(acceptTypes)) {
            return Boolean.TRUE;
        }
        try {
            String type = FileTypeUtil.getType(file.getInputStream());
            if (StrUtil.isBlank(type)) {
                type = cn.hutool.core.io.FileUtil.extName(file.getOriginalFilename());
            }
            if (ArrayUtil.contains(acceptTypes, type)) {
                return Boolean.TRUE;
            }
            throw new FileTypeException("文件格式错误");
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileTypeException("文件格式错误");
        }
    }

    /**
     * 编码文件名
     *
     * @param file
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    public static String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        return extractFilename(fileName, extension);
    }

    /**
     * 编码文件名
     *
     * @param fileName
     * @param extension
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    public static String extractFilename(String fileName, String extension) {
        fileName = DateUtil.format(new Date(), "yyyy/MM/dd/HH/mm") + "/" + encodingFilename(fileName) + "." + extension;
        return fileName;
    }


    private static int counter = 0;

    /**
     * 编码文件名
     *
     * @param fileName
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    private static String encodingFilename(String fileName) {
        fileName = fileName.replace("_", " ");
        fileName = SecureUtil.md5().digestHex(fileName + System.nanoTime() + counter++);
        return fileName;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/26 10:44
     * @since v1.0
     */
    public static String getExtension(MultipartFile file) {
        String extension = cn.hutool.core.io.FileUtil.extName(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 判断文件是否是图片
     *
     * @param file
     * @return boolean
     * @author dengtao
     * @date 2020/10/26 10:45
     * @since v1.0
     */
    public static boolean isImage(File file) {
        if (!file.exists()) {
            return false;
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            return image != null && image.getWidth() > 0 && image.getHeight() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否是图片
     *
     * @param file
     * @return boolean
     * @author dengtao
     * @date 2020/10/26 10:45
     * @since v1.0
     */
    public static boolean isImage(MultipartFile file) {
        if (file == null) {
            return false;
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
            return image != null && image.getWidth() > 0 && image.getHeight() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] getFileByteArray(File file) {
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = null;
        try (FileInputStream fi = new FileInputStream(file)) {
            buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
