package com.qhit.produceReport.service;

import java.util.List;
import java.util.Map;

import com.qhit.baseDevice.pojo.BaseDevice;
import com.qhit.baseDevtype.pojo.BaseDevtype;
import com.qhit.baseFlow.pojo.BaseFlow;
import com.qhit.produceReport.pojo.ProduceReport;
/**
* Created by GeneratorCode on 2019/04/11
*/
public interface IProduceReportService {

    boolean insert(Object object);

    boolean  update(Object object);

    boolean  updateSelective(Object object);

    boolean delete(Object id);

    List findAll();

    ProduceReport findById(Object id);

    List<ProduceReport> search(ProduceReport produceReport);

    boolean completeTask(ProduceReport produceReport);

    boolean insertProduceJob(ProduceReport produceReport);

    List<Map> selectFlowAmount(String year, List<BaseFlow> flows);

    List<Map> selectDevTypeAmount(String year, List<BaseDevtype> devtypeList);

    List<Map> selectDevAmount(String year, List<BaseDevice> devices);

    List<Map> selectFlowConsume(String year, List<BaseFlow> flows);

    List<Map> selectDevTypeConsume(String year, List<BaseDevtype> devTypeList);

    List<Map> selectDevConsume(String year, List<BaseDevice> devices);

    List<Map> selectElectricConsume(String year, List<BaseDevice> devices);

    List<Map> selectWaterConsume(String year, List<BaseDevice> devices);

    List<Map> selectOilConsume(String year, List<BaseDevice> devices);

    List<Map> selectJobModel(String year, BaseFlow baseFlow);

    List<Map> selectEnergyConsumeModel(String year, BaseFlow baseFlow);


//    boolean completeTask(Integer reportid,Integer flowid,String startjobtime,String completetime);
}