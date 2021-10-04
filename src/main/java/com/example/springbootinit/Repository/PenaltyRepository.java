package com.example.springbootinit.Repository;

import com.example.springbootinit.Entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Integer> {

}
