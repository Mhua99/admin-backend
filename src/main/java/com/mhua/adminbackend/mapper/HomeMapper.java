package com.mhua.adminbackend.mapper;

import com.mhua.adminbackend.pojo.vo.CoachCountAndStudentCountVO;
import com.mhua.adminbackend.pojo.vo.CoachRecordsCurrentMonth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface HomeMapper {

    CoachCountAndStudentCountVO getCoachCountAndStudentCount();

    List<CoachRecordsCurrentMonth> getCoachRecordsCurrentMonth(@RequestParam("signDate") LocalDate signTime );

    List<CoachRecordsCurrentMonth> getStudentRecordsCurrentMonth(LocalDate signDate);
}
