package com.example.cwgl.dao;

import com.example.cwgl.entity.Budget;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BudgetMapper {

    List<Budget> selectAll(Budget budget);

    void update(Budget budget);

    void delete(Integer id);

    Budget selectOne(Integer id);

    int add(Budget budget);

}
