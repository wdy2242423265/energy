package com.qhit.reports;

import com.qhit.baseDevice.pojo.BaseDevice;
import com.qhit.baseDevice.service.IBaseDeviceService;
import com.qhit.baseDevtype.pojo.BaseDevtype;
import com.qhit.baseDevtype.service.IBaseDevtypeService;
import com.qhit.baseFlow.pojo.BaseFlow;
import com.qhit.baseFlow.service.IBaseFlowService;
import com.qhit.baseUser.pojo.BaseUser;
import com.qhit.produceReport.service.IProduceReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2019/4/16.
 */
@RestController
@RequestMapping("/jobAmount")
public class JobAmount {
    @Resource
    IBaseFlowService baseFlowService;
    @Resource
    IProduceReportService produceReportService;
    @Resource
    IBaseDevtypeService baseDevtypeService;
    @Resource
    IBaseDeviceService baseDeviceService;

    @RequestMapping("/flowAmount")
    public Object flowAmount(String year, HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseFlow> flows = baseFlowService.findByCompid(compid);
        String[] flowArr = new String[flows.size()+1];
        flowArr[0]="月份";
        for (int i=0; i<flows.size(); i++){
            flowArr[i+1]=flows.get(i).getFlowname();
        }
        List<Map> rows=produceReportService.selectFlowAmount(year,flows);
        map.put("columns",flowArr);
        map.put("rows",rows);
        return map;
    }
    @RequestMapping("/devTypeAmount")
    public Object devtypeAmount(String year){
        Map map = new HashMap();
        List<BaseDevtype> devtypeList =baseDevtypeService.findAll();
        String[] devTypeArr = new String[devtypeList.size()+1];
        devTypeArr[0]="月份";
        for (int i=0; i<devtypeList.size(); i++){
            devTypeArr[i+1]=devtypeList.get(i).getTypename();
        }
        List<Map> rows =produceReportService.selectDevTypeAmount(year,devtypeList);
        map.put("columns",devTypeArr);
        map.put("rows",rows);
        return map;
    }

    @RequestMapping("/devAmount")
    public Object devAmount(String year,HttpSession session){
        Map map = new HashMap();
        BaseUser baseUser = (BaseUser) session.getAttribute("baseUser");
        Integer compid = baseUser.getCompid();
        List<BaseDevice> devices = baseDeviceService.findByCompid(compid);
        String[] devArr = new String[devices.size()+1];
        devArr[0]="月份";
        for (int i=0; i<devices.size(); i++){
            devArr[i+1]=devices.get(i).getDevname();
        }
        List<Map> rows= produceReportService.selectDevAmount(year,devices);
        map.put("columns",devArr);
        map.put("rows",rows);
        return map;
    }

}
