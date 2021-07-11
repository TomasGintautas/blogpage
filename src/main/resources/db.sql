-- ====================
-- === DROP ALL TABLES
-- ====================

DROP TABLE IF EXISTS article cascade;
DROP TABLE IF EXISTS blog_comment cascade;
DROP TABLE IF EXISTS blog_user cascade;

-- ===============================================
-- === CREATE BLOG_USER TABLE AND GRANT PRIVILEGES
-- ===============================================

CREATE TABLE blog_user
(
    id          BIGSERIAL PRIMARY KEY   NOT NULL,
    created_at  TIMESTAMP DEFAULT now() NOT NULL,
    username    VARCHAR(20) UNIQUE      NOT NULL,
    password    VARCHAR(30)             NOT NULL,
    access      VARCHAR(10)             NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE blog_user TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE blog_user_id_seq TO blog_user;

-- =============================================
-- === CREATE ARTICLE TABLE AND GRANT PRIVILEGES
-- =============================================

CREATE TABLE article
(
    id              BIGSERIAL PRIMARY KEY   NOT NULL,
    created_at      TIMESTAMP DEFAULT now() NOT NULL,
    title           VARCHAR(255)            NOT NULL,
    text            TEXT                    NOT NULL,
    image           VARCHAR(255)            NOT NULL,
    blog_user_id    BIGINT                  NOT NULL,
    CONSTRAINT fk_blog_user_id FOREIGN KEY (blog_user_id) REFERENCES blog_user(id)
);
GRANT ALL PRIVILEGES ON TABLE article TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE article_id_seq TO blog_user;

-- ==================================================
-- === CREATE BLOG_COMMENT TABLE AND GRANT PRIVILEGES
-- ==================================================

CREATE TABLE blog_comment
(
    id              BIGSERIAL PRIMARY KEY   NOT NULL,
    created_at      TIMESTAMP DEFAULT now() NOT NULL,
    text            VARCHAR(255)            NOT NULL,
    article_id      BIGINT                  NOT NULL,
    blog_user_id    BIGINT                  NOT NULL,

    CONSTRAINT fk_article_id FOREIGN KEY (article_id) REFERENCES article(id),
    CONSTRAINT fk_blog_user_id FOREIGN KEY (blog_user_id) REFERENCES blog_user(id)
);

GRANT ALL PRIVILEGES ON TABLE blog_comment TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE blog_comment_id_seq TO blog_user;
