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
    username    VARCHAR(40) UNIQUE      NOT NULL,
    password    VARCHAR(255)            NOT NULL
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

(20, CURRENT_TIMESTAMP, 'admin', 'admin'),
(21, CURRENT_TIMESTAMP, 'user', 'user');

--------------

INSERT INTO blog_user_role (blog_user_id, role_id)
VALUES
(20, 1),
(20, 2),
(21, 2);

--------------

INSERT INTO article (id, created_at, title, text, image, blog_user_id, drink_category_id)
VALUES
(100, CURRENT_TIMESTAMP, 'Basil Smash', 'text text text text text text text text text text text text text text text text text text', 'BasilSmash.jpg',20,1),
(101, CURRENT_TIMESTAMP, 'Bramble', 'text text text text text text text text text text text text text text text text text text', 'Bramble.jpg',20,1),
(102, CURRENT_TIMESTAMP, 'Classic Gin Tonic', 'text text text text text text text text text text text text text text text text text text', 'GinTonic.jpg',20,1),
(103, CURRENT_TIMESTAMP, 'Margarita', 'text text text text text text text text text text text text text text text text text text', 'Margarita.jpg',20,5),
(104, CURRENT_TIMESTAMP, 'Mint Cucumber Vodka', 'text text text text text text text text text text text text text text text text text text', 'MintVodka.jpg',20,2),
(105, CURRENT_TIMESTAMP, 'Old Fashioned', 'text text text text text text text text text text text text text text text text text text', 'OldFashioned.jpg',20,3),
(106, CURRENT_TIMESTAMP, 'Screwdriver', 'text text text text text text text text text text text text text text text text text text', 'Screwdriver.jpg',20,2),
(107, CURRENT_TIMESTAMP, 'Tequila Sunrise', 'text text text text text text text text text text text text text text text text text text', 'Sunrise.jpg',20,5),
(108, CURRENT_TIMESTAMP, 'Turnbuckle', 'text text text text text text text text text text text text text text text text text text', 'Turnbuckle.jpg',20,4),
(109, CURRENT_TIMESTAMP, 'Whisky Sour', 'text text text text text text text text text text text text text text text text text text', 'WhiskySour.jpg',20,3),
(110, CURRENT_TIMESTAMP, 'Zombie', 'text text text text text text text text text text text text text text text text text text', 'Zombie.jpg',20,4);

--------------

INSERT INTO blog_comment (id, created_at, text,article_id, blog_user_id)
VALUES
(101,CURRENT_TIMESTAMP,'This is comment from admin account',100,20),
(102,CURRENT_TIMESTAMP,'This is comment from user account',100,21),
(103,CURRENT_TIMESTAMP,'This is comment from admin account',101,20),
(104,CURRENT_TIMESTAMP,'This is comment from user account',101,21),
(105,CURRENT_TIMESTAMP,'This is comment from admin account',102,20),
(106,CURRENT_TIMESTAMP,'This is comment from user account',102,21),
(107,CURRENT_TIMESTAMP,'This is comment from admin account',103,20),
(108,CURRENT_TIMESTAMP,'This is comment from user account',103,21),
(109,CURRENT_TIMESTAMP,'This is comment from admin account',104,20),
(110,CURRENT_TIMESTAMP,'This is comment from user account',104,21),
(111,CURRENT_TIMESTAMP,'This is comment from admin account',105,20),
(112,CURRENT_TIMESTAMP,'This is comment from user account',105,21),
(113,CURRENT_TIMESTAMP,'This is comment from admin account',106,20),
(114,CURRENT_TIMESTAMP,'This is comment from user account',106,21),
(115,CURRENT_TIMESTAMP,'This is comment from admin account',107,20),
(116,CURRENT_TIMESTAMP,'This is comment from user account',107,21),
(117,CURRENT_TIMESTAMP,'This is comment from admin account',108,20),
(118,CURRENT_TIMESTAMP,'This is comment from user account',108,21),
(119,CURRENT_TIMESTAMP,'This is comment from admin account',109,20),
(120,CURRENT_TIMESTAMP,'This is comment from user account',109,21),
(121,CURRENT_TIMESTAMP,'This is comment from admin account',110,20),
(122,CURRENT_TIMESTAMP,'This is comment from user account',110,21);