package com.consolefire.relayer.core.checkpoint.data.repo.impl;

import com.consolefire.relayer.core.checkpoint.Checkpoint;
import com.consolefire.relayer.core.checkpoint.data.repo.CheckpointRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class AbstractCheckpointRepository<ID extends Serializable, C extends Checkpoint<ID>>
        implements CheckpointRepository<ID, C> {

    @Override
    public Collection<C> findAll() {
        return List.of();
    }

    @Override
    public C findByIdentifier(ID identifier) {
        return null;
    }

    @Override
    public <P extends C> P insert(C checkpoint) {
        return null;
    }

    @Override
    public <P extends C> Collection<P> insert(Collection<C> checkpoints) {
        return List.of();
    }

    @Override
    public <P extends C> P update(C checkpoint) {
        return null;
    }

    @Override
    public <P extends C> Collection<P> update(Collection<C> checkpoints) {
        return List.of();
    }

    @Override
    public <P extends C> P saveOrUpdate(C checkpoint) {
        return null;
    }

    @Override
    public <P extends C> Collection<P> saveOrUpdate(Collection<C> checkpoints) {
        return List.of();
    }

    @Override
    public void deleteByIdentifier(ID identifier) {

    }

    @Override
    public void deleteAllByIdentifiers(Collection<ID> identifiers) {

    }

    @Override
    public void delete(C checkpoint) {

    }

    @Override
    public void deleteAll() {

    }
}
