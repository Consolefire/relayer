

CREATE SEQUENCE IF NOT EXISTS seq_outbound_message
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	CYCLE;

CREATE TABLE IF NOT EXISTS outbound_message (
	message_id varchar(64) NOT NULL,
	message_sequence int8 NOT NULL,
	group_id varchar(256) NOT NULL,
	channel_name varchar(256) NOT NULL,
	payload text NULL,
	headers text NULL,
	metadata text NULL,
	state varchar(120) NOT NULL,
	attempted_at timestamptz NULL,
	attempt_count int8 NULL,
	relayed_at timestamptz NULL,
	relay_count int8 NULL,
	relay_error text NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NULL,
	CONSTRAINT outbound_message_pk PRIMARY KEY (message_id)
);
CREATE INDEX IF NOT EXISTS outbound_message_channel_name_idx ON outbound_message USING btree (channel_name);
CREATE INDEX IF NOT EXISTS outbound_message_group_id_idx ON outbound_message USING btree (group_id);
CREATE INDEX IF NOT EXISTS outbound_message_message_sequence_idx ON outbound_message USING btree (message_sequence);
CREATE INDEX IF NOT EXISTS outbound_message_state_idx ON outbound_message USING btree (state);


CREATE TABLE sidelined_group (
	group_id varchar(256) NOT NULL,
	created_at timestamptz DEFAULT now() NOT NULL,
	updated_at timestamptz NULL,
	CONSTRAINT sidelined_group_pk PRIMARY KEY (group_id)
);

