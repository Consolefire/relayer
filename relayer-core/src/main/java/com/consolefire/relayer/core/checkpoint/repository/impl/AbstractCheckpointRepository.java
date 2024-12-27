package com.consolefire.relayer.core.checkpoint.repository.impl;

import com.consolefire.relayer.core.checkpoint.Checkpoint;
import com.consolefire.relayer.core.checkpoint.repository.CheckpointQueryProvider;
import com.consolefire.relayer.core.checkpoint.repository.CheckpointRepository;
import com.consolefire.relayer.core.checkpoint.repository.CheckpointRowMapper;
import com.consolefire.relayer.core.data.DuplicateRecordExistsException;
import com.consolefire.relayer.core.data.NonUniqueResultExistsException;
import com.consolefire.relayer.core.data.PreparedStatementSetter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCheckpointRepository<ID extends Serializable, C extends Checkpoint<ID>> //
    implements CheckpointRepository<ID, C> {

    private final CheckpointQueryProvider checkpointQueryProvider;
    private final CheckpointRowMapper<ID, C> checkpointRowMapper;

    protected abstract DataSource getDataSource();


    @Override
    public Collection<C> findAll() {
        String sql = checkpointQueryProvider.queryForFindAll();
        log.debug("Find all SQL: {}", sql);
        Collection<C> collection = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        collection.add(checkpointRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    @Override
    public C findByIdentifier(ID identifier) {
        String sql = checkpointQueryProvider.queryForFindByIdentifier();
        log.debug("Find by identifier SQL: {}", sql);
        List<C> collection = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                getCheckpointIdStatementSetter().setValues(identifier, statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        collection.add(checkpointRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (collection.isEmpty()) {
            return null;
        }
        if (collection.size() > 1) {
            throw new NonUniqueResultExistsException("More than one records found for identifier: " + identifier);
        }
        return collection.get(0);
    }

    @Override
    public <P extends C> P insert(C checkpoint) {
        String sql = checkpointQueryProvider.queryForInsert();
        log.debug("Insert SQL: {}", sql);
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                getInsertStatementSetter().setValues(checkpoint, statement);
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new DuplicateRecordExistsException("Error in insert checkpoint: " + checkpoint.toString());
                }
            }
        } catch (SQLException e) {
            throw new DuplicateRecordExistsException(e);
        }
        return (P) findByIdentifier(checkpoint.getIdentifier());
    }

    @Override
    public <P extends C> Collection<P> insert(Collection<C> checkpoints) {
        if (null == checkpoints || checkpoints.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<P> collection = new ArrayList<>();
        checkpoints.forEach(c -> collection.add(insert(c)));
        return collection;
    }

    @Override
    public <P extends C> P update(C checkpoint) {
        String sql = checkpointQueryProvider.queryForUpdate();
        log.debug("Update SQL: {}", sql);
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                getUpdateStatementSetter().setValues(checkpoint, statement);
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new RuntimeException("Error in insert checkpoint: " + checkpoint.toString());
                }
                return (P) checkpoint;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <P extends C> Collection<P> update(Collection<C> checkpoints) {
        if (null == checkpoints || checkpoints.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<P> collection = new ArrayList<>();
        checkpoints.forEach(c -> collection.add(update(c)));
        return collection;
    }

    @Override
    public <P extends C> P saveOrUpdate(C checkpoint) {
        log.debug("Find existing checkpoint for id: {}", checkpoint.getIdentifier());
        C existingCheckpoint = findByIdentifier(checkpoint.getIdentifier());
        if (null == existingCheckpoint) {
            log.debug("No checkpoint found for id: {}", checkpoint.getIdentifier());
            return insert(checkpoint);
        }
        log.debug("Checkpoint found: {}", existingCheckpoint);
        return update(checkpoint);
    }

    @Override
    public <P extends C> Collection<P> saveOrUpdate(Collection<C> checkpoints) {
        if (null == checkpoints || checkpoints.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<P> collection = new ArrayList<>();
        checkpoints.forEach(c -> collection.add(saveOrUpdate(c)));
        return collection;
    }

    @Override
    public void deleteByIdentifier(ID identifier) {
        String sql = checkpointQueryProvider.queryForDeleteByIdentifier();
        log.debug("Delete by identifier SQL: {}", sql);
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                getCheckpointIdStatementSetter().setValues(identifier, statement);
                int count = statement.executeUpdate();
                if (count != 1) {
                    throw new RuntimeException("Error in delete checkpoint for id: " + identifier);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteAllByIdentifiers(Collection<ID> identifiers) {
        if (null == identifiers || identifiers.isEmpty()) {
            return;
        }
        Set<ID> idSet = identifiers.stream().collect(Collectors.toSet());
        String sql = checkpointQueryProvider.queryForDeleteAllByIdentifiers();
        log.debug("Delete all by identifiers SQL: {}", sql);
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                getCheckpointIdsStatementSetter().setValues(idSet, statement);
                int count = statement.executeUpdate();
                log.debug("Deleted " + count + "records");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(C checkpoint) {
        deleteByIdentifier(checkpoint.getIdentifier());
    }

    @Override
    public void deleteAll() {
        String sql = checkpointQueryProvider.queryForDeleteAll();
        log.debug("Delete all SQL: {}", sql);
        try (Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int count = statement.executeUpdate();
                log.debug("Deleted " + count + "records");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract PreparedStatementSetter<C> getInsertStatementSetter();

    protected abstract PreparedStatementSetter<C> getUpdateStatementSetter();

    protected abstract PreparedStatementSetter<ID> getCheckpointIdStatementSetter();

    protected abstract PreparedStatementSetter<Set<ID>> getCheckpointIdsStatementSetter();

}
