package com.taotao.cloud.file.propeties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Yihy
 * @version V1.0
 * @Package cc.yihy.storage
 * @Description:
 * @date 2017/6/27 15:31
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "taotao.cloud.file.ftp")
public class FtpProperties {
    private String host;

    private String port;

    private String username;

    private String password;

    private String domain;

    private String remoteDicrory;
}
