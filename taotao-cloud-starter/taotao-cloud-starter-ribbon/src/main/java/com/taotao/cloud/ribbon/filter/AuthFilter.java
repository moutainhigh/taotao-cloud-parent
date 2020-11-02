// /*
//  * Copyright 2017-2020 original authors
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  * https://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */
// package com.taotao.cloud.ribbon.filter;
//
// import cn.hutool.core.util.StrUtil;
// import com.taotao.cloud.common.utils.ContextUtil;
// import com.taotao.cloud.core.utils.AuthUtil;
// import com.taotao.cloud.core.utils.ResponseUtil;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.common.OAuth2AccessToken;
// import org.springframework.security.oauth2.provider.OAuth2Authentication;
// import org.springframework.security.oauth2.provider.token.TokenStore;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Objects;
//
// /**
//  * AuthFilter
//  *
//  * @author dengtao
//  * @date 2020/7/31 15:51
//  * @since v1.0
//  */
// @Component
// public class AuthFilter extends OncePerRequestFilter {
//
//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//         String from = request.getHeader("from");
//         String uri = request.getRequestURI();
//         if ("/api-docs".equals(uri)
//                 || "/oauth/token".equals(uri)
//                 || "/auth/login".equals(uri)
//                 || "/custom/confirm_access".equals(uri)
//                 || "/oauth/authorize".equals(uri)) {
//             return true;
//         }
//         if ("in".equals(from)) {
//             return true;
//         }
//         return false;
//     }
//
//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String token = AuthUtil.extractToken(request);
//         if (StrUtil.isBlank(token)) {
//             ResponseUtil.failed(response, "请求未带token");
//             return;
//         }
//         TokenStore tokenstore = ContextUtil.getBean(TokenStore.class, true);
//         OAuth2AccessToken oAuth2AccessToken = tokenstore.readAccessToken(token);
//         if (Objects.isNull(oAuth2AccessToken)) {
//             ResponseUtil.failed(response, "用户未登录");
//             return;
//         }
//         int expiresIn = oAuth2AccessToken.getExpiresIn();
//         if (expiresIn <= 0) {
//             ResponseUtil.failed(response, "用户已失效,请重新登录");
//             return;
//         }
//
//         OAuth2Authentication oAuth2Authentication = tokenstore.readAuthentication(token);
//         SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
//
//         filterChain.doFilter(request, response);
//     }
// }
