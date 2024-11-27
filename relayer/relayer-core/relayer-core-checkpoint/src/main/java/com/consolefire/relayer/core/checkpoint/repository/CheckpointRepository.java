package com.consolefire.relayer.core.checkpoint.repository;

import com.consolefire.relayer.core.checkpoint.Checkpoint;

import java.io.Serializable;
import java.util.Collection;

public interface CheckpointRepository<ID extends Serializable, C extends Checkpoint<ID>> {

    Collection<C> findAll();

    C findByIdentifier(ID identifier);

    <P extends C> P insert(C checkpoint);

    <P extends C> Collection<P> insert(Collection<C> checkpoints);

    <P extends C> P update(C checkpoint);

    <P extends C> Collection<P> update(Collection<C> checkpoints);

    <P extends C> P saveOrUpdate(C checkpoint);

    <P extends C> Collection<P> saveOrUpdate(Collection<C> checkpoints);

    void deleteByIdentifier(ID identifier);

    void deleteAllByIdentifiers(Collection<ID> identifiers);

    void delete(C checkpoint);

    void deleteAll();

}
