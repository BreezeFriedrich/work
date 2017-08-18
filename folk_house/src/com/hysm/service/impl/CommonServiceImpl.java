package com.hysm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.Property;
import com.hysm.mapper.CommonMapper;
import com.hysm.pojo.Domains;
import com.hysm.service.ICommonService;
@Service
public class CommonServiceImpl implements ICommonService{
	
	@Autowired
	private CommonMapper commonMapper;
	
	public List<Domains> get_provice() {
		return commonMapper.get_provice();
	}

	public List<Domains> get_city(String id) {
		return commonMapper.get_city(id);
	}


	public List<Domains> get_house_provice() {
		return commonMapper.get_house_provice();
	}

	public List<Domains> get_house_city() {
		return commonMapper.get_house_city();
	}

	public List<Property> get_pro() {
		return commonMapper.get_pro();
	}

	public List<Property> get_pro_values() {
		return commonMapper.get_pro_values();
	}

}
