
create table if not exists outbound_messages (
    identifier varchar(120) primary key not null
    --- ... other columns
)

create table if not exists sidelined_messages (
                                                 identifier varchar(120) primary key not null
    --- ... other columns
)


create table if not exists sidelined_groups (
                                                 identifier varchar(120) primary key not null
    --- ... other columns
)
