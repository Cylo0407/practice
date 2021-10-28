package com.example.springbootinit.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "penalty")
@Data
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String date;  //行政处罚日期

    @Column(name = "status", nullable = false)
    private Integer status = 0; //处罚类型(0:未发布|1:已发布)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }

    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Penalty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", partyName='" + partyName + '\'' +
                ", responsiblePersonName='" + responsiblePersonName + '\'' +
                ", facts='" + facts + '\'' +
                ", basis='" + basis + '\'' +
                ", decision='" + decision + '\'' +
                ", organName='" + organName + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}
