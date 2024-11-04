package com.example.cwgl.service;

import com.example.cwgl.dao.CustomerMapper;
import com.example.cwgl.dao.ReportMapper;
import com.example.cwgl.entity.Customer;
import com.example.cwgl.entity.Report;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportMapper reportMapper;

    public List<Report> selectAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<Report> siteList=reportMapper.selectAll();
        return siteList;
    }

    public void update(Report report) {
        reportMapper.update(report);
    }

    public void delete(Integer id) {
        reportMapper.delete(id);
    }

    public Report selectOne(Integer id) {
        return reportMapper.selectOne(id);
    }

    public String add(Report report) {
        int flag = reportMapper.add(report);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
