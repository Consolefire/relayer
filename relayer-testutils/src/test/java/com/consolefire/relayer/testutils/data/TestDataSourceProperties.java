package com.consolefire.relayer.testutils.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(asEnum = true)
public class TestDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String schemaName;
    private Map<String, String> parameters;
    private boolean testConnectionOnStart = true;
    private String testSql = "SELECT 1 AS IT_WORKS";
}
