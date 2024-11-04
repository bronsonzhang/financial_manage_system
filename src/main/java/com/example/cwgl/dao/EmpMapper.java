package com.example.cwgl.dao;

import com.example.cwgl.entity.Budget;
import com.example.cwgl.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {



    void update(Emp emp);

    void delete(Integer id);

    Emp selectOne(Integer id);

    int add(Emp emp);

    Emp selectByNo(String empNo);

    List<Emp> selectAll(Emp emp, int page, int rows);

    int countEmp();
}
