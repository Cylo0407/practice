package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Repository.PenaltyRepository;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.VPMapper.PenaltyMapper;
import com.example.springbootinit.VO.DataListVO;
import com.example.springbootinit.VO.PenaltyVO;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PenaltyServiceImpl implements PenaltyService {

    @Resource
    PenaltyRepository penaltyRepository;

    @Override
    public PenaltyVO insertPenalty(PenaltyVO penaltyVO) {
        try {
            return PenaltyMapper.INSTANCE.p2v(penaltyRepository.save(PenaltyMapper.INSTANCE.v2p(penaltyVO)));
        } catch (DataAccessException e) {
            throw new RuntimeException("创建文号为" + penaltyVO.getNumber() + "的记录失败");
        }
    }

    @Override
    public DataListVO insertPenalties(List<PenaltyVO> penaltyList) {
        DataListVO<PenaltyVO> resDataList = new DataListVO();
        resDataList.setDataList(penaltyList.stream().map(this::insertPenalty).collect(Collectors.toList()));
        return resDataList;
    }

    @Override
    public void deletePenalty(int id) {
        try {
            penaltyRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("删除序号为" + String.valueOf(id) + "的记录失败");
        }
    }

    @Override
    public void deletePenalties(List<String> ids) {
        ids.forEach(id -> deletePenalty(Integer.parseInt(id)));
    }

    @Override
    public PenaltyVO updatePenalty(PenaltyVO penaltyVO) {
        try {
            return PenaltyMapper.INSTANCE.p2v(penaltyRepository.save(PenaltyMapper.INSTANCE.v2p(penaltyVO)));
        } catch (DataAccessException e) {
            throw new RuntimeException("更新序号为" + penaltyVO.getId() + "的记录失败");
        }
    }

    @Override
    public DataListVO<PenaltyVO> changePenaltyStatus(String status, List<String> ids) {
        List<PenaltyVO> penaltyList = new ArrayList<>();
        ids.forEach(id -> {
            Penalty penalty = penaltyRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new RuntimeException("不存在序号为" + id + "的记录"));
            penalty.setStatus(Integer.parseInt(status));
            penaltyList.add(updatePenalty(PenaltyMapper.INSTANCE.p2v(penalty)));
        });
        DataListVO dataList = new DataListVO();
        dataList.setDataList(penaltyList);
        return dataList;
    }

    @Override
    public DataListVO<PenaltyVO> findAllPenalty(PenaltyVO penaltyVO, int pageNumber, int pageSize, boolean isVague) {
        Sort.Direction sort = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort, "id");
        Penalty penalty = PenaltyMapper.INSTANCE.v2p(penaltyVO);
        Specification<Penalty> query = (root, query1, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Class penaltyClass = (Class) penalty.getClass();
            Field[] fs = penaltyClass.getDeclaredFields();
            try {
                for (Field f : fs) {
                    f.setAccessible(true);
                    String key = f.getName();
                    Object val = f.get(penalty);
                    if (val != null) {
                        if (isVague && f.getType() == String.class)
                            predicates.add(criteriaBuilder.like(root.get(key), "%" + val + "%"));
                        else
                            predicates.add(criteriaBuilder.equal(root.get(key), val));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Penalty> penaltyPage = penaltyRepository.findAll(query, pageable);
        DataListVO dataListVO = new DataListVO();
        dataListVO.setDataList(PenaltyMapper.INSTANCE.pList2vList(penaltyPage.getContent()));
        dataListVO.setListRelatedData(penaltyPage.getTotalElements());
        return dataListVO;
    }

   /*     @Override
    public List<Penalty> revokePenalty(List<String> ids) {
        List<Penalty> penaltyList = new ArrayList<>();
        ids.forEach(ID -> {
            Integer id = Integer.parseInt((String) ID);
            Penalty penalty = penaltyRepository.findById(id).orElse(null);
            if(penalty != null) {
                penalty.setStatus("0");
                penaltyRepository.save(penalty);
                penaltyList.add(penalty);
            }
        });
        return penaltyList;
    }*/

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
