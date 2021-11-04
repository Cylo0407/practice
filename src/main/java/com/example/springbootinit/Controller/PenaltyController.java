package com.example.springbootinit.Controller;

import com.example.springbootinit.Exception.ValidException;
import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.DataHandle;
import com.example.springbootinit.Utils.MyResponse;
import com.example.springbootinit.VO.DataListVO;
import com.example.springbootinit.VO.PenaltyVO;
import lombok.SneakyThrows;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    private static final String EMPTY_FILE = "文件不能为空";

    @Resource
    PenaltyService penaltyService;

    /**
     * 导入excel
     */
    @PostMapping("/importXls")
    public MyResponse importXls(@RequestParam(value =  "uploadXls") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) return MyResponse.buildFailure(EMPTY_FILE);
        List<PenaltyVO> penaltyList = (List<PenaltyVO>) DataHandle.parseExcel(multipartFile.getInputStream(), PenaltyVO.class);
        return MyResponse.buildSuccess(penaltyService.insertPenalties(penaltyList));
    }

    /**
     * 新增处罚记录
     */
    @PostMapping("/createPunishment")
    public MyResponse addPenalty(@Valid @RequestBody DataListVO<PenaltyVO> dataList){
        return MyResponse.buildSuccess(penaltyService.insertPenalties(dataList.getDataList()));
    }



    /**
     * 删除处罚记录
     */
    @PostMapping("/deletePunishment")
    public MyResponse deletePenalty(@Valid @RequestBody DataListVO<String> dataList){
        penaltyService.deletePenalties(dataList.getDataList());
        return MyResponse.buildSuccess();
    }

    /**
     * 修改处罚记录
     */
    @PostMapping("/editPunishment")
    public MyResponse updatePenalty(@Valid @RequestBody PenaltyVO penaltyVO){
        return MyResponse.buildSuccess(penaltyService.updatePenalty(penaltyVO));
    }

    /**
     * 批量发布处罚记录
     */
    @PostMapping("/releasePunishment")
    public MyResponse changePenaltyStatus(@Valid @RequestBody DataListVO<String> dataList){
        return MyResponse.buildSuccess(penaltyService.changePenaltyStatus(dataList.getListRelatedOperation(), dataList.getDataList()));
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
            @Valid @ModelAttribute PenaltyVO penaltyVO) {

        if (pageNumber == null) pageNumber = 1;
        DataListVO dataList = penaltyService.findAllPenalty(penaltyVO, pageNumber, pageSize, isVague);
        return MyResponse.buildSuccess(dataList);

    }
}
