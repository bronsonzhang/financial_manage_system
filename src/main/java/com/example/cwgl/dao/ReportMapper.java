package com.example.cwgl.dao;

import com.example.cwgl.entity.Customer;
import com.example.cwgl.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    List<Report> selectAll();

    void update(Report report);

    void delete(Integer id);

    Report selectOne(Integer id);

    int add(Report report);

}
