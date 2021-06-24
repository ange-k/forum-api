USE forum;

CREATE TABLE games (
    id_name varchar,
    view_name varchar,
    PRIMARY KEY (id_name)
);

CREATE TABLE posts (
    id uuid,
    write_day date,
    game_id varchar,
    server varchar,
    player_name varchar,
    purpose varchar,
    vc_use boolean,
    device varchar,
    comment varchar,
    created_at timestamp,
    user_data map<varchar,varchar>,
    PRIMARY KEY ((game_id, write_day), created_at, id)
);
