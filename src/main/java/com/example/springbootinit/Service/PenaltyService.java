package com.example.springbootinit.Service;

import com.example.springbootinit.Entity.Penalty;

import java.util.List;

public interface PenaltyService {
    /**
     * 对行政处罚进行导入
     * @param penalty 行政处罚记录对象
     */

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
     * 对行政处罚记录进行发布操作
     * @param penalty 行政处罚记录对象
     */

    /**
     * 通过id查询行政处罚记录
     * @param id 查询id
     */
    Penalty findPenaltyById(int id);

    /**
     * 查询所有行政处罚记录
     */
    List<Penalty> findAllPenalty();
}
