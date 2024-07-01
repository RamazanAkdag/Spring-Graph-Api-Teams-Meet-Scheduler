# Spring Graph API Teams Meet Scheduler

This project demonstrates how to schedule meetings using Spring Boot, Quartz Scheduler, and Microsoft Graph API for Microsoft Teams. It integrates these technologies to provide a complete solution for scheduling and managing Teams meetings.

## Features

- **Spring Boot Integration**: Configured as a Spring Boot application for easy setup and deployment.
- **Quartz Scheduler**: Utilizes `CronTrigger` and `SimpleTrigger` for precise job scheduling.
- **Microsoft Graph API**: Schedules and manages Microsoft Teams meetings using Microsoft Graph API.
- **Token Refresh**: Automatically refreshes tokens at specified intervals to maintain authentication.

## Technologies Used

- **Spring Boot**: Framework for building Java applications.
- **Quartz Scheduler**: Library for scheduling jobs.
- **Microsoft Graph API**: API for accessing Microsoft services.
- **Java**: Programming language used for development.

## Configuration

### Job Definitions

1. **MeetJob**: Defines the job logic for scheduling meetings.
    ```java
    import org.quartz.Job;
    import org.quartz.JobExecutionContext;
    import org.quartz.JobExecutionException;
    import org.springframework.stereotype.Component;

    @Component
    public class MeetJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // Job logic here
        }
    }
    ```

2. **RefreshTokenJob**: Defines the job logic for refreshing tokens.
    ```java
    import org.quartz.Job;
    import org.quartz.JobExecutionContext;
    import org.quartz.JobExecutionException;
    import org.springframework.stereotype.Component;

    @Component
    public class RefreshTokenJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // Job logic here
        }
    }
    ```

### JobDetail Bean Definitions

3. **JobDetail Beans**: Registers the job with Spring.
    ```java
    import org.quartz.JobDetail;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.scheduling.quartz.JobDetailFactoryBean;

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

        @Bean(name = "refreshTokenJob")
        public JobDetailFactoryBean jobDetailRefreshToken() {
            JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
            jobDetailFactory.setJobClass(RefreshTokenJob.class);
            jobDetailFactory.setName("Qrtz_Job_Two_Detail");
            jobDetailFactory.setDescription("refresh token job");
            jobDetailFactory.setDurability(true);
            return jobDetailFactory;
        }
    }
    ```

### Trigger Definitions

4. **Trigger Beans**: Schedules the job to run at specified intervals.
    ```java
    import org.quartz.Trigger;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
    import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

    @Configuration
    public class QuartzConfig {

        @Bean
        public CronTriggerFactoryBean cronTrigger(@Qualifier("createMeetJob") JobDetail jobDetail) {
            CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
            factoryBean.setJobDetail(jobDetail);
            factoryBean.setCronExpression("0 16 15 * * ?"); // Every day at 15:16
            return factoryBean;
        }

        @Bean
        public SimpleTriggerFactoryBean refreshTokenTrigger(@Qualifier("refreshTokenJob") JobDetail job) {
            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setJobDetail(job);
            int frequencyInSec = 300; // 5 minutes
            trigger.setRepeatInterval(frequencyInSec * 1000);
            trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
            trigger.setName("Qrtz_Trigger_Job_Two");
            return trigger;
        }
    }
    ```

### Scheduler Configuration

5. **SchedulerFactoryBean**: Manages the job and trigger.
    ```java
    import org.quartz.spi.JobFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.scheduling.quartz.SchedulerFactoryBean;

    @Configuration
    public class QuartzConfig {

        @Autowired
        private JobFactory jobFactory;

        @Bean
        public SchedulerFactoryBean scheduler(Trigger cronTrigger, Trigger refreshTokenTrigger, JobDetail jobDetail, JobDetail refreshTokenJob) {
            SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
            schedulerFactory.setJobFactory(jobFactory);
            schedulerFactory.setJobDetails(jobDetail, refreshTokenJob);
            schedulerFactory.setTriggers(cronTrigger, refreshTokenTrigger);
            return schedulerFactory;
        }
    }
    ```

## Usage

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/RamazanAkdag/Spring-Graph-Api-Teams-Meet-Scheduler.git
    cd Spring-Graph-Api-Teams-Meet-Scheduler
    ```

2. **Update Configuration**: Update `application.properties` or other configuration files with your specific settings.

3. **Run the Application**:
    ```bash
    ./mvnw spring-boot:run
    ```



## Acknowledgments
- Spring Boot Documentation
- Quartz Scheduler Documentation
- Microsoft Graph API Documentation

### Repository
[GitHub Repository](https://github.com/RamazanAkdag/Spring-Graph-Api-Teams-Meet-Scheduler)

By following this README, you can set up and run the project to schedule daily jobs using Quartz Scheduler and send emails with Microsoft Graph API.
