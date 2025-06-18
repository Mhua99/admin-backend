package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.vo.CoachCountAndStudentCountVO;
import com.mhua.adminbackend.pojo.vo.CoachRecordsCurrentMonth;

import java.time.LocalDate;
import java.util.List;

public interface HomeService {
    CoachCountAndStudentCountVO getCoachCountAndStudentCount();

    List<CoachRecordsCurrentMonth> getCoachRecordsCurrentMonth(LocalDate signDate);

    List<CoachRecordsCurrentMonth> getStudentRecordsCurrentMonth(LocalDate signDate);
}
