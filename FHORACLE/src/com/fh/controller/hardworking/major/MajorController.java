package com.fh.controller.hardworking.major;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.hardworking.orgnization.Orgnization;
import com.fh.entity.system.Menu;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.service.hardworking.major.MajorService;
import com.fh.service.hardworking.orgnization.OrgnizationService;

/** 
 * 类名称：MajorController
 * 创建人：FH 
 * 创建时间：2015-05-12
 */
@Controller
@RequestMapping(value="/major")
public class MajorController extends BaseController {
	
	@Resource(name="majorService")
	private MajorService majorService;
	@Resource(name="orgnizationService")
	private OrgnizationService orgnizationService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Major");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取最大id
		String UID = String.valueOf(Integer.parseInt(majorService.findMaxId(pd).get("UID").toString())+1);
		
		pd.put("MAJOR_ID", UID);	//主键
		majorService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Major");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			majorService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Major");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		majorService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Major");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = majorService.list(page);	//列出Major列表
			List<Orgnization> orgnizationList = orgnizationService.listAllOrgnizations();	//列出Orgnization列表 用于下拉框
			mv.addObject("orgnizationList", orgnizationList);
			mv.addObject("orgnizationList", orgnizationList);
			mv.setViewName("hardworking/major/major_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Major页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Orgnization> orgnizationList = orgnizationService.listAllOrgnizations();	//列出Orgnization列表 用于下拉框
			mv.setViewName("hardworking/major/major_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("orgnizationList", orgnizationList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Major页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Orgnization> orgnizationList = orgnizationService.listAllOrgnizations();	//列出Orgnization列表 用于下拉框
			pd = majorService.findById(pd);	//根据ID读取
			mv.setViewName("hardworking/major/major_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("orgnizationList", orgnizationList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Major");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				majorService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Major到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("专业号");	//1
			titles.add("专业名称");	//2
			titles.add("专业简称");	//3
			titles.add("专业英文名称");	//4
			titles.add("所属学院");	//5
			dataMap.put("titles", titles);
			List<PageData> varOList = majorService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ZYH"));	//1
				vpd.put("var2", varOList.get(i).getString("ZYMC"));	//2
				vpd.put("var3", varOList.get(i).getString("ZYJC"));	//3
				vpd.put("var4", varOList.get(i).getString("ZYYWMC"));	//4
				vpd.put("var5", varOList.get(i).getString("ORGNIZATION_ID"));	//5
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("hardworking/major/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Major.xls", "Major.xls");
		
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "majorexcel");							//执行上传
			
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);	//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			/*存入数据库操作======================================*/
			
			/**
			 * var0 :机构编号
			 * var1 :机构名称
			 * var2 :创建日期
			 * var3 :状态
			 * var4 :等级
			 */
			for(int i=0;i<listPd.size();i++){		
				//获取最大id
				String UID = String.valueOf(Integer.parseInt(majorService.findMaxId(pd).get("UID").toString())+1);
				
				//获取学院字符串
				String orgnization_Id = listPd.get(i).getString("var4");
				
				//将获取的学院字符串传入方法，得到学院对应的id
				String ORGNIZATIONID = majorService.queryOrgnizationId(orgnization_Id);
				
				pd.put("MAJOR_ID", UID);										//ID
				pd.put("ZYH", listPd.get(i).getString("var0"));							//专业号
				pd.put("ZYMC", listPd.get(i).getString("var1"));							//专业名称
				pd.put("ZYJC", listPd.get(i).getString("var2"));						//专业简称
				pd.put("ZYYWMC", listPd.get(i).getString("var3"));							//专业英文名称
				pd.put("ORGNIZATION_ID", ORGNIZATIONID);							//学院id
				
				majorService.save(pd);
			}
			/*存入数据库操作======================================*/
			
			mv.addObject("msg","success");
		}
		
		mv.setViewName("save_result");
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
