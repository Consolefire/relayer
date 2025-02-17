package com.consolefire.relayer.core.data.repository;

import com.consolefire.relayer.model.ParkedGroup;
import java.util.Collection;

public interface ParkedGroupReadRepository<G extends ParkedGroup> extends ParkedGroupRepository<G> {

    Collection<G> findAll();

    Collection<G> findByGroupId(String groupId);

}
