package com.taotao.cloud.dfs.biz.entity;

import com.taotao.cloud.data.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * <p>
 * 实体类
 * 附件
 * </p>
 *
 * @author zuihou
 * @since 2019-06-24
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_attachment")
@org.hibernate.annotations.Table(appliesTo = "t_attachment", comment = "附件表")
public class Attachment extends BaseEntity {

    /**
     * 业务ID
     */
    @Column(name = "biz_id", nullable = false, columnDefinition = "bigint not null comment '业务ID'")
    private Long bizId;

    /**
     * 业务类型
     * #AttachmentType
     */
    @Column(name = "biz_type", nullable = false, columnDefinition = "varchar(32) not null comment '业务类型'")
    private String bizType;

    /**
     * 数据类型
     * #DataType{DIR:目录;IMAGE:图片;VIDEO:视频;AUDIO:音频;DOC:文档;OTHER:其他}
     */
    @Column(name = "data_type", nullable = false, columnDefinition = "varchar(32) not null comment '数据类型'")
    private String dataType;

    /**
     * 原始文件名
     */
    @Column(name = "original_file_name", nullable = false, columnDefinition = "varchar(255) not null comment '原始文件名'")
    private String originalFileName;

    /**
     * FastDFS返回的组
     * 用于FastDFS
     */
    @Column(name = "dfs_group", columnDefinition = "varchar(255) default '' comment 'FastDFS返回的组'")
    private String dfsGroup;

    /**
     * FastDFS的远程文件名
     * 用于FastDFS
     */
    @Column(name = "dfs_path", columnDefinition = "varchar(255) default '' comment 'FastDFS的远程文件名'")
    private String dfsPath;

    /**
     * 文件相对路径
     */
    @Column(name = "relative_path", columnDefinition = "varchar(255) default '' comment '文件相对路径'")
    private String relativePath;

    /**
     * 文件访问链接
     * 需要通过nginx配置路由，才能访问
     */
    @Column(name = "url", nullable = false, columnDefinition = "varchar(255) not null comment '文件访问链接'")
    private String url;

    /**
     * 文件md5值
     */
    @Column(name = "file_md5", nullable = false, columnDefinition = "varchar(255) not null comment '文件md5值'")
    private String fileMd5;

    /**
     * 文件上传类型
     * 取上传文件的值
     */
    @Column(name = "context_type", nullable = false, columnDefinition = "varchar(255) not null comment '文件上传类型'")
    private String contextType;

    /**
     * 唯一文件名
     */
    @Column(name = "filename", nullable = false, columnDefinition = "varchar(255) not null comment '唯一文件名'")
    private String filename;

    /**
     * 后缀
     * (没有.)
     */
    @Column(name = "ext", nullable = false, columnDefinition = "varchar(64) not null comment '后缀'")
    private String ext;

    /**
     * 大小
     */
    @Column(name = "size", nullable = false, columnDefinition = "bigint not null comment '大小'")
    private Long size;
}
