package com.hysm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.mapper.PropertyMapper;
import com.hysm.mapper.PropertyValMapper;
import com.hysm.pojo.Category;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Property;
import com.hysm.pojo.PropertyValue;
import com.hysm.service.IPropertyService;
import com.hysm.service.IPropertyValService;
@Service
public class PropertyValServiceImpl implements IPropertyValService{

	@Autowired
	private PropertyValMapper propertyValMapper;
	
	
	public PageBean<PropertyValue> get_propertyVal_list(int pn) {
		PageBean<PropertyValue> pb=new PageBean<PropertyValue>();
		int ps=10;
		int beginNum=(pn-1)*ps;
		pb.setPageSize(ps);
		pb.setPageNum(pn);
		int rowCount=propertyValMapper.get_propertyVal_count();
		pb.setRowCount(rowCount);
		List<PropertyValue> list=propertyValMapper.get_propertyVal_list(beginNum, ps);
		pb.setList(list);
		return pb;
	}

	public List<PropertyValue> get_propertyVal_name() {
		return propertyValMapper.get_propertyVal_name();
	}

	public int addPropertyVal(int pro_id,String name ) {
		return propertyValMapper.addProVal(pro_id, name);
	}

	public int del_propertyVal(int v_id) {
		return propertyValMapper.del_propertyVal(v_id);
	}

	public PropertyValue get_propertyVal(int v_id) {
		return propertyValMapper.get_propertyVal(v_id);
	}

	public int edit_propertyVal(int v_id, String name, int pro_id) {
		return propertyValMapper.edit_propertyVal(v_id, name, pro_id);
	}

	

}
