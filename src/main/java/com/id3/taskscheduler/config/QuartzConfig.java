package com.id3.taskscheduler.config;

import com.id3.taskscheduler.quartz.AutowiringSpringBeanJobFactory;
import com.id3.taskscheduler.quartz.MeetJob;
import com.id3.taskscheduler.quartz.RefreshTokenJob;
import org.quartz.*;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.quartz.*;

import java.util.Map;


@Configuration

public class QuartzConfig {

    @Bean(name = "createMeetJob")
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(MeetJob.class);
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean(name = "tokenRefreshJob")
    public JobDetailFactoryBean jobDetailRefreshToken() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(RefreshTokenJob.class);
        jobDetailFactory.setName("Qrtz_Job_Two_Detail");
        jobDetailFactory.setDescription("refresh token job");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(@Qualifier("createMeetJob") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0 26 13 * * ?"); // Her gün saat 9:00'da
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean refreshTokenTrigger(@Qualifier("tokenRefreshJob") JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(job);

        trigger.setRepeatInterval(60 * 1000);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("Qrtz_Trigger_Job_Two");
        return trigger;
    }

   /* @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(jobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);

        return schedulerFactory;
    }*/

    @Bean
    public SchedulerFactoryBean scheduler(Map<String, JobDetail> jobMap, Map<String, Trigger> triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        JobDetail[] jobs = jobMap.values().toArray(new JobDetail[0]);
        Trigger[] tr = triggers.values().toArray(new Trigger[0]);
        schedulerFactory.setJobDetails(jobs);
        schedulerFactory.setTriggers(tr);

        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        return jobFactory;
    }




}
