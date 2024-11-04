package com.example.cwgl.service;

import com.example.cwgl.dao.CustomerMapper;
import com.example.cwgl.entity.Customer;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    public List<Customer> selectAll(int page, int rows, Customer customer) {
        PageHelper.startPage(page, rows);
        List<Customer> siteList=customerMapper.selectAll(customer);
        return siteList;
    }

    public void update(Customer customer) {
        customerMapper.update(customer);
    }

    public void delete(Integer id) {
        customerMapper.delete(id);
    }

    public Customer selectOne(Integer id) {
        return customerMapper.selectOne(id);
    }

    public String add(Customer customer) {
        int flag = customerMapper.add(customer);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
