package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.hysm.mapper.sqlpro.PropertyPro;
import com.hysm.pojo.Property;

@Repository("propertyMapper")
public interface PropertyMapper {
	@InsertProvider(method="add_Pro",type=PropertyPro.class)
	
	int addPro(@Param("pro_name")String name,@Param("ptype")int ptype);

	@SelectProvider(method="get_property_list",type=PropertyPro.class )
	List<Property> get_property_list(@Param("beginNum")int beginNum, @Param("ps")int ps);
	
	@SelectProvider(method="get_property_count",type=PropertyPro.class )
	int get_property_count();

	@UpdateProvider(method="edit_property",type=PropertyPro.class )
	int edit_property(@Param("pro_name")String pro_name,@Param("state") int state,@Param("pro_id") int pro_id);

	@DeleteProvider(method="del_property",type=PropertyPro.class )
	int del_property(@Param("pro_id")int id);
	
	@SelectProvider(method="get_property",type=PropertyPro.class )
	Property get_property(@Param("pro_id")int pro_id);
	
	@SelectProvider(method="get_property_name",type=PropertyPro.class )
	List<Property> get_property_name();
}
