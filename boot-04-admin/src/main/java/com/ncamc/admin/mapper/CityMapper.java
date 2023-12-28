package com.ncamc.admin.mapper;

import com.ncamc.admin.bean.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CityMapper {

    @Select("select * from city where id=#{id}")
    public City getById(Long id);


    public void insertCity(City city);

}
