package com.consolefire.relayer.msgsrc.data.repo;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.msgsrc.data.entity.MessageSource.State;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public interface MessageSourceRepository {

    MessageSource findById(String identifier);

    int save(MessageSource messageSource);

    int updateState(String id, State state);

    int updateConfiguration(String id, JsonNode configuration);

    List<MessageSource> findAll();
}
