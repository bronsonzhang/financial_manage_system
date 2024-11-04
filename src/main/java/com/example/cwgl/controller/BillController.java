package com.example.cwgl.controller;


import com.example.cwgl.dao.BillMapper;
import com.example.cwgl.entity.Bill;
import com.example.cwgl.entity.Payway;
import com.example.cwgl.entity.UserInfo;
import com.example.cwgl.service.BillService;
import com.example.cwgl.utils.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Resource
    private BillService billService;

    @Resource
    private BillMapper mapper;

    /**
     * 适用于统计图
     * @param bill
     * @return
     */
    @RequestMapping("/getBillsToChart")
    public Result<Bill> findByWhereNoPage(Bill bill, HttpSession session){
        bill = getHouseBill(bill,session);
        return billService.findByWhereNoPage(bill);
    }

    @RequestMapping("/getBillsByWhere/{type}/{pageNo}/{pageSize}")
    public Result<Bill> getBillsByWhere(Bill bill,@PathVariable String type, @PathVariable int pageNo, @PathVariable int pageSize, HttpSession session){
        if("-1".equals(bill.getPayway())){
            bill.setPayway(null);
        }
        bill.setType(type);
        bill = getHouseBill(bill,session);
        System.out.println(bill);
        PageModel model = new PageModel<>(pageNo,bill);
        model.setPageSize(pageSize);
        return billService.findByWhere(model);
    }

    @RequestMapping("/getBillsByUserid/{userid}/{pageNo}/{pageSize}/{year}/{month}")
    public Result getBillsByUserid(@PathVariable Integer userid, @PathVariable int pageNo, @PathVariable int pageSize, @PathVariable int year, @PathVariable int month){
        Bill bill = new Bill();
        bill.setUserid(userid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bill.setStartTime(year+"-0"+month+"-01");
        try {
            Date date = sdf.parse(year+"-0"+(month+1)+"-01");
            date.setDate(date.getDate()-1);
            bill.setEndTime(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PageModel model = new PageModel<>(pageNo,bill);
        model.setPageSize(pageSize);
        Result result = billService.findByWhere(model);
        List<Map<String,String>> r = billService.getMonthlyInfo(model);
        Map<String,String> map = new HashMap<>();
        for (Map<String,String> m: r) {
            map.put(m.get("typeid"),String.format("%.2f",m.get("sum(money)")));
        }
        result.setData(map);
        return result;
    }

    private Bill getHouseBill(Bill bill, HttpSession session) {
        UserInfo currentUser = Config.getSessionUser(session);
        //当登录用户为家主时，查询默认查询全家账单情况
        //当登录用户为普通用户时，仅查询当前用户的账单
        if (currentUser.getRoleid() == 2){
            bill.setHouseid(currentUser.getHouseid());
        }else if (currentUser.getRoleid() == 3){
            bill.setUserid(currentUser.getId());
        }
        return bill;
    }

    @RequestMapping(value = "/addBill",method = RequestMethod.POST)
    public Result add(Bill bill, HttpSession session){
        if (Config.getSessionUser(session)!=null){
            bill.setUserid(Config.getSessionUser(session).getId());
        }
        Utils.log(bill.toString());
        try {
            int num = billService.add(bill);
            if(num>0){
                int billid = bill.getId();
                bill = new Bill();
                bill.setId(billid);
                return ResultUtil.success("记账成功！",billService.findByWhereNoPage(bill));
//                return ResultUtil.success("记账成功！",bill);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/updateBill")
    public Result update(Bill bill, HttpSession session){
        if (Config.getSessionUser(session)!=null){
            bill.setUserid(Config.getSessionUser(session).getId());
        }
        Utils.log(bill.toString());
        try {
            int num = billService.update(bill);
            if(num>0){
                return ResultUtil.success("修改成功！",null);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/delBill")
    public Result del(int id){
        try {
            int num = billService.del(id);
            if(num>0){
                return ResultUtil.success("删除成功！",null);
            }else {
                return ResultUtil.unSuccess();
            }
        }catch (Exception e){
            return ResultUtil.error(e);
        }
    }

    @RequestMapping("/getPayways")
    public Result<Payway> getAllPayways(){

        try {
            List<Payway> payways = billService.getAllPayways();
            if (payways!=null && payways.size()>0){
                return ResultUtil.success(payways);
            }else {
                return ResultUtil.unSuccess();
            }
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }


    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/getBillsExport/{type}")
    public void getBillsExport(Bill bill,@PathVariable String type, HttpSession session,HttpServletResponse response){
        if("-1".equals(bill.getPayway())){
            bill.setPayway(null);
        }
        bill.setType(type);
        bill = getHouseBill(bill,session);
        PageModel model = new PageModel<>(1,bill);
        List<Bill> billList = mapper.findByWhere(model);
        //Sheet名称
        String sheetName="";
        //Excel 标题
        String[] title;
        String name="";
        if (type.equals("2")){
            title= new String[]{"标题", "姓名", "金额(元)", "收入方式", "备注", "时间"};
            sheetName="收入表";
        }else {
            title= new String[]{"标题", "姓名", "金额(元)", "支付方式", "备注", "时间"};
            sheetName="支出表";
        }

        String fileName = sheetName+System.currentTimeMillis()+".xls";
        String[][] content=new String[billList.size()][title.length];
        for (int i = 0; i < billList.size(); i++) {
            Bill channels=billList.get(i);
            content[i][0] = channels.getTitle();
            content[i][1] = channels.getUsername();
            content[i][2] = String.valueOf(channels.getMoney());
            content[i][3] = String.valueOf(channels.getPayway());
            content[i][4] = channels.getRemark();
            content[i][5] = channels.getTime();
        }
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response){
        //Sheet名称
        String sheetName="模板表";
        //Excel 标题
        String[] title= new String[]{"标题", "金额", "备注", "支付方式(1.支付宝,2.微信,3.银联,4.现金,5.其他)"};;
        String name="模板表";
        String fileName = name+System.currentTimeMillis()+".xls";
        String[][] content=new String[0][title.length];
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //导入
    @PostMapping("importExcel/{type}")
    @ResponseBody
    public String importExcel(@PathVariable String type,@RequestParam("upload") MultipartFile file,HttpSession session,HttpServletResponse response){
        UserInfo userInfo = (UserInfo)session.getAttribute(Config.CURRENT_USERNAME);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Bill> channelsList=new ArrayList<>();
        try {
            InputStream stream = file.getInputStream();
            //获取Excel文件对象
            HSSFWorkbook wb = new HSSFWorkbook(stream);
            //获取文件的指定工作表 默认的第一个
            HSSFSheet sheet = wb.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
                HSSFRow row = sheet.getRow(i);//行
                int cellNum = row.getLastCellNum(); //每行的最后一个单元格位置
                Bill channels=new Bill();
                for (int j = 0; j < cellNum+1; j++) {
                    HSSFCell cell = row.getCell(j);
                    if (j==0){
                        cell.setCellType(CellType.STRING);
                        channels.setTitle(cell.getStringCellValue());
                    }else if (j==1){
                        cell.setCellType(CellType.STRING);
                        channels.setMoney(Float.parseFloat(cell.getStringCellValue()));
                    }else if (j==2){
                        cell.setCellType(CellType.STRING);
                        channels.setRemark(cell.getStringCellValue());
                    }else if (j==3){
                        cell.setCellType(CellType.STRING);
                        channels.setPaywayid(Integer.parseInt(cell.getStringCellValue()));
                    }
                }
                channelsList.add(channels);
            }
            //添加到数据库
            for (int i = 0; i < channelsList.size(); i++) {
                Bill channels=channelsList.get(i);
                channels.setUserid(userInfo.getId());
                channels.setTypeid(Integer.parseInt(type));
                channels.setTime(simpleDateFormat.format(new Date()));
                mapper.add(channels);
            }
            return "{\"code\":0,\"msg\":\"success\"}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"code\":1,\"msg\":\"error\"}";
    }

}
