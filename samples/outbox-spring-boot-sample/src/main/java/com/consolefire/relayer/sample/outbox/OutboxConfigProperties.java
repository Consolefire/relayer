package com.consolefire.relayer.sample.outbox;

import com.consolefire.relayer.sample.outbox.props.TenantProperties;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "outbox")
public class OutboxConfigProperties {

    @NonNull
    private Set<TenantProperties> tenants;
    private String appTenantId;

}
