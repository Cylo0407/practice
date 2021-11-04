package com.example.springbootinit.Controller;

import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.DataHandle;
import com.example.springbootinit.Utils.MyResponse;
import com.example.springbootinit.VO.DataListVO;
import com.example.springbootinit.VO.PenaltyVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    private static final String EMPTY_FILE = "文件不能为空!";
    private static final String PARSE_FAILED = "文件解析错误!";
    private static final String FIND_FAILED = "查询出错!";

    @Resource
    PenaltyService penaltyService;

    /**
     * 导入excel
     */
    @PostMapping("/importXls")
    public MyResponse importXls(@RequestParam(value =  "uploadXls") MultipartFile multipartFile){
        if(multipartFile.isEmpty()) return MyResponse.buildFailure(EMPTY_FILE);
        try {
            List<PenaltyVO> penaltyList = (List<PenaltyVO>)DataHandle.parseExcel(multipartFile.getInputStream(), PenaltyVO.class);
            return insertPenalties(penaltyList);
        }catch (Exception e) {
            e.printStackTrace();
            return MyResponse.buildFailure(PARSE_FAILED);
        }
    }

    /**
     * 新增处罚记录
     */
    @PostMapping("/createPunishment")
    public MyResponse addPenalty(@RequestBody DataListVO<PenaltyVO> dataList){
        return insertPenalties(dataList.getDataList());
    }



    private MyResponse insertPenalties(List<PenaltyVO> penaltyList) {
        try {
            return MyResponse.buildSuccess(penaltyService.insertPenalties(penaltyList));
        } catch (Exception e){
            return MyResponse.buildFailure(e.getMessage());
        }
    }

    /**
     * 删除处罚记录
     */
    @PostMapping("/deletePunishment")
    public MyResponse deletePenalty(@RequestBody DataListVO<String> dataList){
        try {
            penaltyService.deletePenalties(dataList.getDataList());
        } catch (Exception e){
            return MyResponse.buildFailure(e.getMessage());
        }
        return MyResponse.buildSuccess();
    }

    /**
     * 修改处罚记录
     */
    @PostMapping("/editPunishment")
    public MyResponse updatePenalty(@RequestBody PenaltyVO penaltyVO){
        try {
             return MyResponse.buildSuccess(penaltyService.updatePenalty(penaltyVO));
        } catch (Exception e) {
            return MyResponse.buildFailure(e.getMessage());
        }
    }

    /**
     * 批量发布处罚记录
     */
    @PostMapping("/releasePunishment")
    public MyResponse changePenaltyStatus(@RequestBody DataListVO<String> dataList){
        try {
            return MyResponse.buildSuccess(penaltyService.changePenaltyStatus(dataList.getListRelatedOperation(), dataList.getDataList()));
        }catch (Exception e) {
            return MyResponse.buildFailure(e.getMessage());
        }
    }

    /**
     * 查询处罚记录
     * 默认 pageNumber = 1; pageSize = 20;
     */
    @GetMapping("/getPunishmentList/{pageNumber}")
    public MyResponse findAll(
            @PathVariable(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "isVague", required = false, defaultValue = "true") Boolean isVague,
            @ModelAttribute PenaltyVO penaltyVO) {

        if (pageNumber == null) pageNumber = 1;
        try {
            DataListVO dataList = penaltyService.findAllPenalty(penaltyVO, pageNumber, pageSize, isVague);
            return MyResponse.buildSuccess(dataList);
        }catch (Exception e) {
            return MyResponse.buildFailure(FIND_FAILED);
        }

    }
}
