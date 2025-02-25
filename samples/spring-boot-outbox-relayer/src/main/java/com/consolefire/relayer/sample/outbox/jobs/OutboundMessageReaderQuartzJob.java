package com.consolefire.relayer.sample.outbox.jobs;

import com.consolefire.relayer.core.tasks.MessageReaderTask;
import com.consolefire.relayer.outbox.model.OutboundMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class OutboundMessageReaderQuartzJob implements Job {

    public static final String JOB_DATA_KEY = "MESSAGE_SOURCE_PROPERTIES";

    private final ObjectMapper objectMapper;
    private final MessageReaderTask<UUID, OutboundMessage<UUID>> messageReaderTask;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("In spring quartz job");
        log.info("Job Key>> group: {}, name: {}",
            context.getJobDetail().getKey().getGroup(),
            context.getJobDetail().getKey().getName());
        String json = Optional.ofNullable(context.getJobDetail().getJobDataMap().get(JOB_DATA_KEY))
            .map(Object::toString).orElse("--empty--");
        log.info("Job Data: {}", json);
        messageReaderTask.execute();
    }
}
