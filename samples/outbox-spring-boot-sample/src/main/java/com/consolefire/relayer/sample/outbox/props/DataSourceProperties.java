package com.consolefire.relayer.sample.outbox.props;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String schemaName;
    private Map<String, String> parameters;
    private PoolProperties pool;
    private boolean testConnectionOnStart = true;
    private String testSql = "SELECT 1 AS IT_WORKS";
}
