package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Repository.PenaltyRepository;
import com.example.springbootinit.Service.PenaltyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PenaltyServiceImpl implements PenaltyService {
    @Resource
    PenaltyRepository penaltyRepository;

    @Override
    public Penalty insertPenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    @Override
    public void deletePenalty(int id) {
        penaltyRepository.deleteById(id);
    }

    @Override
    public Penalty updatePenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    @Override
    public List<Penalty> releasePenalty(String ids) {
        List<Penalty> penaltyList = new ArrayList<>();
        List idList = Arrays.asList(ids.split(","));
        idList.forEach(ID ->{
            Integer id = Integer.parseInt((String) ID);
            Penalty penalty = penaltyRepository.findById(id).orElse(null);
            penalty.setStatus(1);
            penaltyRepository.save(penalty);
            penaltyList.add(penalty);
        });
        return penaltyList;
    }

    @Override
    public Penalty findPenaltyById(int id) {
        return penaltyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Penalty> findAllPenalty() {
        return penaltyRepository.findAll();
    }
}
