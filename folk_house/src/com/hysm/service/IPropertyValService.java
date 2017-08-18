package com.hysm.service;

import java.util.List;

import com.hysm.pojo.PageBean;
import com.hysm.pojo.PropertyValue;


public interface IPropertyValService {
	
	PageBean<PropertyValue> get_propertyVal_list(int pageNum);

	List<PropertyValue> get_propertyVal_name();

	int addPropertyVal(int pro_id,String name);

	int del_propertyVal(int v_id);

	PropertyValue get_propertyVal(int v_id);

	int edit_propertyVal(int v_id,String name, int pro_id);

	
	
}
