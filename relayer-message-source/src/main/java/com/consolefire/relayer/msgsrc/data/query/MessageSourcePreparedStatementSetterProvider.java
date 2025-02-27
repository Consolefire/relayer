package com.consolefire.relayer.msgsrc.data.query;

import com.consolefire.relayer.msgsrc.data.entity.MessageSource;
import com.consolefire.relayer.util.data.PreparedStatementSetter;

public interface MessageSourcePreparedStatementSetterProvider {

    PreparedStatementSetter<String> findByIdSetter();

    PreparedStatementSetter<MessageSource> saveSetter();

    PreparedStatementSetter<MessageSource> updateStateSetter();

    PreparedStatementSetter<MessageSource> updateConfigurationSetter();

    PreparedStatementSetter<Void> findAllSetter();

}