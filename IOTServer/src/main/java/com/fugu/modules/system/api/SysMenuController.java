package com.fugu.modules.system.api;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.fugu.modules.common.api.BaseController;
import com.fugu.modules.shiro.service.ShiroService;
import com.fugu.modules.system.dto.input.MenuQueryPara;
import com.fugu.modules.system.dto.output.MenuTreeNode;
import com.fugu.modules.system.entity.Menu;
import com.google.common.collect.Lists;
import com.fugu.modules.common.dto.output.ApiResult;
import com.fugu.modules.system.service.IMenuService;
import com.fugu.utils.TreeBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p> 系统管理-菜单表  接口 </p>
 *
 * @author: fugu
 * @description:
 * @date: 2020-06-20
 *
 */
@RestController
@RequestMapping("/api/system/menu")
@Api(description = "系统管理 - 菜单表 接口")
public class SysMenuController extends BaseController {

    @Autowired
    IMenuService menuService;
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    private ShiroService shiroService;

//    @PostMapping(value = "/treeMenu", produces = "application/json;charset=utf-8")
//    @ApiOperation(value = "获取菜单树", httpMethod = "POST", response = ApiResult.class)
//    public ApiResult treeMenu() {
//       List<Menu> list = menuService.listTreeMenu();
//       List<MenuTreeNode> menuTreeNodeList = Lists.newArrayList();
//       if( list != null && !list.isEmpty() ){
//          list.forEach(temp->{
//              MenuTreeNode menuTreeNode = new MenuTreeNode();
//              BeanUtil.copyProperties( temp, menuTreeNode);
//              menuTreeNodeList.add( menuTreeNode );
//          });
//       }
//       List<MenuTreeNode> menuTreeNodeList2 = TreeBuilder.buildMenuTree( menuTreeNodeList );
//
//       menuTreeNodeList2.stream().sorted( Comparator.comparing( MenuTreeNode::getSortNo ) ).collect( Collectors.toList());
//       JSONObject json = new JSONObject();
//       json.put( "menuList", list);
//       json.put( "menuTree", menuTreeNodeList2);
//       return ApiResult.ok("获取菜单树成功", json);
//    }

    @PostMapping(value = "/save", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "保存菜单 ", httpMethod = "POST", response = ApiResult.class)
    public ApiResult save(@RequestBody @Validated Menu input) {
       Integer id = menuService.save(input);
       // 更新权限
//       shiroService.updatePermission(shiroFilterFactoryBean, null, false);
       return ApiResult.ok("保存菜单成功", id);
    }

    @PostMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", response = ApiResult.class)
    public ApiResult delete(@RequestBody MenuQueryPara input) {
       menuService.deleteById(input.getMenu_id());
       // 更新权限
//       shiroService.updatePermission(shiroFilterFactoryBean, null, false);
       return ApiResult.ok("删除菜单成功");
    }

    // 下面暂时不用 ================================================

    @PostMapping(value = "/listPage", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取系统管理-菜单表 列表分页", httpMethod = "POST", response = ApiResult.class)
    public ApiResult listPage(@RequestBody MenuQueryPara filter) {
       Page<Menu> page = new Page<>(filter.getPage(),filter.getLimit());
//       menuService.listPage(page, filter);
       return ApiResult.ok("获取系统管理-菜单表 列表分页成功", page);
    }

//    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
//    @ApiOperation(value = "获取系统管理-菜单表 列表", httpMethod = "POST", response = ApiResult.class)
//    public ApiResult list(@RequestBody MenuQueryPara filter) {
//       List<Menu> result = menuService.list();
//       return ApiResult.ok("获取系统管理-菜单表 列表成功",result);
//    }

    @PostMapping(value = "/getById", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取系统管理-菜单表 信息", httpMethod = "POST", response = ApiResult.class)
    public ApiResult getById(@RequestBody MenuQueryPara input) {
       Menu entity = menuService.selectById(input.getMenu_id());
       return ApiResult.ok("获取系统管理-菜单表 信息成功", entity);
    }

    @PostMapping(value = "/getByPid", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "根据父ID获取子菜单表信息(用于角色分配菜单)", httpMethod = "POST", response = ApiResult.class)
    public ApiResult getByPid(@RequestParam(value = "menu_id" ,defaultValue = "1")Integer menu_id) {
        List<Menu> list = menuService.selectByPid(menu_id);
        return ApiResult.ok("获取分配菜单表 信息成功", list);
    }


}
