package com.example.cwgl.dao;

import com.example.cwgl.entity.Callpay;
import com.example.cwgl.entity.Customer;
import com.example.cwgl.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CallpayMapper {

    List<Callpay> selectAll(Callpay callpay);

    void update(Callpay callpay);

    void delete(Integer id);

    Callpay selectOne(Integer id);

    int add(Callpay callpay);

    Customer selectById(Integer callpayId);
}
