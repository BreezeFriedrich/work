package com.hysm.service;

import java.util.List;

import com.hysm.domain.Property;
import com.hysm.pojo.Domains;

public interface ICommonService {

	List<Domains> get_provice();

	List<Domains> get_city(String id);


	List<Domains> get_house_provice();

	List<Domains> get_house_city();

	List<Property> get_pro();

	List<Property> get_pro_values();

}
