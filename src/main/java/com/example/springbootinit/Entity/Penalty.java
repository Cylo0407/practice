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
    private Integer Id; //案例的主键

    @Column(name = "penalty_desc", nullable = false)
    private String penaltyDesc;

    @Column(name = "penalty_id", nullable = false)
    private String penaltyId;

    @Column(name = "penalty_type", nullable = false, length = 32)
    private String penaltyType;

    @Column(name = "punished_person_name", nullable = false)
    private String punishedPersonName;

    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @Column(name = "punishment_fact", nullable = false)
    private String punishmentFact;

    @Column(name = "punishment_basis", nullable = false)
    private String punishmentBasis;

    @Column(name = "punishment_descision", nullable = false)
    private String punishmentDecision;

    @Column(name = "punishment_organ_name", nullable = false)
    private String punishmentOrganName;

    @Column(name = "penalty_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String penaltyDate;

    @Column(name = "penalty_status", nullable = false, length = 1)
    private Integer penaltyStatus;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPenaltyDesc() {
        return penaltyDesc;
    }

    public void setPenaltyDesc(String penaltyDesc) {
        this.penaltyDesc = penaltyDesc;
    }

    public String getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(String penaltyId) {
        this.penaltyId = penaltyId;
    }

    public String getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(String penaltyType) {
        this.penaltyType = penaltyType;
    }

    public String getPunishedPersonName() {
        return punishedPersonName;
    }

    public void setPunishedPersonName(String punishedPersonName) {
        this.punishedPersonName = punishedPersonName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPunishmentFact() {
        return punishmentFact;
    }

    public void setPunishmentFact(String punishmentFact) {
        this.punishmentFact = punishmentFact;
    }

    public String getPunishmentBasis() {
        return punishmentBasis;
    }

    public void setPunishmentBasis(String punishmentBasis) {
        this.punishmentBasis = punishmentBasis;
    }

    public String getPunishmentDecision() {
        return punishmentDecision;
    }

    public void setPunishmentDecision(String punishmentDecision) {
        this.punishmentDecision = punishmentDecision;
    }

    public String getPunishmentOrganName() {
        return punishmentOrganName;
    }

    public void setPunishmentOrganName(String punishmentOrganName) {
        this.punishmentOrganName = punishmentOrganName;
    }

    public String getPenaltyDate() {
        return penaltyDate;
    }

    public void setPenaltyDate(String penaltyDate) {
        this.penaltyDate = penaltyDate;
    }

    public Integer getPenaltyStatus() {
        return penaltyStatus;
    }

    public void setPenaltyStatus(Integer penaltyStatus) {
        this.penaltyStatus = penaltyStatus;
    }
}
