package com.example.springbootinit.VO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.time.LocalDate;

@Data
@ExcelTarget(value = "penalty")
public class PenaltyVO {

    private String id; //案例的主键
    
    @Excel(name = "行政处罚名称", isImportField = "true")
    private String name; //行政处罚名称

    @Excel(name = "行政处罚决定文号", isImportField = "true")
    private String number; //行政处罚决定文号

    @Excel(name = "处罚类型")
    private String type; //处罚类型('personal':个人|'organization':企业)

    @Excel(name = "被罚当事人名称")
    private String partyName; //被罚当事人名称

    @Excel(name = "主要负责人姓名")
    private String responsiblePersonName; //主要负责人姓名

    @Excel(name = "主要违法违规事实")
    private String facts; //主要违法违规事实

    @Excel(name = "行政处罚依据")
    private String basis; //行政处罚依据

    @Excel(name = "行政处罚决定")
    private String decision; //行政处罚决定

    @Excel(name = "行政处罚机关名称")
    private String organName; //行政处罚机关名称

    @Excel(name = "行政处罚日期")
    private String date;  //行政处罚日期

    @Excel(name = "发布类型")
    private String status; //发布类型(0:未发布|1:已发布)
}
