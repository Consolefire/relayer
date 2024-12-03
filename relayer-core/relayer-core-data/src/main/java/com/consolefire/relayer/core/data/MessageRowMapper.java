package com.consolefire.relayer.core.data;

import com.consolefire.relayer.model.Message;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface MessageRowMapper<ID extends Serializable, M extends Message<ID>> {

    M mapRow(ResultSet resultSet) throws SQLException;

}
