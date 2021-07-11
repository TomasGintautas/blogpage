DROP TABLE IF EXISTS article cascade;
DROP TABLE IF EXISTS blog_comment;
DROP TABLE IF EXISTS blog_user;

CREATE TABLE article
(
    id              BIGSERIAL PRIMARY KEY            NOT NULL,
    created_at      TIMESTAMP                        NOT NULL,
    title           VARCHAR(255)                     NOT NULL,
    text            TEXT                             NOT NULL,
    image           VARCHAR(255)                     NOT NULL
);
GRANT ALL PRIVILEGES ON TABLE article TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE article_id_seq TO blog_user;

CREATE TABLE blog_user
(
    id          BIGSERIAL PRIMARY KEY   NOT NULL,
    created_at  TIMESTAMP               NOT NULL,
    username    VARCHAR(20)             NOT NULL,
    password    VARCHAR(30)             NOT NULL,
    access      VARCHAR(10)             NOT NULL,
    article_id  BIGINT,
    CONSTRAINT fk_article FOREIGN KEY (article_id) REFERENCES article(id)
);

GRANT ALL PRIVILEGES ON TABLE blog_user TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE blog_user_id_seq TO blog_user;


CREATE TABLE blog_comment
(
    id              BIGSERIAL PRIMARY KEY           NOT NULL,
    created_at      TIMESTAMP                       NOT NULL,
    text            VARCHAR(255)                    NOT NULL,
    article_id      BIGINT REFERENCES article (id)  NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE blog_comment TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE blog_comment_id_seq TO blog_user;
