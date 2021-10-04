package com.example.springbootinit.Controller;

import com.example.springbootinit.Entity.Penalty;
import com.example.springbootinit.Entity.User;
import com.example.springbootinit.Service.PenaltyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/penalty")
public class PenaltyController {
    @Resource
    PenaltyService penaltyService;

    /**
     * 新增处罚记录
     */
    @PostMapping("")
    public Penalty addPenalty(@RequestBody Penalty penalty){
        return penaltyService.insertPenalty(penalty);
    }

    /**
     * 删除处罚记录
     */
    @DeleteMapping("/{id}")
    public void deletePenalty(@PathVariable("id") int id){
        penaltyService.deletePenalty(id);
    }

    /**
     * 修改处罚记录
     */
    @PutMapping("")
    public Penalty updatePenalty(@RequestBody Penalty penalty){
        return penaltyService.updatePenalty(penalty);
    }

    /**
     * 批量发布处罚记录
     */
    @PutMapping("/release")
    public List<Penalty> releasePenalty(@RequestBody String ids){
        return penaltyService.releasePenalty(ids);
    }

    /**
     * id查处罚记录
     */
    @GetMapping("/{id}")
    public Penalty findPenaltybyId(@PathVariable("id") int id){
        return penaltyService.findPenaltyById(id);
    }

    /**
     * 全查处罚记录
     */
    @GetMapping("")
    public List<Penalty> findAll(){
        return penaltyService.findAllPenalty();
    }
}
