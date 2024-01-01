package com.ncamc.admin.service;

import com.ncamc.admin.bean.City;

public interface CityService {

    public City getById(Long id);

    public void saveCity(City city);
}
