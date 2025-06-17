package com.mhua.adminbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classes extends Comm  implements Serializable {
    /**
     * 课程 id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private Integer year;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 课时数
     */
    private Integer courseCount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  endTime;
}
