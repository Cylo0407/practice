package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Repository.PenaltyRepository;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.MyResponse;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.HTMLDocument;
import java.lang.reflect.Field;
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
    public List<Penalty> insertPenalties(List<Penalty> penalties) {
        List<Penalty> success = new ArrayList<>();
        Penalty newPenalty;
        try {
            for (Penalty penalty : penalties){
                newPenalty = insertPenalty(penalty);
                success.add(newPenalty);
            }
        } catch (Exception e){
            return success;
        }
        return success;
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
    public List<Penalty> findAllPenalty(Penalty penalty, int pageNumber, int pageSize) {
        Sort.Direction sort = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort, "id" ) ;
        Specification<Penalty> query = new Specification<Penalty>() {
            @SneakyThrows
            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Class penaltyClass = (Class) penalty.getClass();
                Field[] fs = penaltyClass.getDeclaredFields();
                for(Field f : fs) {
                    f.setAccessible(true);
                    String key = f.getName();
                    Object val = f.get(penalty);
                    if(val != null)
                        predicates.add(criteriaBuilder.equal(root.get(key), val));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return penaltyRepository.findAll(query, pageable).getContent();
    }
    /*
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
    }*/
}
