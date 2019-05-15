package com.qhit.produceReport.controller; 

import com.qhit.baseFlow.pojo.BaseFlow;
import com.qhit.baseFlow.service.IBaseFlowService;
import com.qhit.energyConsume.pojo.EnergyConsume;
import com.qhit.energyConsume.service.IEnergyConsumeService;
import com.qhit.produceJob.pojo.ProduceJob;
import com.qhit.produceJob.service.IProduceJobService;
import com.qhit.produceReport.pojo.ProduceReport;
import com.qhit.produceReport.service.IProduceReportService; 
import org.springframework.web.bind.annotation.RequestMapping; 
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RestController;

/** 
* Created by GeneratorCode on 2019/04/11
*/ 

@RestController 
@RequestMapping("/produceReport") 
public class ProduceReportController { 

    @Resource 
    IProduceReportService produceReportService;
    @Resource
    IProduceJobService produceJobService;
    @Resource
    IBaseFlowService baseFlowService;
    @Resource
    IEnergyConsumeService energyConsumeService;

    @RequestMapping("/insert") 
    public void insert(ProduceReport produceReport) { 
        produceReportService.insert(produceReport); 
    } 

    @RequestMapping("/delete") 
    public void delete(Integer reportid) { 
        produceReportService.delete(reportid); 
    } 

    @RequestMapping("/update") 
    public void update(ProduceReport produceReport) { 
        produceReportService.update(produceReport); 
    } 

    @RequestMapping("/updateSelective") 
    public List<ProduceReport> updateSelective(ProduceReport produceReport) {
        produceReportService.updateSelective(produceReport);
        List<ProduceReport> list = produceReportService.findAll();
        return list;
    } 

    @RequestMapping("/load") 
    public ProduceReport load(Integer reportid) { 
        ProduceReport produceReport = produceReportService.findById(reportid); 
        return produceReport; 
    } 

    @RequestMapping("/list") 
    public List<ProduceReport> list()  { 
        List<ProduceReport> list = produceReportService.findAll(); 
        return list; 
    } 

    @RequestMapping("/search") 
    public List<ProduceReport> search(ProduceReport produceReport) { 
        List<ProduceReport> list = produceReportService.search(produceReport); 
        return list; 
    }

    @RequestMapping("/completeTask")
    public List<ProduceReport> completeTask(ProduceReport produceReport) throws ParseException {
        //produceReport:reportid,flowid,startjobtime,completetime
        boolean flag = produceReportService.completeTask(produceReport);
        List<ProduceReport> list = produceReportService.findAll();
        //插入作业信息表
        BaseFlow baseFlow = baseFlowService.findById(produceReport.getFlowid());
        ProduceJob produceJob = new ProduceJob();
        //斗轮机
        produceJob.setDevid(baseFlow.getDljid());
//        produceJob.setDevid(baseFlow.getZcjid());
//        produceJob.setDevid(baseFlow.getPdjid());
        produceJob.setStarttime(produceReport.getStartjobtime());
        produceJob.setCompletetime(produceReport.getCompletetime());
        //把字符串日期转换成long
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        //运行结束时间-运行开始时间=duration（运行时长） (毫秒)
        long duration =simpleDateFormat.parse(produceJob.getCompletetime()).getTime()-simpleDateFormat.parse(produceJob.getStarttime()).getTime();
        //把运行时长(毫秒)转换成分钟
        long Minute =(duration/1000)/60;
        double a = Math.floor(Minute/60)+(double)(Minute%60)/60;
        produceJob.setDuration(a);
        //装载量(吨) 作业量比例 斗:装:皮带 = 4:4:2
        double capacity =produceReport.getCapacity();
        //保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        produceJob.setAmount(Double.parseDouble(df.format(capacity*0.4)));
        produceJob.setReportid(produceReport.getReportid());
        boolean inserts = produceJobService.insert(produceJob);
        //装船机
        produceJob.setDevid(baseFlow.getZcjid());
        produceJob.setAmount(Double.parseDouble(df.format(capacity*0.4)));
        boolean inserts1 = produceJobService.insert(produceJob);
        //皮带机
        produceJob.setDevid(baseFlow.getPdjid());
        produceJob.setAmount(Double.parseDouble(df.format(capacity*0.2)));
        boolean inserts2 = produceJobService.insert(produceJob);

        //插入能耗信息表
        EnergyConsume energyConsume = new EnergyConsume();
        //斗轮机
        energyConsume.setDevid(baseFlow.getDljid());
//        produceJobService.insert()
//        energyConsume.setDevid(baseFlow.getZcjid());
//        energyConsume.setDevid(baseFlow.getPdjid());
        //生成100~300之间的随机数,用转换后double类型的作业量,根据电耗公式计算出电耗, set进double类型的Electric里
        //电耗：一吨作业量对应100~300度电 随机数100~300
        Random random = new Random();
        energyConsume.setElectric(Double.parseDouble(df.format((capacity*0.4)*random.nextInt(201)+100)));

        //水耗：一吨作业量对应1~10吨水 随机数1~10
        energyConsume.setWater(Double.parseDouble(df.format((capacity*0.4)*random.nextInt(10)+1)));

        //油耗：一吨作业量对应10~40L油 随机数10~40   随机数：均保留两位小数
        //生成10~40之间的随机数
        double randomNum =Math.random()*31+10;
        //随机数保留2位小数
        double DoubleRandomNum  =Double.parseDouble(df.format(randomNum));
        energyConsume.setOil(Double.parseDouble(df.format((capacity*0.4)*DoubleRandomNum)));

        energyConsume.setReportid(produceReport.getReportid());
        boolean insert = energyConsumeService.insert(energyConsume);
        //装船机
        energyConsume.setDevid(baseFlow.getZcjid());
        energyConsume.setElectric(Double.parseDouble(df.format((capacity*0.4)*random.nextInt(201)+100)));
        energyConsume.setWater(Double.parseDouble(df.format((capacity*0.4)*random.nextInt(10)+1)));
        energyConsume.setOil(Double.parseDouble(df.format((capacity*0.4)*DoubleRandomNum)));
        boolean insert1 = energyConsumeService.insert(energyConsume);
        //皮带机
        energyConsume.setDevid(baseFlow.getPdjid());
        energyConsume.setElectric(Double.parseDouble(df.format((capacity*0.2)*random.nextInt(201)+100)));
        energyConsume.setWater(Double.parseDouble(df.format((capacity*0.2)*random.nextInt(10)+1)));
        energyConsume.setOil(Double.parseDouble(df.format((capacity*0.2)*DoubleRandomNum)));
        boolean insert2 = energyConsumeService.insert(energyConsume);
        return list;
    }

} 
