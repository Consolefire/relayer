package com.consolefire.relayer.outbox.core.data.repository;

import com.consolefire.relayer.core.data.repository.MessageWriteRepository;
import com.consolefire.relayer.core.data.repository.ParkedGroupRepository;
import com.consolefire.relayer.outbox.model.SidelinedGroup;
import com.consolefire.relayer.outbox.model.SidelinedMessage;
import java.io.Serializable;

public interface SidelinedGroupWriteRepository
    extends ParkedGroupRepository<SidelinedGroup> {

}
