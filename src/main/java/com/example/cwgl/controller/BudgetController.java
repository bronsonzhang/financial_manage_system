package com.example.cwgl.controller;

import com.example.cwgl.entity.Budget;
import com.example.cwgl.service.BudgetService;
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
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @GetMapping
    public String add() {
        return "budget/budget-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Budget> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit,
                                          Budget budget) {
        List<Budget> sites = budgetService.selectAll(page, limit, budget);
        PageInfo<Budget> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(String projectName,String content,double money,double cost,String remark,String completeTime) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Budget budget=new Budget();
        budget.setProjectName(projectName);
        budget.setContent(content);
        budget.setMoney(money);
        budget.setCost(cost);
        budget.setDateTime(date);
        budget.setRemark(remark);
        budget.setCompleteTime(completeTime);
        budgetService.add(budget);
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("budgetInfo", budgetService.selectOne(id));
        return "budget/budget-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(int id,String projectName,String content,double money,double cost,String remark,String completeTime) {
        Budget budget=new Budget();
        budget.setId(id);
        budget.setProjectName(projectName);
        budget.setContent(content);
        budget.setMoney(money);
        budget.setCost(cost);
        budget.setRemark(remark);
        budget.setCompleteTime(completeTime);
        budgetService.update(budget);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        budgetService.delete(id);
        return ResultBean.success();
    }
}
