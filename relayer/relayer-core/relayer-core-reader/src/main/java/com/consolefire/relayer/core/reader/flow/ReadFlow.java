package com.consolefire.relayer.core.reader.flow;

import com.consolefire.relayer.core.checkpoint.MessageSourceCheckpoint;
import com.consolefire.relayer.core.checkpoint.ProcessorCheckpoint;
import com.consolefire.relayer.core.checkpoint.ReaderCheckpoint;
import com.consolefire.relayer.core.checkpoint.service.ReaderCheckpointService;
import com.consolefire.relayer.core.common.MessageWrapper;
import com.consolefire.relayer.core.data.tx.TransactionalOperation;
import com.consolefire.relayer.core.distributor.MessageDistributor;
import com.consolefire.relayer.core.reader.OutboundMessageSource;
import com.consolefire.relayer.core.reader.SidelinedMessageSource;
import com.consolefire.relayer.model.OutboundMessage;
import com.consolefire.relayer.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
public class ReadFlow<ID extends Serializable> {

    private final ExecutorService executorService;
    private final ReaderCheckpointService readerCheckpointService;
    private final MessageDistributor<ID, OutboundMessage<ID>, MessageWrapper<ID, OutboundMessage<ID>>> messageDistributor;

    public void read(String readerIdentifier, Set<String> sourceIdentifiers) {
        // begin tx
        // validate and/or reset reader checkpoint
        TransactionalOperation<String, Boolean> transactionalOperation
                // TODO: Should build using transaction manager delegate
                = new TransactionalOperation<>((identifier) -> validateReaderCheckpoint(identifier));
        Boolean validCheckpoint = transactionalOperation.doInTransaction(readerIdentifier);
        if (!validCheckpoint) {
            log.error("Invalid checkpoint found for reader identifier: [{}]", readerIdentifier);
            return;
        }
        // end tx

        List<MessageWrapper<ID, OutboundMessage<ID>>> allMessageList = new ArrayList<>();

        // Build and Spawn Single-Source outbound readers

        Map<String, Pair<OutboundMessageSource<ID>, SidelinedMessageSource<ID>>> messageSources
                = buildCombinedMessageSources(sourceIdentifiers);
        Collection<CombinedMessageSourceReaderTask<ID, MessageSourceReaderTaskResult<ID, OutboundMessage<ID>>>> allTasks
                = messageSources.entrySet().stream()
                .map(entry ->
                        new CombinedMessageSourceReaderTask<>(
                                entry.getKey(), entry.getValue().left(), entry.getValue().right()
                        ))
                .toList();

        Collection<Future<MessageSourceReaderTaskResult<ID, OutboundMessage<ID>>>> allFutures = null;
        try {
            allFutures = executorService.invokeAll(allTasks);
        } catch (InterruptedException e) {
            // TODO: special handling for one/some of the task/s has/ve error/s
            throw new RuntimeException(e);
        }


        if (null != allFutures && !allFutures.isEmpty()) {
            for (Future<MessageSourceReaderTaskResult<ID, OutboundMessage<ID>>> future : allFutures) {
                MessageSourceReaderTaskResult<ID, OutboundMessage<ID>> result = null;
                try {
                    result = future.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }

                TransactionalOperation<MessageSourceCheckpoint, Boolean> operation
                        = new TransactionalOperation<>((checkpoint) -> updateCheckpoint(checkpoint));
                MessageSourceCheckpoint messageSourceCheckpoint
                        = MessageSourceCheckpoint.forSidelinedMessage(
                        result.getSourceIdentifier(), result.getMessages().size(), readerIdentifier);
                log.info("Setting MessageSourceCheckpoint for the sidelined messages: {}", messageSourceCheckpoint);
                String srcId = result.getSourceIdentifier();
                allMessageList.addAll(result.getMessages().stream()
                        .map(m -> new MessageWrapper<>(readerIdentifier, srcId, m))
                        .toList()
                );
                operation.doInTransaction(messageSourceCheckpoint);
            }
        }

        if (allMessageList.isEmpty()) {
            log.info("No messages found for reader identifier: {}, from sources: [{}]",
                    readerIdentifier, sourceIdentifiers);
            return;
        }

        // Sort messages
        Collections.sort(allMessageList);

        // distribute to processors
        allMessageList.forEach(wrappedMessage -> {
            UUID processorId = messageDistributor.distribute(wrappedMessage);

        });

        // begin tx
        // update count in reader checkpoint
        // end tx
    }


    private Boolean validateReaderCheckpoint(String readerIdentifier) {
        ReaderCheckpoint readerCheckpoint = readerCheckpointService.findByIdentifier(readerIdentifier);
        // No checkpoint exists
        if (null == readerCheckpoint) {
            log.info("No reader checkpoint found for identifier {}", readerIdentifier);
            // create new
            readerCheckpoint = ReaderCheckpoint.builder()
                    .identifier(readerIdentifier)
                    .build();
            readerCheckpointService.save(readerCheckpoint);
            return true;
        }
        log.info("Reader checkpoint found: {}", readerCheckpoint);

        long totalReadCount = readerCheckpoint.getMessageSourceCheckpoints()
                .stream().mapToLong(MessageSourceCheckpoint::getReadCount).sum();
        long totalProcessedCount = readerCheckpoint.getProcessorCheckpoints()
                .stream().mapToLong(ProcessorCheckpoint::getProcessedCount).sum();
        if (totalReadCount == totalProcessedCount) {
            readerCheckpointService.reset(readerCheckpoint);
            return true;
        }
        return false;
    }

    private Boolean updateCheckpoint(MessageSourceCheckpoint checkpoint) {
        return null;
    }

    private Map<String, Pair<OutboundMessageSource<ID>, SidelinedMessageSource<ID>>> buildCombinedMessageSources(
            Set<String> sourceIdentifiers) {
        return null;
    }

}
