package com.example.springbootinit.Service;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.VO.DataListVO;
import com.example.springbootinit.VO.PenaltyVO;

import java.util.List;

public interface PenaltyService {

    /**
     * 新增行政处罚记录
     * @param penalty 行政处罚记录对象
     */
    PenaltyVO insertPenalty(PenaltyVO penalty);


    /**
     * 批量新增行政处罚记录
     * @param penaltyList 行政处罚记录对象列表
     */
    DataListVO insertPenalties(List<PenaltyVO> penaltyList);


    /**
     * 删除行政处罚记录
     * @param id 删除id
     */
    void deletePenalty(int id);

    /**
     * 删除行政处罚记录
     * @param ids
     */
    void deletePenalties(List<String> ids);

    /**
     * 修改行政处罚记录
     * @param penaltyVO 行政处罚记录对象
     */
    PenaltyVO updatePenalty(PenaltyVO penaltyVO);


    /**
     * @param status
     * @param ids
     * @return
     */
    DataListVO changePenaltyStatus(String status, List<String> ids);

//    /**
//     * 对行政处罚记录进行"批量"撤销操作
//     * @param penaltyIds 行政处罚记录对象
//     */
//    List<Penalty> revokePenalty(List<String> penaltyIds);

    /**
     * 查询所有行政处罚记录
     */
    DataListVO findAllPenalty(PenaltyVO penaltyVO, int pageNumber, int pageSize, boolean isVague);
}
