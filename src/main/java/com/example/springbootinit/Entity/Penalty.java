package com.example.springbootinit.Entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "penalty")
@Data
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Excel(name = "序号")
    private Integer id; //案例的主键

    @Excel(orderNum = "0", name = "行政处罚名称")
    @Column(name = "name", nullable = false)
    private String name; //行政处罚名称

    @Excel(orderNum = "1", name = "行政处罚决定文号")
    @Column(name = "number", nullable = false)
    private String number; //行政处罚决定文号

    @Excel(orderNum = "2", name = "处罚类型")
    @Column(name = "type", nullable = false)
    private String type; //处罚类型('personal':个人|'organization':企业)

    @Excel(orderNum = "3", name = "被罚当事人名称")
    @Column(name = "partyName", nullable = false)
    private String partyName; //被罚当事人名称

    @Excel(orderNum = "4", name = "主要负责人姓名")
    @Column(name = "responsiblePersonName")
    private String responsiblePersonName; //主要负责人姓名

    @Excel(orderNum = "5", name = "主要违法违规事实")
    @Column(name = "facts")
    private String facts; //主要违法违规事实

    @Excel(orderNum = "6", name = "行政处罚依据")
    @Column(name = "basis")
    private String basis; //行政处罚依据

    @Excel(orderNum = "7", name = "行政处罚决定")
    @Column(name = "decision")
    private String decision; //行政处罚决定

    @Excel(orderNum = "8", name = "行政处罚机关名称")
    @Column(name = "organName")
    private String organName; //行政处罚机关名称

    @Excel(orderNum = "9", name = "行政处罚日期")
    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date;  //行政处罚日期

    @Excel(orderNum = "10", name = "发布类型")
    @Column(name = "status", nullable = false)
    private String status = "0"; //发布类型('0':未发布|'1':已发布)

//
//    @Override
//    public String toString() {
//        return "Penalty{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", number='" + number + '\'' +
//                ", type=" + type +
//                ", partyName='" + partyName + '\'' +
//                ", responsiblePersonName='" + responsiblePersonName + '\'' +
//                ", facts='" + facts + '\'' +
//                ", basis='" + basis + '\'' +
//                ", decision='" + decision + '\'' +
//                ", organName='" + organName + '\'' +
//                ", date='" + date + '\'' +
//                ", status=" + status +
//                '}';
//    }
}
