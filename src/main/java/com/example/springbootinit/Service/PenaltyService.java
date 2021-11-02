package com.example.springbootinit.Service;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Utils.MyResponse;

import java.util.List;

public interface PenaltyService {

    /**
     * 新增行政处罚记录
     * @param penalty 行政处罚记录对象
     */
    Penalty insertPenalty(Penalty penalty);



    /**
     * 对导入后的结果记录可以删除
     * @param id 删除id
     */
    void deletePenalty(int id);

    /**
     * 对导入后的结果记录可以修改
     * @param penalty 行政处罚记录对象
     */
    Penalty updatePenalty(Penalty penalty);

    /**
     * 对行政处罚记录进行"批量"发布和撤销操作
     * @param penaltyIds 行政处罚记录对象
     */
    List<Penalty> changePenaltyStatus(String status, List<String> penaltyIds);

//    /**
//     * 对行政处罚记录进行"批量"撤销操作
//     * @param penaltyIds 行政处罚记录对象
//     */
//    List<Penalty> revokePenalty(List<String> penaltyIds);

    /**
     * 查询所有行政处罚记录
     */
    List<Penalty> findAllPenalty(Penalty penalty, int pageNumber, int pageSize);
}
