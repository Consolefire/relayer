package com.consolefire.relayer.model.source;

import com.consolefire.relayer.util.data.cfg.DataSourceProperties;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSourceProperties implements Serializable {

    public enum ConfigurationKey {
        DATA_SOURCE, DATA_SOURCE_REFERENCE
    }

    private String identifier;
    private Map<String, Object> configuration;
    private DataSourceProperties dataSource;

    public void setConfiguration(Map<String, Object> configuration) {
        if (null == configuration || configuration.isEmpty()) {
            this.configuration = new HashMap<>();
            return;
        }
        this.configuration = configuration;
    }

}
