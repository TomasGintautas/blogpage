-- ====================
-- === DROP ALL TABLES
-- ====================

DROP TABLE IF EXISTS article cascade;
DROP TABLE IF EXISTS blog_comment cascade;
DROP TABLE IF EXISTS blog_user cascade;
DROP TABLE IF EXISTS role cascade;
DROP TABLE IF EXISTS drink_category cascade;
DROP TABLE IF EXISTS blog_user_role cascade;

-- ===============================================
-- === CREATE BLOG_USER TABLE AND GRANT PRIVILEGES
-- ===============================================

CREATE TABLE blog_user
(
    id          BIGSERIAL PRIMARY KEY   NOT NULL,
    created_at  TIMESTAMP DEFAULT now() NOT NULL,
    username    VARCHAR(20) UNIQUE      NOT NULL,
    password    VARCHAR(30)             NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE blog_user TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE blog_user_id_seq TO blog_user;

-- ====================================================
-- === CREATE DRINK_CATEGORY TABLE AND GRANT PRIVILEGES
-- ====================================================

CREATE TABLE drink_category
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    category_name VARCHAR(255)          NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE drink_category TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE drink_category_id_seq TO blog_user;

-- ==========================================
-- === CREATE ROLE TABLE AND GRANT PRIVILEGES
-- ==========================================

CREATE TABLE role
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    role_name VARCHAR(255) UNIQUE   NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE role TO blog_user;
GRANT USAGE, SELECT ON SEQUENCE role_id_seq TO blog_user;

-- ==========================================
-- === CREATE ROLE TABLE AND GRANT PRIVILEGES
-- ==========================================

CREATE TABLE blog_user_role
(
    blog_user_id    BIGINT REFERENCES blog_user(id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id         BIGINT REFERENCES role(id) ON UPDATE CASCADE
);

GRANT ALL PRIVILEGES ON TABLE blog_user_role TO blog_user;

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
    drink_category_id BIGINT                NOT NULL,
    CONSTRAINT fk_blog_user_id FOREIGN KEY (blog_user_id) REFERENCES blog_user(id),
    CONSTRAINT  fk_drink_category_id FOREIGN KEY (drink_category_id) REFERENCES drink_category(id)
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

-- ==================================
-- === INSERTING VALUES INTO TABLES
-- =================================

INSERT INTO role (id, role_name)
VALUES
(1, 'ADMIN'),
(2, 'USER');

--------------

INSERT INTO drink_category (id, category_name)
VALUES
(1, 'GIN'),
(2, 'VODKA'),
(3, 'WHISKY'),
(4, 'RUM'),
(5, 'TEQUILA');

--------------

INSERT INTO blog_user (id, created_at, username, password)
VALUES
(1, CURRENT_TIMESTAMP, 'admin', 'admin');

--------------

INSERT INTO blog_user_role (blog_user_id, role_id)
VALUES
(1, 1);
--------------

INSERT INTO article (created_at, title, text, image, blog_user_id, drink_category_id)
VALUES
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1),
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1),
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1),
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1),
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1),
(CURRENT_TIMESTAMP, 'PLEASE', 'WORK', 'NO',1,1);