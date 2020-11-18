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
package com.taotao.cloud.manager.resolver;

import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.manager.entity.Article;
import com.taotao.cloud.manager.entity.User;
import com.taotao.cloud.order.api.feign.RemoteOrderService;
import com.taotao.cloud.product.api.feign.RemoteProductService;
import com.taotao.cloud.uc.api.feign.RemoteUserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolver中的方法，入参及返回值类型，必须和graphqls文件中定义的一致，否则启动报错
 *
 * @author dengtao
 * @date 2020/11/9 09:54
 * @since v1.0
 */
@Component
public class QueryResolver implements GraphQLQueryResolver {

	@Autowired
	private RemoteUserService remoteUserService;
	@Autowired
	private RemoteProductService remoteProductService;
	@Autowired
	private RemoteOrderService remoteOrderService;

	public User user(String nickname) {
		LogUtil.info("Query Resolver ==> user");
		LogUtil.info("params: nickname:{}", nickname);
		User user = new User();
		user.setId("1");
		user.setNickname("addUserByInput");
		return user;
		// return userService.getUserByNickname(nickname);
	}

	public List<User> users() {
		LogUtil.info("Query Resolver ==> users");
		User user = new User();
		user.setId("1");
		user.setNickname("users");

		List<User> users = new ArrayList<>();
		users.add(user);
		return users;

		// return userService.listUsers();
	}

	public Article article(String title) {
		return new Article();
	}


}
