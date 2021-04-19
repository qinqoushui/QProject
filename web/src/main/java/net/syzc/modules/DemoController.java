/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules;

import net.syzc.modules.pm.entity.PmProjBase;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;

/**
 * 演示实例Controller
 * @author ThinkGem
 * @version 2018-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/demo")
public class DemoController extends BaseController {


	
	/**
	 * DataGrid
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "dataGrid/{viewName}")
	public String dataGrid(@PathVariable String viewName, PmProjBase testData, Model model) {
		return "modules/demo/demoDataGrid" + StringUtils.cap(viewName);
	}
	
	/**
	 * Form
	 */
	@RequiresPermissions("test:testData:view")
	@RequestMapping(value = "form/{viewName}")
	public String form(@PathVariable String viewName, PmProjBase testData, Model model) {
		return "modules/demo/demoForm" + StringUtils.cap(viewName);
	}
	
}