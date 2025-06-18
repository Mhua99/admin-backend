package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.vo.CoachCountAndStudentCountVO;
import com.mhua.adminbackend.pojo.vo.CoachRecordsCurrentMonth;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/business/home")
@Tag(name = "首页管理")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/coachCountAndStudentCount")
    @Operation(summary = "获取教练和学生数量")
    public Result<CoachCountAndStudentCountVO> getCoachCountAndStudentCount() {
        CoachCountAndStudentCountVO coachCountAndStudentCountVO = homeService.getCoachCountAndStudentCount();
        return Result.success(coachCountAndStudentCountVO);
    }

    @GetMapping("/getCoachRecordsCurrentMonth")
    @Operation(summary = "获取教练当月记录")
    public Result<List<CoachRecordsCurrentMonth>> getCoachRecordsCurrentMonth(@RequestParam(required = false) LocalDate signDate) {
        List<CoachRecordsCurrentMonth> list =  homeService.getCoachRecordsCurrentMonth(signDate);
        return Result.success(list);
    }

    @GetMapping("/getStudentRecordsCurrentMonth")
    @Operation(summary = "获取学生当月记录")
    public Result<List<CoachRecordsCurrentMonth>> getStudentRecordsCurrentMonth(@RequestParam(required = false) LocalDate signDate) {
        List<CoachRecordsCurrentMonth> list =  homeService.getStudentRecordsCurrentMonth(signDate);
        return Result.success(list);
    }

}
