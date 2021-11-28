package com.upb.qresent.statistics;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class StatisticsService {
    private StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Statistics retrieveOrGenerateStatistics(int courseNo) {
        return statisticsRepository.findByCourseNo(courseNo);
    }

    public int getCourseNoByData(Date endDate) throws ParseException {
        Date startDate = new GregorianCalendar(2021, Calendar.OCTOBER, 4).getTime();
        return (int) (Math.abs(endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 * 7)) + 1;
    }
}
