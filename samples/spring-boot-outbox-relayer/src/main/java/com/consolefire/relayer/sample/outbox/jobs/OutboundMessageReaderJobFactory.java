package com.consolefire.relayer.sample.outbox.jobs;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.consolefire.relayer.core.msgsrc.MessageSourceContext;
import com.consolefire.relayer.core.msgsrc.MessageSourceProvider;
import com.consolefire.relayer.model.source.MessageSourceProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboundMessageReaderJobFactory {

    private final Scheduler scheduler;
    private final ObjectMapper objectMapper;
    private final MessageSourceProvider messageSourceProvider;


    @SneakyThrows
    @PostConstruct
    void createJobs() {
        log.info("Create jobs...");



        if (messageSourceProvider.getAllRegisteredSources() == null
            || messageSourceProvider.getAllRegisteredSources().isEmpty()) {
            log.warn("No sources");
            return;
        }

        Set<MessageSourceProperties> messageSourceProperties
            = messageSourceProvider.getAllRegisteredSources().stream()
            .map(MessageSourceContext::getMessageSourceProperties)
            .collect(Collectors.toSet());
        log.info("Source count: {}", messageSourceProperties.size());

        for (MessageSourceProperties sourceProperty : messageSourceProperties) {
            JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
            jobDetailFactoryBean.setJobClass(OutboundMessageReaderQuartzJob.class);
            jobDetailFactoryBean.setDescription("OUTBOUND::" + sourceProperty.getIdentifier());
            jobDetailFactoryBean.setDurability(true);
            jobDetailFactoryBean.setGroup(sourceProperty.getIdentifier());
            jobDetailFactoryBean.setName("JOB|OUTBOUND|" + sourceProperty.getIdentifier());
            jobDetailFactoryBean.setJobDataAsMap(
                Map.of("MESSAGE_SOURCE_PROPERTIES", objectMapper.writeValueAsString(sourceProperty)));
            jobDetailFactoryBean.afterPropertiesSet();
            JobDetail jobDetail = jobDetailFactoryBean.getObject();

            Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity("TRIGGER|OUTBOUND|" + sourceProperty.getIdentifier(), sourceProperty.getIdentifier())
                .withDescription("Sample trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                .build();
            scheduler.scheduleJob(jobDetail, Set.of(trigger), true);
        }

    }


}
