package com.consolefire.relayer.util.data.cfg;

import java.util.Map;
import java.util.Optional;
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
public class DataSourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String schemaName;
    private Map<String, String> parameters;
    private PoolProperties pool;
    private boolean testConnectionOnStart = true;
    private String testSql = "SELECT 1 AS IT_WORKS";

    public static DataSourceProperties fromMap(Map<String, Object> map) {
        DataSourceProperties dsp = DataSourceProperties.builder()
            .driverClassName(toDriverClass(map))
            .url(String.valueOf(map.get(Fields.url.name())))
            .build();
        return dsp;
    }

    private static String toDriverClass(Map<String, Object> map) {
        String fieldName = Fields.driverClassName.name();
        if (map.containsKey(fieldName)) {
            return getString(map, fieldName);
        } else if (map.containsKey("driver-class-name")) {
            return getString(map, "driver-class-name");
        }
        return null;
    }

    private static String getString(Map<String, Object> map, String fieldName) {
        return Optional.ofNullable(map.get(fieldName))
            .map(Object::toString)
            .orElse(null);
    }


}
