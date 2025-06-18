package com.mhua.adminbackend.service.impl;

import com.mhua.adminbackend.mapper.HomeMapper;
import com.mhua.adminbackend.pojo.vo.CoachCountAndStudentCountVO;
import com.mhua.adminbackend.pojo.vo.CoachRecordsCurrentMonth;
import com.mhua.adminbackend.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;


    public CoachCountAndStudentCountVO getCoachCountAndStudentCount() {
        return homeMapper.getCoachCountAndStudentCount();
    }

    public List<CoachRecordsCurrentMonth> getCoachRecordsCurrentMonth(LocalDate signDate) {
        List<CoachRecordsCurrentMonth> list = homeMapper.getCoachRecordsCurrentMonth(signDate);

        return getCoachRecordsCurrentMonths(signDate, list);
    }


    public List<CoachRecordsCurrentMonth> getStudentRecordsCurrentMonth(LocalDate signDate) {
        List<CoachRecordsCurrentMonth> list = homeMapper.getStudentRecordsCurrentMonth(signDate);

        return getCoachRecordsCurrentMonths(signDate, list);
    }

    private List<CoachRecordsCurrentMonth> getCoachRecordsCurrentMonths(LocalDate signDate, List<CoachRecordsCurrentMonth> list) {
        int daysInMonth= YearMonth.of(signDate.getYear(), signDate.getMonth()).lengthOfMonth();

        List<CoachRecordsCurrentMonth> result = new ArrayList<>();

        Map<String, CoachRecordsCurrentMonth> map = new HashMap<>();
        for (CoachRecordsCurrentMonth item : list) {
            map.put(item.getSignDate().toString(), item);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate date = LocalDate.of(signDate.getYear(), signDate.getMonth(), i);
            CoachRecordsCurrentMonth item;

            if(map.containsKey(date.toString())) {
                item = map.get(date.toString());
            } else {
                item = new CoachRecordsCurrentMonth(date, 0);
            }

            result.add(item);
        }

        return result;
    }

}
