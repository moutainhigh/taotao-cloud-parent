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
package com.taotao.cloud.uc.biz.mapper;

import com.taotao.cloud.uc.api.dto.user.UserDTO;
import com.taotao.cloud.uc.api.vo.user.AddUserVO;
import com.taotao.cloud.uc.api.vo.user.UserVO;
import com.taotao.cloud.uc.biz.entity.SysUser;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dengtao
 * @date 2020/11/11 14:42
 * @since v1.0
 */
@Mapper(builder = @Builder(disableBuilder = true),
	unmappedSourcePolicy = ReportingPolicy.IGNORE,
	unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	/**
	 * SysUser转UserVO
	 *
	 * @param sysUser sysUser
	 * @return com.taotao.cloud.uc.api.vo.user.UserVO
	 * @author dengtao
	 * @date 2020/11/11 14:47
	 * @since v1.0
	 */
	UserVO sysUserToUserVO(SysUser sysUser);

	AddUserVO sysUserToAddUserVO(SysUser sysUser);


	/**
	 * list -> SysUser转UserVO
	 *
	 * @param userList userList
	 * @return java.util.List<com.taotao.cloud.uc.api.vo.user.UserVO>
	 * @author dengtao
	 * @date 2020/11/11 15:00
	 * @since v1.0
	 */
	List<UserVO> sysUserToUserVO(List<SysUser> userList);

	/**
	 * UserDTO转SysUser
	 *
	 * @param userDTO userDTO
	 * @return com.taotao.cloud.uc.biz.entity.SysUser
	 * @author dengtao
	 * @date 2020/11/11 14:52
	 * @since v1.0
	 */
	SysUser userDtoToSysUser(UserDTO userDTO);

	void copyUserDtoToSysUser(UserDTO userDTO, @MappingTarget SysUser user);
}
