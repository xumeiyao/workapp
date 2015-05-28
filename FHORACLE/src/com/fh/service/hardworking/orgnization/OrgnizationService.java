package com.fh.service.hardworking.orgnization;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.hardworking.orgnization.Orgnization;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;


@Service("orgnizationService")
public class OrgnizationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OrgnizationMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OrgnizationMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OrgnizationMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrgnizationMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)  
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrgnizationMapper.listAll", pd);
	}
	
	//用于下列列表
	public List<Orgnization> listAllOrgnizations() throws Exception {
		return (List<Orgnization>) dao.findForList("OrgnizationMapper.listAllOrgnizations", null);
		
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrgnizationMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OrgnizationMapper.deleteAll", ArrayDATA_IDS);
	}
	
	//取最大id
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrgnizationMapper.findMaxId", pd);
		
	}
	
}

