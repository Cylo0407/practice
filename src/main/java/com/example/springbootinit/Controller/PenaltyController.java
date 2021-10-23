package com.example.springbootinit.Controller;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Entity.User;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    private static final String INSERT_FAILED = "添加失败!";
    private static final String UPDATE_FAILED = "更新失败!";
    private static final String FIND_FAILED = "查询出错!";

    @Resource
    PenaltyService penaltyService;

    /**
     * 新增处罚记录
     */
    @PostMapping("/createPunishment")
    public MyResponse addPenalty(@RequestBody Penalty penalty){
        Penalty newPenalty;
        try {
            newPenalty = penaltyService.insertPenalty(penalty);
        } catch (Exception e){
            e.printStackTrace();
            return MyResponse.buildFailure(INSERT_FAILED);
        }
        return MyResponse.buildSuccess(newPenalty);
    }

    /**
     * 删除处罚记录
     */
    @PostMapping("/deletePunishment")
    public MyResponse deletePenalty(@RequestBody String ids){
//        penaltyService.deletePenalty(id);
        return null;
    }

    /**
     * 修改处罚记录
     */
    @PostMapping("/editPunishment")
    public MyResponse updatePenalty(@RequestBody Penalty penalty){
        Penalty newPenalty;
        try {
            newPenalty = penaltyService.updatePenalty(penalty);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponse.buildFailure(UPDATE_FAILED);
        }
        return MyResponse.buildSuccess(newPenalty);
    }

    /**
     * 批量发布处罚记录
     */
    @PostMapping("/releasePunishment")
//    public List<Penalty> releasePenalty(@RequestBody String ids){
//        return penaltyService.releasePenalty(ids);
//    }
    public MyResponse releasePenalty(@RequestBody String ids){
        return null;
    }

    /**
     * 全查处罚记录
     * 不包含输入，则默认 pageNumber = 1; pageSize = 20;
     */
    @GetMapping("/getPunishmentList/{pageNumber}/{pageSize}")
    public MyResponse findAll(@PathVariable(value = "pageNumber", required = false) Integer pageNumber, @PathVariable(value = "pageSize", required = false) Integer pageSize){
        if (pageNumber == null) pageNumber = 1;
        if (pageSize == null) pageSize = 20;
        List<Penalty> penaltyList =  penaltyService.findAllPenalty(pageNumber, pageSize);
        if (penaltyList==null) {
            return MyResponse.buildFailure(FIND_FAILED);
        } else {
            return MyResponse.buildSuccess(penaltyList);
        }
    }
}
