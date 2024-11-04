package com.example.cwgl.controller;

import com.example.cwgl.entity.Report;
import com.example.cwgl.service.ReportService;
import com.example.cwgl.utils.PageResultBean;
import com.example.cwgl.utils.ResultBean;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping
    public String add() {
        return "report/report-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Report> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Report> sites = reportService.selectAll(page, limit);
        PageInfo<Report> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(String reportName,String reportPurpose,String reportPath) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=dateFormat.format(new Date());
        Report report=new Report();
        report.setReportName(reportName);
        report.setReportPurpose(reportPurpose);
        report.setReportPath(reportPath);
        report.setDateTime(date);
        reportService.add(report);
        return ResultBean.success();
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("report", reportService.selectOne(id));
        return "report/report-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Integer id,String reportName,String reportPurpose,String reportPath) {
        Report report=new Report();
        report.setId(id);
        report.setReportName(reportName);
        report.setReportPurpose(reportPurpose);
        report.setReportPath(reportPath);
        reportService.update(report);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        reportService.delete(id);
        return ResultBean.success();
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResultBean start(@RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = "D:/uploads/";
        // 文件重命名，防止重复
        String reName=System.currentTimeMillis() +"."+ suffixName;
        fileName = filePath + reName;
        // 文件对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            return ResultBean.success(reName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBean.error("上传失败");
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Integer id, HttpServletResponse response) {
        Report report=reportService.selectOne(id);

        if (report.getReportPath()==null || "".equals(report.getReportPath())){
            return;
        }
        // 文件地址，真实环境是存放在数据库中的
        File file = new File("D:/uploads/"+report.getReportPath());
        // 穿件输入对象
        try {
            FileInputStream fis = new FileInputStream(file);

            // 设置相关格式
            response.setContentType("application/force-download");
            // 设置下载后的文件名以及header
            response.addHeader("Content-disposition", "attachment;fileName=" + report.getReportPath());
            // 创建输出对象
            OutputStream os = response.getOutputStream();
            // 常规操作
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            //IOUtils.copy(fis,os);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
