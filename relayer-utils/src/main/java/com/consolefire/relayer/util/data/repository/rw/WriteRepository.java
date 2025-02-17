package com.consolefire.relayer.util.data.repository.rw;

import com.consolefire.relayer.util.data.repository.Repository;
import java.io.Serializable;

public interface WriteRepository<ID extends Serializable, T> extends Repository<ID, T> {

}
