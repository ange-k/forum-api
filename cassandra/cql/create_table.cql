USE forum;

CREATE TABLE games (
    id_name varchar,
    view_name varchar,
    PRIMARY KEY (id_name)
);

CREATE TABLE posts (
    uuid uuid,
    write_day date,
    game_id varchar,
    server varchar,
    title varchar,
    player_name varchar,
    purpose varchar,
    vc_use varchar,
    device varchar,
    comment varchar,
    created_at timestamp,
    user_data map<varchar,varchar>,
    tags list<varchar>,
    self_tags list<varchar>,
    play_time list<varchar>,
    delete_key varchar,
    image_path varchar,
    PRIMARY KEY ((game_id, write_day), created_at, uuid)
);

-- AWS: CREATE TABLE IF NOT EXISTS "forum".{table}