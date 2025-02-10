CREATE TABLE employee (
	id bigserial NOT NULL,
	"name" varchar(128) NOT NULL,
	created_at timestamptz NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (id)
);