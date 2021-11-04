package com.example.springbootinit.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; //案例的主键

    @Column(name = "name", nullable = false)
    private String name; //行政处罚名称

    @Column(name = "number", nullable = false)
    private String number; //行政处罚决定文号

    @Column(name = "type", nullable = false)
    private Integer type; //处罚类型(0:个人|1:企业)

    @Column(name = "partyName", nullable = false)
    private String partyName; //被罚当事人名称

    @Column(name = "responsiblePersonName")
    private String responsiblePersonName; //主要负责人姓名

    @Column(name = "facts")
    private String facts; //主要违法违规事实

    @Column(name = "basis")
    private String basis; //行政处罚依据

    @Column(name = "decision")
    private String decision; //行政处罚决定

    @Column(name = "organName")
    private String organName; //行政处罚机关名称

    @Column(name = "date")
    private LocalDate date;  //行政处罚日期

    @Column(name = "status", nullable = false)
    private Integer status; //发布类型(0:未发布|1:已发布)

    @PrePersist
    private void onCreate() {
        if(status == null) status = 0;
    }

}
