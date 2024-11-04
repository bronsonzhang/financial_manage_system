package com.example.cwgl.service;

import com.example.cwgl.dao.CallpayMapper;
import com.example.cwgl.dao.EmpMapper;
import com.example.cwgl.entity.Callpay;
import com.example.cwgl.entity.Emp;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallpayService {

    @Autowired
    CallpayMapper callpayMapper;

    public List<Callpay> selectAll(int page, int rows, Callpay callpay) {
        PageHelper.startPage(page, rows);
        List<Callpay> siteList=callpayMapper.selectAll(callpay);
        return siteList;
    }

    public void update(Callpay callpay) {
        callpayMapper.update(callpay);
    }

    public void delete(Integer id) {
        callpayMapper.delete(id);
    }

    public Callpay selectOne(Integer id) {
        return callpayMapper.selectOne(id);
    }

    public String add(Callpay callpay) {
        int flag = callpayMapper.add(callpay);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
