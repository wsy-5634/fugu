package com.fugu.modules.system.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.fugu.modules.common.api.BaseController;
import com.fugu.modules.shiro.service.ShiroService;
import com.fugu.modules.system.dto.input.RoleMenuQueryPara;
import com.fugu.modules.common.dto.output.ApiResult;
import com.fugu.modules.system.entity.RoleMenu;
import com.fugu.modules.system.service.IRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p> 系统管理 - 角色-菜单关联表  接口 </p>
 *
 * @author: fugu
 * @description:
 * @date: 2019-08-20
 *
 */
@RestController
@RequestMapping("/api/system/roleMenu")
@Api(description = "系统管理 - 角色-菜单关联表 接口")
public class SysRoleMenuController extends BaseController {

    @Autowired
    IRoleMenuService roleMenuService;
    @Autowired
    private ShiroService shiroService;

    @PostMapping(value = "/listPage", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取系统管理 - 角色-菜单关联表 列表分页", httpMethod = "POST", response = ApiResult.class)
    public ApiResult listPage(@RequestBody RoleMenuQueryPara filter) {
       Page<RoleMenu> page = new Page<>(filter.getPage(),filter.getLimit());
       roleMenuService.listPage(page, filter);
       return ApiResult.ok("获取系统管理 - 角色-菜单关联表 列表分页成功", page);
    }

    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "分配菜单 - 角色-菜单关联表 列表", httpMethod = "POST", response = ApiResult.class)
    public ApiResult list(@RequestBody RoleMenuQueryPara filter) {
       List<RoleMenu> result = roleMenuService.list(filter);
       return ApiResult.ok("获取系统管理 - 角色-菜单关联表 列表成功",result);
    }

    @PostMapping(value = "/update", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "修改角色菜单--分配菜单功能 ", httpMethod = "POST", response = ApiResult.class)
    public ApiResult saveOrUpdate(@RequestBody Map<Integer,Integer> map) {
       boolean b = roleMenuService.update(map);
        if (!b){
            return ApiResult.fail("修改失败");
        }
       return ApiResult.ok("修改角色菜单成功");
    }

    @PostMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "删除系统管理 - 角色-菜单关联表 ", httpMethod = "POST", response = ApiResult.class)
    public ApiResult delete(@RequestBody RoleMenuQueryPara input) {
       roleMenuService.deleteById(input.getId());
       return ApiResult.ok("删除系统管理 - 角色-菜单关联表 成功");
    }

    @PostMapping(value = "/detail", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "根据ID获取系统管理 - 角色-菜单关联表 信息", httpMethod = "POST", response = ApiResult.class)
    public ApiResult detail(@RequestBody RoleMenuQueryPara input) {
       RoleMenu entity = roleMenuService.selectById(input.getId());
       return ApiResult.ok("根据ID获取系统管理 - 角色-菜单关联表 信息成功", entity);
    }

    @PostMapping(value = "/saveRoleMenu", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "保存角色相关联菜单", httpMethod = "POST", response = ApiResult.class)
    public ApiResult saveRoleMenu(@RequestBody RoleMenuQueryPara input) {
       roleMenuService.saveRoleMenu( input );
       // 更新shiro权限
       shiroService.updatePermissionByRoleId(input.getRole_id(), false);
       return ApiResult.ok("保存角色相关联菜单成功");
    }

}
