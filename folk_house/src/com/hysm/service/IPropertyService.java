package com.hysm.service;

import java.util.List;





import com.hysm.pojo.PageBean;
import com.hysm.pojo.Property;

public interface IPropertyService {
	
	PageBean<Property> get_property_list(int pageNum);

	List<Property> get_property_name();

	int addProperty(String pro_name,int protype);

	int del_property(int id);

	Property get_property(int pro_id);

	int edit_property(String pro_name, int state, int id);

	

}
