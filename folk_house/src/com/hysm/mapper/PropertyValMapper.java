package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.hysm.mapper.sqlpro.PropertyPro;
import com.hysm.mapper.sqlpro.PropertyValPro;
import com.hysm.mapper.sqlpro.SysPro;
import com.hysm.pojo.Category;
import com.hysm.pojo.Property;
import com.hysm.pojo.PropertyValue;

@Repository("propertyValMapper")
public interface PropertyValMapper {
	@InsertProvider(method="add_ProVal",type=PropertyValPro.class)
	
	int addProVal(@Param("pro_id")int pro_id,@Param("name")String name);

	@SelectProvider(method="get_propertyVal_list",type=PropertyValPro.class )
	List<PropertyValue> get_propertyVal_list(@Param("beginNum")int beginNum, @Param("ps")int ps);
	
	@SelectProvider(method="get_propertyVal_count",type=PropertyValPro.class )
	int get_propertyVal_count();

	@UpdateProvider(method="edit_propertyVal",type=PropertyValPro.class )
	int edit_propertyVal(@Param("v_id") int v_id,@Param("name")String name,@Param("pro_id")int pro_id);

	@DeleteProvider(method="del_propertyVal",type=PropertyValPro.class )
	int del_propertyVal(@Param("v_id")int id);
	
	@SelectProvider(method="get_propertyVal",type=PropertyValPro.class )
	PropertyValue get_propertyVal(@Param("v_id")int v_id);
	
	@SelectProvider(method="get_propertyVal_name",type=PropertyValPro.class )
	List<PropertyValue> get_propertyVal_name();
	

}
