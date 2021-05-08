package com.myexaminer.component;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ExamScheduler {

    @Scheduled(cron = "0 * * * * *")
    public void closeExams(){
        log.info("Minęła minuta");
    }
}
