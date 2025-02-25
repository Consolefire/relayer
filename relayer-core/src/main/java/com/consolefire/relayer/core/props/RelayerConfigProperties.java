package com.consolefire.relayer.core.props;

import com.consolefire.relayer.util.data.cfg.DataSourceProperties;
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
public class RelayerConfigProperties {

    private DataSourceProperties dataSource;

}
