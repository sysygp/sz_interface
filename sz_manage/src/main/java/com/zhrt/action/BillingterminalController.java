package com.zhrt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.zhrt.entity.Billingterminal;
import com.zhrt.service.BillingterminalService;


@Controller
@RequestMapping("/manager/zhrt/billingterminal")
public class BillingterminalController extends BaseController {

	@Autowired
	private BillingterminalService billingterminalService;

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			
			Page page = new Page(50);
			String pn = request.getParameter("pageNo");
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			view.addObject("orderBy",orderByParam);
			view.addObject("order",orderParam);
			view.addObject("pageNo",pageNoParam);
			
			if (page.isAutoCount()) {
				int totalCount = billingterminalService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<Billingterminal> list = billingterminalService.getList(paramMap);
			page.setResult(list);
			view.addObject("page", page);
			
			view.addObject("toPage","../billingterminal/billinglist.jsp");
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			Billingterminal entity = new Billingterminal();
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = billingterminalService.getById(idparam);				
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../billingterminal/billingmodify.jsp");
			view.addObject("entity", entity);
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return view;
	}
	
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/billingterminal/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			if(StringUtils.isNotBlank(idparam)){
				billingterminalService.delById(idparam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/billingterminal/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids.length > 0){
				billingterminalService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/billingterminal/index"));
	}
}
