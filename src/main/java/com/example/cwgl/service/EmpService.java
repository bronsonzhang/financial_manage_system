package com.example.cwgl.service;

import com.example.cwgl.dao.EmpMapper;
import com.example.cwgl.entity.Emp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    EmpMapper empMapper;

    public List<Emp> selectAll(int page, int rows, Emp emp) {
        PageHelper.startPage(page, rows);
        List<Emp> siteList=empMapper.selectAll(emp,(page-1)*rows,rows*page);
        return siteList;
    }
public int countEmp(){
  return   empMapper.countEmp();
}
    public void update(Emp emp) {
        empMapper.update(emp);
    }

    public void delete(Integer id) {
        empMapper.delete(id);
    }

    public Emp selectOne(Integer id) {
        return empMapper.selectOne(id);
    }

    public String add(Emp emp) {
        int flag = empMapper.add(emp);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
