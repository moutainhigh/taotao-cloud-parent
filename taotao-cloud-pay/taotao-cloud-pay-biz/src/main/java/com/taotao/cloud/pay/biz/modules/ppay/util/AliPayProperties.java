package com.taotao.cloud.pay.biz.modules.ppay.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ali.pay")
public class AliPayProperties {

    private String mchId;
    private String key;
    private String notifyUrl;

}
