package com.example.cwgl.dao;

import com.example.cwgl.entity.Customer;
import com.example.cwgl.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {

    List<Customer> selectAll(Customer customer);

    void update(Customer customer);

    void delete(Integer id);

    Customer selectOne(Integer id);

    int add(Customer customer);

    Customer selectByNo(String cNo);
}
