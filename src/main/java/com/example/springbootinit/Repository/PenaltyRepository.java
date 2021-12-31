package com.example.springbootinit.Repository;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.VO.PunishmentDecisionVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Integer> ,JpaSpecificationExecutor<Penalty> {

    /*@Query(value =
            "Select type, frequency, round(frequency / total * 100, 2) as ratio, amount " +
            "From " +
            "(Select punishmentType as type, count(*) as frequency, sum(fine) as amount " +
            "From penalty " +
            "Where type = ?1 and year(date) = ?2 and month(date) = ?3 " +
            "Group by punishmentType) t1 " +
            "Inner join " +
            "(Select count(*) as total " +
            "From penalty " +
            "Where type = ?1 and year(date) = ?2 and month(date) = ?3) t2 ",
            nativeQuery = true)
    List<Object[]> getAnalysis(Integer type, String year, String month);*/

    List<Penalty> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Penalty> findAllByTypeAndDateBetween(Integer type, LocalDate startDate, LocalDate endDate);
}
