package com.upb.qresent.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticsDto {
    private String statisticsId;
    private int totalNumberOfStudents;
    private int numberOfStudentsPresentAtTheBeginning;
    private int numberOfStudentsPresentDuringTheCourse;
    private int numberOfStudentsPresentAtTheEndOfTheCourse;
}
