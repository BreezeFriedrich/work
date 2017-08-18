package com.hysm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.mapper.PropertyMapper;
import com.hysm.pojo.Category;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Property;
import com.hysm.service.IPropertyService;
@Service
public class PropertyServiceImpl implements IPropertyService{

	@Autowired
	private PropertyMapper propertyMapper;
	
	/* 
	 * ÃÌº” Ù–‘
	 */
	public int addProperty(String pro_name, int ptype) {
		return propertyMapper.addPro(pro_name, ptype);
		
	}

	@Override
	public PageBean<Property> get_property_list(int pn) {
		PageBean<Property> pb=new PageBean<Property>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=propertyMapper.get_property_count();
		pb.setRowCount(rowCount);
		List<Property> list=propertyMapper.get_property_list(beginNum, ps);
		pb.setList(list);
		return pb;
	}

	@Override
	public List<Property> get_property_name() {
		
		return propertyMapper.get_property_name();
	}


	@Override
	public Property get_property(int pro_id) {
		return propertyMapper.get_property(pro_id);
	}
	
	

	@Override
	public int del_property(int pro_id) {
		
		return propertyMapper.del_property(pro_id);
	}

	@Override
	public int edit_property(String pro_name, int state, int id) {
		// TODO Auto-generated method stub
		return propertyMapper.edit_property(pro_name, state, id);
	}

}
