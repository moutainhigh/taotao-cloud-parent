package com.taotao.cloud.uc.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 角色DTO
 *
 * @author dengtao
 * @date 2020/5/14 10:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户-角色DTO")
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = -1972549738577159538L;

    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @Length(max = 20, message = "角色id列表不能为空")
    @ApiModelProperty(value = "角色id列表")
    private Set<Long> roleIds;
}
