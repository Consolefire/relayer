package com.consolefire.relayer.model;

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
public class TestMessagePayload {

    private Long id;
    private String name;
    private Map<String, String> parameters;

    public static TestMessagePayload random() {
        return TestMessagePayload.builder()
            .id(1001l).name("some name")
            .parameters(Map.of("P1", "V1"))
            .build();
    }

}
