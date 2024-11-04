package com.example.cwgl.controller;

import com.example.cwgl.dao.CallpayMapper;
import com.example.cwgl.dao.CustomerMapper;
import com.example.cwgl.entity.Callpay;
import com.example.cwgl.entity.Customer;
import com.example.cwgl.service.CallpayService;
import com.example.cwgl.utils.PageResultBean;
import com.example.cwgl.utils.ResultBean;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/callpay")
public class CallpayController {

    @Autowired
    CallpayService callpayService;

    @Autowired
    CallpayMapper callpayMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping
    public String add(Model model) {
        List<Customer> customerList = customerMapper.selectAll(null);
        model.addAttribute("customerList",customerList);
        return "callpay/callpay-add";
    }

    @GetMapping("cuijiao")
    public String cuijiao(Model model,Integer id) {
        model.addAttribute("callpayId",id);
        return "callpay/call";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Callpay> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit,
                                          Callpay callpay) {
        List<Callpay> sites = callpayService.selectAll(page, limit, callpay);
        PageInfo<Callpay> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(int customerId,String callNo,double cost,int status,String expireTime) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Callpay callpay=new Callpay();
        callpay.setCustomerId(customerId);
        callpay.setCallNo(callNo);
        callpay.setCost(cost);
        callpay.setStatus(status);
        callpay.setCreateTime(date);
        callpay.setExpireTime(expireTime);
        if (status==1){
            callpay.setPayTime(date);
        }
        callpayService.add(callpay);
        return ResultBean.success();
    }

    @PostMapping("callType")
    @ResponseBody
    public ResultBean callType(Integer callpayId,Integer callType) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Customer customer=callpayMapper.selectById(callpayId);
        if (callType==0){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("");
            message.setTo(customer.getEmail());
            message.setSubject("催缴信息");
            message.setText("尊敬的"+customer.getcName()+"，你有一笔缴费信息需要缴纳！");
            javaMailSender.send(message);
        }
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        List<Customer> customerList = customerMapper.selectAll(null);
        model.addAttribute("customerList",customerList);
        model.addAttribute("customer", callpayService.selectOne(id));
        return "callpay/callpay-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(int id,int customerId,String callNo,double cost,int status,String expireTime) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Callpay callpay=new Callpay();
        callpay.setId(id);
        callpay.setCustomerId(customerId);
        callpay.setCallNo(callNo);
        callpay.setCost(cost);
        callpay.setStatus(status);
        callpay.setExpireTime(expireTime);
        if (status==1){
            callpay.setPayTime(date);
        }
        callpayService.update(callpay);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        callpayService.delete(id);
        return ResultBean.success();
    }
}
