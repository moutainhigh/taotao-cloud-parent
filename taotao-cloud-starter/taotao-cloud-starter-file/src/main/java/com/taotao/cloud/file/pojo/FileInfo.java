package com.taotao.cloud.file.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @classname: FileInfo
 * @author: zhaojiacan
 * @description: file实体类
 * @date: 2020/6/2 16:40
 * @version:1.0
 */
@Data
public class FileInfo {

    /**
     * 文件标识id
     */
    private String id;

    /**
     * 原始文件名
     */
    private String name;

    /**
     * 是否图片
     */
    private Boolean isImg;

    /**
     * 上传文件类型
     */
    private String contentType;

    /**
     * 上传文件具体类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private long size;

    /**
     * oss访问路径
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
