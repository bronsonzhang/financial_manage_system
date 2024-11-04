package com.example.cwgl.service;

import com.example.cwgl.dao.BudgetMapper;
import com.example.cwgl.entity.Budget;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    BudgetMapper budgetMapper;

    public List<Budget> selectAll(int page, int rows, Budget budget) {
        PageHelper.startPage(page, rows);
        List<Budget> siteList=budgetMapper.selectAll(budget);
        return siteList;
    }

    public void update(Budget budget) {
        budgetMapper.update(budget);
    }

    public void delete(Integer id) {
        budgetMapper.delete(id);
    }

    public Budget selectOne(Integer id) {
        return budgetMapper.selectOne(id);
    }

    public String add(Budget budget) {
        int flag = budgetMapper.add(budget);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
