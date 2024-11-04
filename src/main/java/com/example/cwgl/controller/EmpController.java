package com.example.cwgl.controller;

import com.example.cwgl.entity.Emp;
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

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    EmpService empService;

    @GetMapping
    public String add() {
        return "emp/emp-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Emp> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit,
                                          Emp emp) {
        List<Emp> sites = empService.selectAll(page, limit, emp);
        PageInfo<Emp> userPageInfo = new PageInfo<>(sites);
        int count = empService.countEmp();
        PageResultBean pageResultBean = new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
        pageResultBean.setCode(0);
        return new PageResultBean<>(count, userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(String username,String empNo,String password,int age,String sex,
                          double salary,double subsidy,double socialSecurity) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Emp emp=new Emp();
        emp.setUsername(username);
        emp.setAge(age);
        emp.setSex(sex);
        emp.setSalary(salary);
        emp.setSubsidy(subsidy);
        emp.setSocialSecurity(socialSecurity);
        emp.setDateTime(date);
        emp.setPassword(password);
        emp.setEmpNo(empNo);
        empService.add(emp);
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("empInfo", empService.selectOne(id));
        return "emp/emp-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(int id,String username,String empNo,String password,int age,
                             String sex,double salary,double subsidy,double socialSecurity) {
        Emp emp=new Emp();
        emp.setId(id);
        emp.setUsername(username);
        emp.setAge(age);
        emp.setSex(sex);
        emp.setSalary(salary);
        emp.setSubsidy(subsidy);
        emp.setSocialSecurity(socialSecurity);
        emp.setPassword(password);
        emp.setEmpNo(empNo);
        empService.update(emp);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        empService.delete(id);
        return ResultBean.success();
    }
}
