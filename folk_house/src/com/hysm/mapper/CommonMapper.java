package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.hysm.domain.Property;
import com.hysm.mapper.sqlpro.CommonPro;
import com.hysm.pojo.Domains;

@Repository
public interface CommonMapper {

	@SelectProvider(method="get_provice",type=CommonPro.class )
	public List<Domains> get_provice() ;

	@SelectProvider(method="get_city",type=CommonPro.class )
	public List<Domains> get_city(String id);
	
	@SelectProvider(method="get_house_provice",type=CommonPro.class )
	List<Domains> get_house_provice();

	@SelectProvider(method="get_house_city",type=CommonPro.class )
	List<Domains> get_house_city();
	
	@SelectProvider(method="get_pro",type=CommonPro.class )
	List<Property> get_pro();

	@SelectProvider(method="get_pro_value",type=CommonPro.class )
	List<Property> get_pro_values();
}
