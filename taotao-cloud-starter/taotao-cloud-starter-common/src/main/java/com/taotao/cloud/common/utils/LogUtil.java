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
package com.taotao.cloud.common.utils;

import com.taotao.cloud.common.constant.CommonConstant;
import lombok.experimental.UtilityClass;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

/**
 * LogUtil
 *
 * @author dengtao
 * @version v1.0.0
 * @date 2020/4/27 16:16
 */
@UtilityClass
public class LogUtil {
    /**
     * 空数组
     */
    private final Object[] EMPTY_ARRAY = new Object[]{};
    /**
     * 全类名
     */
    private final String FQDN = LogUtil.class.getName();

    /**
     * 获取栈中类信息
     *
     * @return org.slf4j.spi.LocationAwareLogger
     * @author dengtao
     * @date 2020/10/15 15:19
     * @since v1.0
     */
    public LocationAwareLogger getLocationAwareLogger() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        StackTraceElement frame = stackTraceElement[stackTraceElement.length - 1];

        return (LocationAwareLogger) LoggerFactory.getLogger(frame.getClassName() + "-" +
                frame.getMethodName().split("\\$")[0] + "-" +
                frame.getLineNumber());
    }

    /**
     * 封装Debug级别日志
     *
     * @param msg       msg
     * @param arguments 参数
     * @author dengtao
     * @date 2020/10/15 15:20
     * @since v1.0
     */
    public void debug(String msg, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            msg = MessageFormat.format(msg, arguments);
        }
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.DEBUG_INT, msg, EMPTY_ARRAY, null);
    }

    /**
     * 封装Info级别日志
     *
     * @param msg       msg
     * @param arguments 参数
     * @author dengtao
     * @date 2020/10/15 15:20
     * @since v1.0
     */
    public void info(String msg, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            msg = MessageFormat.format(msg, arguments);
        }
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.INFO_INT, msg, EMPTY_ARRAY, null);
    }

    /**
     * 封装Warn级别日志
     *
     * @param msg       msg
     * @param arguments 参数
     * @author dengtao
     * @date 2020/10/15 15:20
     * @since v1.0
     */
    public void warn(String msg, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            msg = MessageFormat.format(msg, arguments);
        }
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.WARN_INT, msg, EMPTY_ARRAY, null);
    }

    /**
     * 封装Error级别日志
     *
     * @param msg       msg
     * @param error     error
     * @param arguments 参数
     * @author dengtao
     * @date 2020/10/15 15:20
     * @since v1.0
     */
    public void error(String msg, Throwable error, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            msg = MessageFormat.format(msg, arguments);
        }
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.ERROR_INT, msg, EMPTY_ARRAY, error);
    }

    /**
     * 封装Error级别日志
     *
     * @param error error
     * @author dengtao
     * @date 2020/10/15 15:22
     * @since v1.0
     */
    public void error(Throwable error) {
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.ERROR_INT, null, EMPTY_ARRAY, error);
    }

    /**
     * 封装Error级别日志
     *
     * @param msg       msg
     * @param arguments 参数
     * @return void
     * @author dengtao
     * @date 2020/10/15 15:23
     * @since v1.0
     */
    public void error(String msg, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            msg = MessageFormat.format(msg, arguments);
        }
        getLocationAwareLogger().log(null, FQDN, LocationAwareLogger.ERROR_INT, msg, EMPTY_ARRAY, null);
    }

    public boolean isErrorEnabled() {
        return getLocationAwareLogger().isErrorEnabled();
    }

    public boolean isWarnEnabled() {
        return getLocationAwareLogger().isWarnEnabled();
    }

    public boolean isDebugEnabled() {
        return getLocationAwareLogger().isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return getLocationAwareLogger().isInfoEnabled();
    }

    /**
     * 异常堆栈转字符串
     *
     * @param e e
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:23
     * @since v1.0
     */
    public String exceptionToString(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            if (e == null) {
                return "无具体异常信息";
            }
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception ex) {
            return "";
        } finally {
            if (sw != null) {
                sw.flush();
            }
            if (pw != null) {
                pw.flush();
            }
            pw.close();
        }
    }

    /**
     * 获取堆栈信息
     *
     * @param throwable throwable
     * @return java.lang.String
     * @author dengtao
     * @date 2020/10/15 15:23
     * @since v1.0
     */
    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 获取操作类型
     *
     * @param methodName 方法名称
     * @return int
     * @author dengtao
     * @date 2020/10/15 15:23
     * @since v1.0
     */
    public int getOperateType(String methodName) {
        if (methodName.startsWith("get")) {
            return CommonConstant.OPERATE_TYPE_1;
        }
        if (methodName.startsWith("add")) {
            return CommonConstant.OPERATE_TYPE_2;
        }
        if (methodName.startsWith("update")) {
            return CommonConstant.OPERATE_TYPE_3;
        }
        if (methodName.startsWith("delete")) {
            return CommonConstant.OPERATE_TYPE_4;
        }
        return CommonConstant.OPERATE_TYPE_1;
    }
}
