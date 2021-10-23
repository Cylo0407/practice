package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Repository.PenaltyRepository;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.MyResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PenaltyServiceImpl implements PenaltyService {
    private static final String DELETE_FAILED = "删除失败!";

    @Resource
    PenaltyRepository penaltyRepository;

    @Override
    public Penalty insertPenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    @Override
    public void deletePenalty(int id) {
//        List idList = Arrays.asList(ids.split(","));
//        for (int i = 0; i < idList.size(); i++) {
//            Integer id = Integer.parseInt((String) idList.get(i));
//            try {
//                penaltyRepository.deleteById(id);
//                return MyResponse.buildSuccess();
//            } catch (Exception e) {
//                return MyResponse.buildFailure(DELETE_FAILED, id);
//            }
//        }
    }

    @Override
    public Penalty updatePenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    @Override
    public List<Penalty> releasePenalty(String ids) {
        List<Penalty> penaltyList = new ArrayList<>();
        List idList = Arrays.asList(ids.split(","));
        idList.forEach(ID -> {
            Integer id = Integer.parseInt((String) ID);
            Penalty penalty = penaltyRepository.findById(id).orElse(null);
            penalty.setStatus("1");
            penaltyRepository.save(penalty);
            penaltyList.add(penalty);
        });
        return penaltyList;
    }

    @Override
    public List<Penalty> revokePenalty(String ids) {
        List<Penalty> penaltyList = new ArrayList<>();
        List idList = Arrays.asList(ids.split(","));
        idList.forEach(ID -> {
            Integer id = Integer.parseInt((String) ID);
            Penalty penalty = penaltyRepository.findById(id).orElse(null);
            penalty.setStatus("0");
            penaltyRepository.save(penalty);
            penaltyList.add(penalty);
        });
        return penaltyList;
    }

    @Override
    public List<Penalty> findAllPenalty(int pageNumber, int pageSize) {
        List<Penalty> penaltyList = penaltyRepository.findAll();
        int count = penaltyList.size();

        int startCurrentPage = (pageNumber - 1) * pageSize; //开启的数据
        int endCurrentPage = pageNumber * pageSize; //结束的数据

        // pageNumber小于0 或 大于总页数 报错
        int totalPage = count / pageSize;                   //总页数
        if (pageNumber > totalPage || pageNumber <= 0) {
            return null;
        } else {
            return penaltyList.subList(startCurrentPage, endCurrentPage);
        }
    }
}
