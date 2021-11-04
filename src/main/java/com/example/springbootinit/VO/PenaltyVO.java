package com.example.springbootinit.VO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ExcelTarget(value = "penalty")
public class PenaltyVO {

    private String id; //案例的主键

    @Size(max = 50, message = "处罚名称长度不能超过50")
    @Excel(name = "行政处罚名称", isImportField = "true")
    private String name; //行政处罚名称

    @Size(max = 50, message = "文号长度不能超过50")
    @Excel(name = "行政处罚决定文号", isImportField = "true")
    private String number; //行政处罚决定文号

    @Pattern(regexp = "^(0|1)$", message = "不存在的类型")
    @Excel(name = "处罚类型", replace = {"个人_1", "企业_0"}, isImportField = "true")
    private String type; //处罚类型('0':个人|'1':企业)

    @Size(max = 20, message = "当事人名称长度不能超过20")
    @Excel(name = "被罚当事人名称", isImportField = "true")
    private String partyName; //被罚当事人名称

    @Size(max = 20, message = "负责人名称长度不能超过20")
    @Excel(name = "主要负责人姓名")
    private String responsiblePersonName; //主要负责人姓名

    @Size(max = 20, message = "违法事实长度不能超过255")
    @Excel(name = "主要违法违规事实")
    private String facts; //主要违法违规事实

    @Excel(name = "行政处罚依据")
    @Size(max = 20, message = "处罚依据长度不能超过255")
    private String basis; //行政处罚依据

    @Excel(name = "行政处罚决定")
    @Size(max = 20, message = "处罚决定长度不能超过255")
    private String decision; //行政处罚决定

    @Excel(name = "行政处罚机关名称")
    @Size(max = 20, message = "机关名称长度不能超过20")
    private String organName; //行政处罚机关名称

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "行政处罚日期")
    private String date;  //行政处罚日期

    @Pattern(regexp = "^(0|1)$", message = "不存在的类型")
    @Excel(name = "发布类型")
    private String status; //发布类型('0':未发布|'1':已发布)
}
