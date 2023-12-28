package com.ncamc.admin.service;

import com.ncamc.admin.bean.City;
import com.ncamc.admin.mapper.CityMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    CityMapper cityMapper;

    public City getById(Long id){
        return cityMapper.getById(id);
    }

    public void saveCity(City city) {
        cityMapper.insertCity(city);
    }
}
