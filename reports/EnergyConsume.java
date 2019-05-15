package com.qhit.reports;

import com.qhit.baseDevice.pojo.BaseDevice;
import com.qhit.baseDevice.service.IBaseDeviceService;
import com.qhit.baseDevtype.pojo.BaseDevtype;
import com.qhit.baseDevtype.service.IBaseDevtypeService;
import com.qhit.baseFlow.pojo.BaseFlow;
import com.qhit.baseFlow.service.IBaseFlowService;
import com.qhit.baseUser.pojo.BaseUser;
import com.qhit.produceReport.pojo.ProduceReport;
import com.qhit.produceReport.service.IProduceReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2019/4/22.
 */
@RestController
@RequestMapping("/energyConsume")
public class EnergyConsume {
    @Resource
    IBaseFlowService baseFlowService;
    @Resource
    IProduceReportService produceReportService;
    @Resource
    IBaseDevtypeService baseDevtypeService;
    @Resource
    IBaseDeviceService baseDeviceService;
    @RequestMapping("/flowConsume")
    public Object flowConsume(String year, HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseFlow> flows = baseFlowService.findByCompid(compid);
        String[] flowArr = new String[flows.size()+1];
        flowArr[0]="月份";
        for (int i=0; i<flows.size(); i++){
            flowArr[i+1]=flows.get(i).getFlowname();
        }
        List<Map> rows = produceReportService.selectFlowConsume(year,flows);
        map.put("columns",flowArr);
        map.put("rows",rows);
        return map;
    }
    @RequestMapping("/devTypeConsume")
    public Object devTypeConsume(String year){
        Map map = new HashMap();
        List<BaseDevtype> devTypeList = baseDevtypeService.findAll();
        String[] devTypeArr =new String[devTypeList.size()+1];
        devTypeArr[0]="月份";
        for (int i=0; i<devTypeList.size(); i++){
            devTypeArr[i+1]=devTypeList.get(i).getTypename();
        }
        List<Map> rows =produceReportService.selectDevTypeConsume(year,devTypeList);
        map.put("columns",devTypeArr);
        map.put("rows",rows);
        return map;
    }
    @RequestMapping("/devConsume")
    public Object devConsume(String year,HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseDevice> devices = baseDeviceService.findByCompid(compid);
        String[] deviceArr = new String[devices.size()+1];
        deviceArr[0] ="月份";
        for (int i=0; i<devices.size(); i++){
            deviceArr[i+1]=devices.get(i).getDevname();
        }
        List<Map> rows = produceReportService.selectDevConsume(year,devices);
        map.put("columns",deviceArr);
        map.put("rows",rows);
        return map;
    }
    @RequestMapping("/electricConsume")
    public Object electricConsume(String year,HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseDevice> devices = baseDeviceService.findByCompid(compid);

        String[] devArr = new String[devices.size()+1];
        devArr[0]="月份";
        for (int i = 0; i <devices.size() ; i++) {
            devArr[i+1]=devices.get(i).getDevname();
        }

        List<Map> rows = produceReportService.selectElectricConsume(year,devices);
        map.put("columns",devArr);
        map.put("rows",rows);
        return  map;
    }
    @RequestMapping("/waterConsume")
    public Object waterConsume(String year,HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseDevice> devices = baseDeviceService.findByCompid(compid);

        String[] devArr = new String[devices.size()+1];
        devArr[0]="月份";
        for (int i = 0; i <devices.size() ; i++) {
            devArr[i+1]=devices.get(i).getDevname();
        }

        List<Map> rows = produceReportService.selectWaterConsume(year,devices);
        map.put("columns",devArr);
        map.put("rows",rows);
        return  map;
    }
    @RequestMapping("/oilConsume")
    public Object oilConsume(String year,HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseDevice> devices = baseDeviceService.findByCompid(compid);

        String[] devArr = new String[devices.size()+1];
        devArr[0]="月份";
        for (int i = 0; i <devices.size() ; i++) {
            devArr[i+1]=devices.get(i).getDevname();
        }

        List<Map> rows = produceReportService.selectOilConsume(year,devices);
        map.put("columns",devArr);
        map.put("rows",rows);
        return  map;
    }



}
