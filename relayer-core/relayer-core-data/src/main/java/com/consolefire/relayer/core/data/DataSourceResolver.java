package com.consolefire.relayer.core.data;

import com.consolefire.relayer.model.source.MessageSourceProperties;
import javax.sql.DataSource;

public interface DataSourceResolver {

    DataSource resolve(MessageSourceProperties messageSource);

}
