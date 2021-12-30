package com.example.springbootinit.Controller;

import com.example.springbootinit.Service.PenaltyService;
import com.example.springbootinit.Utils.DataHandle;
import com.example.springbootinit.Utils.MyResponse;
import com.example.springbootinit.VO.DataListVO;
import com.example.springbootinit.VO.PenaltyVO;
import com.example.springbootinit.VO.SummaryVO;
import liquibase.pro.packaged.S;
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
    public MyResponse importXls(@RequestParam(value = "uploadXls") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) return MyResponse.buildFailure(EMPTY_FILE);
        List<PenaltyVO> penaltyList = (List<PenaltyVO>) DataHandle.parseExcel(multipartFile.getInputStream(), PenaltyVO.class);
        return MyResponse.buildSuccess(penaltyService.insertPenalties(penaltyList));
    }

    /**
     * 新增处罚记录
     */
    @PostMapping("/createPunishment")
    public MyResponse addPenalty(@Valid @RequestBody DataListVO<PenaltyVO> dataList) {
        return MyResponse.buildSuccess(penaltyService.insertPenalties(dataList.getDataList()));
    }


    /**
     * 删除处罚记录
     */
    @PostMapping("/deletePunishment")
    public MyResponse deletePenalty(@Valid @RequestBody DataListVO<String> dataList) {
        penaltyService.deletePenalties(dataList.getDataList());
        return MyResponse.buildSuccess();
    }

    /**
     * 修改处罚记录
     */
    @PostMapping("/editPunishment")
    public MyResponse updatePenalty(@Valid @RequestBody PenaltyVO penaltyVO) {
        return MyResponse.buildSuccess(penaltyService.updatePenalty(penaltyVO));
    }

    /**
     * 批量发布处罚记录
     */
    @PostMapping("/releasePunishment")
    public MyResponse changePenaltyStatus(@Valid @RequestBody DataListVO<String> dataList) {
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


    /**
     * 获取总体情况
     */
    @GetMapping("/getSummary")
    public MyResponse getSummary(@RequestParam(value = "year") String year,
                                 @RequestParam(value = "month") String month) {
        //TODO
        SummaryVO summaryVO = penaltyService.getSummary(year, month);
        return MyResponse.buildSuccess(summaryVO);
    }

    /**
     * 获取机构罚单笔数排行
     */
    @GetMapping("/getOrganListOrderByCount")
    public MyResponse getOrganListOrderByCount(@RequestParam(value = "year") String year,
                                               @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

    /**
     * 获取机构罚没金额排行
     */
    @GetMapping("/getOrganListOrderByFine")
    public MyResponse getOrganListOrderByFine(@RequestParam(value = "year") String year,
                                              @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

    /**
     * 获取罚单地域分布
     */
    @GetMapping("/getPenaltyDistribution")
    public MyResponse getPenaltyDistribution(@RequestParam(value = "year") String year,
                                             @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

    /**
     * 获取大额罚单详情
     */
    @GetMapping("/getPenaltyOrderByFine")
    public MyResponse getPenaltyOrderByFine(@RequestParam(value = "year") String year,
                                            @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

    /**
     * 获取机构处罚决定分析
     */
    @GetMapping("/getAnalysisForOrgan")
    public MyResponse getAnalysisForOrgan(@RequestParam(value = "year") String year,
                                          @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

    /**
     * 获取个人处罚决定分析
     */
    @GetMapping("/getAnalysisForIndividual")
    public MyResponse getAnalysisForIndividual(@RequestParam(value = "year") String year,
                                               @RequestParam(value = "month") String month) {
        //TODO

        return null;
    }

}
