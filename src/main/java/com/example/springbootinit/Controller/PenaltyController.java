package com.example.springbootinit.Controller;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Entity.User;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.DataHandle;
import com.example.springbootinit.Utils.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    private static final String EMPTY_FILE = "文件不能为空!";
    private static final String PARSE_FAILED = "解析错误!";
    private static final String INSERT_FAILED = "添加出错!";
    private static final String UPDATE_FAILED = "更新失败!";
    private static final String FIND_FAILED = "查询出错!";
    private static final String DELETE_FAILED = "删除出错!";

    @Resource
    PenaltyService penaltyService;

    /**
     * 导入excel
     */
    @PostMapping("/importXls")
    public MyResponse importXls(@RequestParam(value = "multipartfile") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) return MyResponse.buildFailure(EMPTY_FILE);
        List<Penalty> penaltyList = null;

        try {
            penaltyList = (List<Penalty>) DataHandle.parseExcel(multipartFile.getInputStream(), Penalty.class);
        } catch (Exception e) {
            return MyResponse.buildFailure(PARSE_FAILED);
        }
        return insertPenalties(penaltyList);
    }

    /**
     * 新增处罚记录
     */
    @PostMapping("/createPunishment")
    public MyResponse addPenalty(@RequestBody List<Penalty> penaltyList) {
        return insertPenalties(penaltyList);
    }


    private MyResponse insertPenalties(List<Penalty> penaltyList) {
        List<Penalty> success = new ArrayList<>();
        Penalty newPenalty;
        try {
            for (Penalty penalty : penaltyList) {
                newPenalty = penaltyService.insertPenalty(penalty);
                success.add(newPenalty);
            }
        } catch (Exception e) {
            return MyResponse.buildFailure(INSERT_FAILED, success);
        }

        return MyResponse.buildSuccess(success);
    }

    /**
     * 删除处罚记录
     */
    @PostMapping("/deletePunishment")
    public MyResponse deletePenalty(@RequestBody List<String> ids) {
        List<String> success = new ArrayList<>();
        Penalty newPenalty;
        try {
            for (String id : ids) {
                penaltyService.deletePenalty(Integer.parseInt(id));
                success.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponse.buildFailure(DELETE_FAILED, success);
        }
        return MyResponse.buildSuccess();
    }

    /**
     * 修改处罚记录
     */
    @PostMapping("/editPunishment")
    public MyResponse updatePenalty(@RequestBody Penalty penalty) {
        Penalty newPenalty;
        try {
            newPenalty = penaltyService.updatePenalty(penalty);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponse.buildFailure(UPDATE_FAILED);
        }
        return MyResponse.buildSuccess(true);
    }

    /**
     * 批量发布处罚记录
     */
    @PostMapping("/releasePunishment")
    public MyResponse changePenaltyStatus(@RequestParam String status, @RequestBody List<String> ids) {
        List<Penalty> penalties = null;

        try {
            penaltyService.changePenaltyStatus(status, ids);
        } catch (Exception e) {

        }

        return MyResponse.buildSuccess(penalties);
    }

    /**
     * 查询处罚记录
     * 不包含输入，则默认 pageNumber = 1; pageSize = 20;
     */
    @GetMapping("/getPunishmentList/{pageNumber}/{pageSize}")
    public MyResponse findAll(@PathVariable(value = "pageNumber", required = false) Integer pageNumber, @PathVariable(value = "pageSize", required = false) Integer pageSize, @RequestParam Penalty penalty) {
        if (pageNumber == null) pageNumber = 1;
        if (pageSize == null) pageSize = 20;
        List<Penalty> penaltyList = penaltyService.findAllPenalty(penalty, pageNumber, pageSize);
        if (penaltyList == null)
            return MyResponse.buildFailure(FIND_FAILED);
        else
            return MyResponse.buildSuccess(String.valueOf(penaltyList.size()), penaltyList);
    }

    @PostMapping("/getChart")
    public MyResponse getChart() {
        try {
            return MyResponse.buildSuccess(penaltyService.getChart());
        } catch (Exception e) {
            return MyResponse.buildFailure(e.getMessage());
        }
    }
}
