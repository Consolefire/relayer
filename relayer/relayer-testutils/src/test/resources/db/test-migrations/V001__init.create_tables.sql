create schema if not exists message_store;

create table if not exists message_store.outbound_message
(
    message_id       CHARACTER VARYING(64)            not null,
    group_id         CHARACTER VARYING(128)           not null,
    channel_name     CHARACTER VARYING(256)           not null,
    payload          CHARACTER VARYING,
    headers          CHARACTER VARYING,
    metadata         CHARACTER VARYING,
    state            CHARACTER VARYING(120),
    relayed_at       TIMESTAMP,
    relay_count      INTEGER,
    created_at       TIMESTAMP default LOCALTIMESTAMP not null,
    updated_at       TIMESTAMP,
    constraint outbound_message_pk
        primary key (message_id)
);

create table if not exists message_store.sidelined_message
(
    message_id       CHARACTER VARYING(64)            not null,
    group_id         CHARACTER VARYING(128)           not null,
    channel_name     CHARACTER VARYING(256)           not null,
    payload          CHARACTER VARYING,
    headers          CHARACTER VARYING,
    metadata         CHARACTER VARYING,
    last_tried_at    TIMESTAMP,
    retry_count      INTEGER,
    created_at       TIMESTAMP default LOCALTIMESTAMP not null,
    updated_at       TIMESTAMP,
    constraint sidelined_message_pk
        primary key (message_id)
);



