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

import lombok.experimental.UtilityClass;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * NetworkUtils
 *
 * @author dengtao
 * @date 2020/6/2 16:37
 * @since v1.0
 */
@UtilityClass
public class NetworkUtil {

	/**
	 * 获取ip地址
	 *
	 * @return java.lang.String
	 * @author dengtao
	 * @date 2020/10/15 15:24
	 * @since v1.0
	 */
	public String getIpAddress() {
		String ipExclude = "";
		if (StringUtil.hasText(ipExclude)) {
			String regex = buildRegex(ipExclude);
			return getIpAddressExMatched(regex);
		}

		String ipInclude = "";
		if (StringUtil.hasText(ipInclude)) {
			String regex = buildRegex(ipInclude);
			return getIpAddressMatched(regex);
		}

		return getIpAddress0();
	}

	/**
	 * 获取ip地址
	 *
	 * @return java.lang.String
	 * @author dengtao
	 * @date 2020/10/15 15:24
	 * @since v1.0
	 */
	public String getIpAddress0() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp() || netInterface.isPointToPoint()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip instanceof Inet4Address) {
							return ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
		return "";
	}

	/**
	 * 获取指定网段地址
	 *
	 * @param regex 10.0.18 网址前两个或前三个地址段
	 * @return java.lang.String
	 * @author dengtao
	 * @date 2020/10/15 15:25
	 * @since v1.0
	 */
	public static String getIpAddressMatched(String regex) {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip instanceof Inet4Address) {
							String strIp = ip.getHostAddress();
							//如果匹配网段则返回
							if (Pattern.matches(regex, strIp)) {
								return strIp;
							}
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
		return "";
	}

	/**
	 * 获取指定网段地址
	 *
	 * @param regex 10.0.18 排除地址段，两个或前三个地址段
	 * @return java.lang.String
	 * @author dengtao
	 * @date 2020/10/15 15:25
	 * @since v1.0
	 */
	public static String getIpAddressExMatched(String regex) {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip instanceof Inet4Address) {
							String strIp = ip.getHostAddress();
							//如果不匹配匹配网段则返回;
							if (!Pattern.matches(regex, strIp)) {
								return strIp;
							}
						}
					}
				}
			}
		} catch (Exception ignored) {
		}
		return "";
	}

	/**
	 * 构建正则表达式
	 *
	 * @param source source
	 * @return java.lang.String
	 * @author dengtao
	 * @date 2020/10/15 15:25
	 * @since v1.0
	 */
	private static String buildRegex(String source) {
		StringBuilder sb = new StringBuilder();
		String[] strSource = source.split(",");
		for (String s : strSource) {
			sb.append("|(^").append(s).append(".*)");
		}
		String regex = sb.toString();
		if (!StringUtil.isEmpty(regex)) {
			//去掉开头|号
			return regex.substring(1);
		}
		return "";
	}
}
