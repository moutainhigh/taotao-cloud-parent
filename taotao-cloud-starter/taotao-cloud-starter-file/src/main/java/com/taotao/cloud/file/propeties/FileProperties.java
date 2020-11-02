package com.taotao.cloud.file.propeties;

import com.taotao.cloud.file.canstants.FileCanstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 文件服务Properties
 *
 * @author dengtao
 * @date 2020/10/26 09:39
 * @since v1.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "taotao.cloud.file")
public class FileProperties {

    /**
     * 是否开启
     */
    private final Boolean enabled = false;

    /**
     * 类型
     */
    private final String type = FileCanstant.DFS_ALIYUN;

}
