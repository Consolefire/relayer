package com.consolefire.relayer.util.data.repository.ro;

import com.consolefire.relayer.util.data.repository.Repository;
import java.io.Serializable;

public interface ReadRepository<ID extends Serializable, T> extends Repository<ID, T> {

}
