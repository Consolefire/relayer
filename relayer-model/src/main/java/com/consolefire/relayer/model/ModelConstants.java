package com.consolefire.relayer.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ModelConstants {

    private ModelConstants() {
        throw new UnsupportedOperationException(
                "Cannot support creating instance of this class: ." + getClass().getCanonicalName());
    }

    public static final String DEFAULT_MESSAGE_GROUP = "G-DEFAULT";

    public static final String DEFAULT_DB_PATH = "/db";
    public static final String DEFAULT_DB_MIGRATION_PATH =
            Paths.get(DEFAULT_DB_PATH, "migrations").toString();

    public static final String DEFAULT_DB_MAPPINGS_PATH =
            Paths.get(DEFAULT_DB_PATH, "mappings").toString();
    public static final String DEFAULT_DB_MAPPING_FILE = Path.of(DEFAULT_DB_MAPPINGS_PATH,
            "OutboundMessage.generic.mapping.json").toString();


}
