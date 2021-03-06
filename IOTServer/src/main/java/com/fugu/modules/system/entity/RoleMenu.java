package com.fugu.modules.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.fugu.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>  系统管理 - 角色-菜单关联表  </p>
 *
 * @author: fugu
 * @date: 2019-08-20
 */
@Data
@ApiModel(description = "系统管理 - 角色-菜单关联表 ")
@TableName("t_sys_role_menu")
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty(value = "主键")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 角色ID
     */
	@ApiModelProperty(value = "角色ID")
	@TableField("role_id")
	private Integer role_id;
    /**
     * 菜单ID
     */
	@ApiModelProperty(value = "菜单ID")
	@TableField("menu_id")
	private Integer menu_id;

	@ApiModelProperty(value = "是否启用该菜单")
	@TableField("status")
	private Integer status;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
