package com.example.springbootinit.Service.Impl;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Exception.BussinessException;
import com.example.springbootinit.Repository.PenaltyRepository;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.VPMapper.PenaltyMapper;
import com.example.springbootinit.VO.*;
import com.example.springbootinit.VO.PunishmentDecisionVO;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            throw new BussinessException("创建文号为" + penaltyVO.getNumber() + "的记录失败");
        }
    }

    @Override
    public DataListVO insertPenalties(List<PenaltyVO> penaltyList) {
        DataListVO<PenaltyVO> resDataList = new DataListVO<>();
        resDataList.setDataList(penaltyList.stream().map(this::insertPenalty).collect(Collectors.toList()));
        return resDataList;
    }

    @Override
    public void deletePenalty(int id) {
        try {
            penaltyRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new BussinessException("删除序号为" + id + "的记录失败");
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
            throw new BussinessException("更新序号为" + penaltyVO.getId() + "的记录失败");
        }
    }

    @Override
    public DataListVO<PenaltyVO> changePenaltyStatus(String status, List<String> ids) {
        List<PenaltyVO> penaltyList = new ArrayList<>();
        ids.forEach(id -> {
            Penalty penalty = penaltyRepository.findById(Integer.parseInt(id))
                    .orElseThrow(() -> new BussinessException("不存在序号为" + id + "的记录"));
            penalty.setStatus(Integer.parseInt(status));
            penaltyList.add(updatePenalty(PenaltyMapper.INSTANCE.p2v(penalty)));
        });
        DataListVO<PenaltyVO> dataList = new DataListVO<>();
        dataList.setDataList(penaltyList);
        return dataList;
    }

    @Override
    public DataListVO<PenaltyVO> findAllPenalty(PenaltyVO penaltyVO, int pageNumber, int pageSize, boolean isVague) {
        try {
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
            DataListVO<PenaltyVO> dataListVO = new DataListVO<>();
            dataListVO.setDataList(PenaltyMapper.INSTANCE.pList2vList(penaltyPage.getContent()));
            dataListVO.setListRelatedData(penaltyPage.getTotalElements());
            return dataListVO;
        } catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }

    @Override
    public DataListVO<PunishmentDecisionVO> getAnalysis(String type, String year, String month) {
        try {
            List<Penalty> penaltyMatch = penaltyRepository.findAllByTypeAndDate(Integer.valueOf(type), year, month);
            int countAll = penaltyMatch.size();
            //数据根据处罚类型分组
            Map<String, List<Penalty>> penaltyGroupByPunishmentType = penaltyMatch.stream().collect(Collectors.groupingBy(Penalty::getPunishmentType));

            List<PunishmentDecisionVO> result = new ArrayList<>();
            penaltyGroupByPunishmentType.forEach((key, value) -> {
                PunishmentDecisionVO p = new PunishmentDecisionVO();
                p.setType(key);
                p.setFrequency(String.valueOf(value.size()));
                p.setRatio(String.format("%.2f", value.size() / (double)countAll));
                p.setAmount(String.format("%.2f", getAmount(value)));
                result.add(p);
            });

            //按频次降序排序
            Collections.sort(result, Comparator.comparingInt((PunishmentDecisionVO p)-> Integer.parseInt(p.getFrequency())).reversed());

            DataListVO<PunishmentDecisionVO> dataListVO = new DataListVO<>();
            dataListVO.setDataList(result);
            return dataListVO;
        }catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }

    @Override
    public DataListVO getPenaltyOrderByFine(String year, String month) {
        try {
            DataListVO dataListVO = new DataListVO();
            dataListVO.setDataList(penaltyRepository.findAllOrderByFine(year, month));
            return dataListVO;
        }catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }


    @Override
    public DataListVO getPenaltyDistribution(String year, String month) {
        try {
            List<Penalty> penaltyMatch = penaltyRepository.findAllByDate(year, month);
            int countAll = penaltyMatch.size();
            double amountAll = getAmount(penaltyMatch);
            //数据根据省份分组
            Map<String, List<Penalty>> penaltyGroupByProvince = penaltyMatch.stream().collect(Collectors.groupingBy(Penalty::getProvince));

            List<ProvinceDetailVO> result = new ArrayList<>();
            penaltyGroupByProvince.forEach((key, value) -> {
                double amount = getAmount(value);
                //数据根据类型分组
                Map<Integer, List<Penalty>> penaltyGroupByType = value.stream().collect(Collectors.groupingBy(Penalty::getType));
                List<Penalty> organPenaltyList = penaltyGroupByType.getOrDefault(1, new ArrayList<>());
                List<Penalty> personalPenaltyList = penaltyGroupByType.getOrDefault(0, new ArrayList<>());
                double organAmount = getAmount(organPenaltyList);
                double personalAmount = getAmount(personalPenaltyList);

                ProvinceDetailVO p = new ProvinceDetailVO();
                p.setProvince(key);
                p.setCount(String.valueOf(value.size()));
                p.setCountRatio(String.format("%.2f", value.size() / (double) countAll));
                p.setAmount(String.format("%.2f", amount));
                p.setAmountRatio(String.format("%.2f", amount / amountAll));
                p.setAmountOrganization(String.format("%.2f", organAmount));
                p.setAmountPersonal(String.format("%.2f", personalAmount));
                p.setCountOrganization(String.valueOf(organPenaltyList.size()));
                p.setCountPersonal(String.valueOf(personalPenaltyList.size()));

                result.add(p);
            });
            //按订单数降序排序
            Collections.sort(result, Comparator.comparingInt((ProvinceDetailVO p) -> Integer.parseInt(p.getCount())).reversed());

            DataListVO<ProvinceDetailVO> dataListVO = new DataListVO<>();
            dataListVO.setDataList(result);
            return dataListVO;
        }catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }

    @Override
    public SummaryVO getSummary(String year, String month) {
        SummaryVO summaryVO = new SummaryVO();
        //现在的年月
        List<Penalty> presentList = penaltyRepository.findAllByDate(year, month);
        summaryVO.setTotal("" + presentList.size()); //罚单合计
        double amountOfSummary = getAmount(presentList);
        summaryVO.setAmount("" + amountOfSummary); //累计罚没金额

        int countOrganization = 0, countPersonal = 0;
        Map<String, Integer> map = getMapOfSummary(presentList);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) countPersonal++;
            else countOrganization++;
        }
        summaryVO.setCountOrganization("" + countOrganization); //处罚机构数量
        summaryVO.setCountPersonal("" + countPersonal); //处罚人员数量

        //去年同月
        String pastyear = String.valueOf(Integer.parseInt(year) - 1);
        List<Penalty> lastList = penaltyRepository.findAllByDate(pastyear, month);
        summaryVO.setLastTotal("" + lastList.size()); //去年罚单合计
        summaryVO.setLastAmount("" + getAmount(lastList)); //去年累计罚没金额

        int lastCountOrganization = 0, lastCountPersonal = 0;
        Map<String, Integer> pastMap = getMapOfSummary(lastList);
        for (Map.Entry<String, Integer> entry : pastMap.entrySet()) {
            if (entry.getValue() == 0) lastCountPersonal++;
            else lastCountOrganization++;
        }
        summaryVO.setLastCountOrganization("" + lastCountOrganization); //去年处罚机构数量
        summaryVO.setLastCountPersonal("" + lastCountPersonal); //去年处罚人员数量

        //分行相关
        List<Penalty> branchList = getBranchToalOfSummary(presentList, 0);
        summaryVO.setBranchTotal("" + branchList.size()); //分行罚单合计
        summaryVO.setBranchTotalRatio(String.format("%.2f", (double) branchList.size() / presentList.size())); //分行罚单百分比
        summaryVO.setBranchAmount("" + getAmount(branchList)); //分行累计罚没金额
        summaryVO.setBranchAmountRatio(String.format("%.2f", getAmount(branchList) / amountOfSummary)); //分行罚没金额百分比

        //中心支行相关
        List<Penalty> centerBranchList = getBranchToalOfSummary(presentList, 1);
        summaryVO.setCenterBranchTotal("" + centerBranchList.size()); //中心支行罚单合计
        summaryVO.setCenterBranchTotalRatio(String.format("%.2f", (double) centerBranchList.size() / presentList.size())); //中心支行罚单百分比
        summaryVO.setCenterBranchAmount("" + getAmount(centerBranchList)); //中心支行累计罚没金额
        summaryVO.setCenterBranchAmountRatio(String.format("%.2f", getAmount(centerBranchList) / amountOfSummary)); //中心支行罚没金额百分比

        return summaryVO;
    }

    private Double getAmount(List<Penalty> penaltyList) {
        return penaltyList.stream().mapToDouble(Penalty::getFine).sum();
    }

    public Map<String, Integer> getMapOfSummary(List<Penalty> penaltyList) {
        Map<String, Integer> map = new HashMap<>();
        for (Penalty penalty : penaltyList) {
            if (!map.containsKey(penalty.getName())) map.put(penalty.getName(), penalty.getType());
        }
        return map;
    }

    public List<Penalty> getBranchToalOfSummary(List<Penalty> penaltyList, int flag) { //flag 为0是分行；为1是中心支行
        List<Penalty> branchList = new ArrayList<>();
        List<Penalty> centerBranchList = new ArrayList<>();

        for (Penalty penalty : penaltyList) {
            String organizationName = penalty.getOrganName();
            if (organizationName.endsWith("分行")) branchList.add(penalty);
            else if (organizationName.endsWith("中心支行")) centerBranchList.add(penalty);
        }

        if (flag == 0) return branchList;
        else return centerBranchList;
    }

    @Override
    public List<OrganDetailVO> getOrganListOrderByCount(String year, String month){
        try {
            List<OrganDetailVO> organDetailVOList = penaltyRepository.findAllByCountAndDate(year, month);
            return organDetailVOList;
        }catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }

    @Override
    public List<OrganDetailVO> getOrganListOrderByFine(String year, String month){
        try {
            List<OrganDetailVO> organDetailVOList = penaltyRepository.findAllByFineAndDate(year, month);
            return organDetailVOList;
        }catch (DataAccessException e) {
            throw new BussinessException("查询出错");
        }
    }
}
