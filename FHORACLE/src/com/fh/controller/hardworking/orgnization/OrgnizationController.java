package com.fh.controller.hardworking.orgnization;

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
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.hardworking.orgnization.Orgnization;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.Menu;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.GetPinyin;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Tools;
import com.fh.service.hardworking.orgnization.OrgnizationService;

/** 
 * 类名称：OrgnizationController
 * 创建人：FH 
 * 创建时间：2015-05-05
 */
@Controller
@RequestMapping(value="/orgnization")
public class OrgnizationController extends BaseController {
	
	@Resource(name="orgnizationService")
	private OrgnizationService orgnizationService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Orgnization");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//判断表中是否有数据，若没有数据将第一条数据id置为1
		if(orgnizationService.findMaxId(pd)==null){
			String UID = String.valueOf(1);
			pd.put("ORGNIZATION_ID", UID);	//主键
			System.err.println("1111:"+UID);
		}else{
			String UID = String.valueOf(Integer.parseInt(orgnizationService.findMaxId(pd).get("UID").toString())+1);
			pd.put("ORGNIZATION_ID", UID);	//主键
			System.err.println("2222:"+UID);
		}
		
		orgnizationService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Orgnization");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			orgnizationService.delete(pd);
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
		logBefore(logger, "修改Orgnization");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		orgnizationService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Orgnization");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = orgnizationService.list(page);	//列出Orgnization列表
			List<Orgnization> orgnizationList = orgnizationService.listAllOrgnizations();	//列出Orgnization列表 用于下拉框
			
			mv.setViewName("hardworking/orgnization/orgnization_list");
			mv.addObject("varList", varList);
			mv.addObject("orgnizationList", orgnizationList);
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
		logBefore(logger, "去新增Orgnization页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("hardworking/orgnization/orgnization_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
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
		logBefore(logger, "去修改Orgnization页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = orgnizationService.findById(pd);	//根据ID读取
			mv.setViewName("hardworking/orgnization/orgnization_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
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
		logBefore(logger, "批量删除Orgnization");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				orgnizationService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Orgnization到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("机构编码");	//1
			titles.add("机构名称");	//2
			titles.add("创建日期");	//3
			titles.add("状态");	//4
			titles.add("等级");	//5
			dataMap.put("titles", titles);
			List<PageData> varOList = orgnizationService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ORGCODE"));	//1
				vpd.put("var2", varOList.get(i).getString("ORGNAME"));	//2
				vpd.put("var3", varOList.get(i).getString("CREATEDATE"));	//3
				vpd.put("var4", varOList.get(i).getString("STATUS"));	//4
				vpd.put("var5", varOList.get(i).getString("RANK"));	//5
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
		mv.setViewName("hardworking/orgnization/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Orgnization.xls", "Orgnization.xls");
		
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
			String fileName =  FileUpload.fileUp(file, filePath, "orgnizationexcel");							//执行上传
			
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
				//判断表中是否有数据，若没有数据将第一条数据id置为1
				if(orgnizationService.findMaxId(pd)==null){
					String UID = String.valueOf(1);
					pd.put("ORGNIZATION_ID", UID);	//主键
					logBefore(logger, UID);
				}else{
					String UID = String.valueOf(Integer.parseInt(orgnizationService.findMaxId(pd).get("UID").toString())+1);
					pd.put("ORGNIZATION_ID", UID);	//主键
					logBefore(logger, UID);
				}
				
				pd.put("ORGCODE", listPd.get(i).getString("var0"));							//机构编号
				pd.put("ORGNAME", listPd.get(i).getString("var1"));							//机构名称
				pd.put("CREATEDATE", listPd.get(i).getString("var2"));						//创建日期
				
				if(listPd.get(i).getString("var3").equals("可用")&&listPd.get(i).getString("var3")!=null){
					pd.put("STATUS", 1);
				}else{
					pd.put("STATUS", 0);							//状态
				}
				
				pd.put("RANK", listPd.get(i).getString("var4"));							//等级
				
				orgnizationService.save(pd);
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
