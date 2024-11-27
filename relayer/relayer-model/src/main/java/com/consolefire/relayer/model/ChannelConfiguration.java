package com.consolefire.relayer.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ChannelConfiguration {

    private String channelName;
    private Map<String, Object> properties;

}
