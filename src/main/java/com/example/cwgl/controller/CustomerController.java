package com.example.cwgl.controller;

import com.example.cwgl.entity.Customer;
import com.example.cwgl.entity.Emp;
import com.example.cwgl.service.CustomerService;
import com.example.cwgl.service.EmpService;
import com.example.cwgl.utils.PageResultBean;
import com.example.cwgl.utils.ResultBean;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 客户管理
 */

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    // 跳转到客户添加页面
    @GetMapping
    public String add() {
        return "customer/customer-add";
    }

    // 获取客户列表
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Customer> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit,
                                          Customer customer) {
        List<Customer> sites = customerService.selectAll(page, limit, customer);
        PageInfo<Customer> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    // 添加客户
    @PostMapping
    @ResponseBody
    public ResultBean add(String cName,String cNo,String password,String phone,String company,String address,String email) {

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Customer customer=new Customer(cName,phone,company,address,email,date);
        customer.setPassword(password);
        customer.setcNo(cNo);
        customerService.add(customer);
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("customerInfo", customerService.selectOne(id));
        return "customer/customer-add";
    }

    // 修改客户
    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Integer cId,String cNo,String cName,String password,String phone,
                             String company,String address,String email) {
        Customer customer=new Customer(cId,cName,phone,company,address,email);
        customer.setPassword(password);
        customer.setcNo(cNo);
        customerService.update(customer);
        return ResultBean.success();
    }

    // 删除
    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        customerService.delete(id);
        return ResultBean.success();
    }
}
