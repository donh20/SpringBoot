package com.ncamc.admin.service.impl;

import com.ncamc.admin.bean.City;
import com.ncamc.admin.mapper.CityMapper;
import com.ncamc.admin.service.CityService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityMapper cityMapper;

    Counter counter;
    public CityServiceImpl(MeterRegistry meterRegistry){
        counter = meterRegistry.counter("cityService.saveCity.count");
    }

    public City getById(Long id){
        return cityMapper.getById(id);
    }

    public void saveCity(City city) {
        counter.increment();
        cityMapper.insertCity(city);
    }
}
