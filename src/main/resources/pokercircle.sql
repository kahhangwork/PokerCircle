CREATE DATABASE pokercircle;
USE pokercircle;

-- =========================================================
-- PokerCircle - MySQL Schema
-- =========================================================

-- Users
CREATE TABLE IF NOT EXISTS usr (
  usr_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(128) NOT NULL UNIQUE,
  display_name VARCHAR(16) NOT NULL UNIQUE,
  profile_picture VARCHAR(256),
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Personal Sessions
CREATE TABLE IF NOT EXISTS personal_session (
  session_id INT AUTO_INCREMENT PRIMARY KEY,
  usr_id INT NOT NULL,
  session_type VARCHAR(32) NOT NULL,
  session_stakes VARCHAR(32) NOT NULL,
  session_location VARCHAR(128),
  session_started_at DATETIME NOT NULL,
  session_ended_at DATETIME,
  buy_in_cents INT NOT NULL,
  cash_out_cents INT NOT NULL,
  notes VARCHAR(1028),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  KEY idx_personal_session_usr_id (usr_id),

  CONSTRAINT fk_personal_session_usr
    FOREIGN KEY (usr_id) REFERENCES usr(usr_id)
    ON DELETE CASCADE,

  CONSTRAINT chk_personal_buy_in_nonneg CHECK (buy_in_cents >= 0),
  CONSTRAINT chk_personal_cash_out_nonneg CHECK (cash_out_cents >= 0)
);

-- Groups
CREATE TABLE IF NOT EXISTS grp (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(64) NOT NULL UNIQUE,
  created_by INT NOT NULL,
  description VARCHAR(256),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  KEY idx_grp_created_by (created_by),

  CONSTRAINT fk_grp_created_by
    FOREIGN KEY (created_by) REFERENCES usr(usr_id)
    ON DELETE RESTRICT
);

-- Group Members
-- Note: We keep an auto ID as PK, but enforce one membership per (grp_id, usr_id)
CREATE TABLE IF NOT EXISTS grp_member (
  id INT AUTO_INCREMENT PRIMARY KEY,
  grp_id INT NOT NULL,
  usr_id INT NOT NULL,
  joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  left_at TIMESTAMP NULL,

  UNIQUE KEY uq_grp_member (grp_id, usr_id),
  KEY idx_grp_member_grp_id (grp_id),
  KEY idx_grp_member_usr_id (usr_id),

  CONSTRAINT fk_grp_member_grp
    FOREIGN KEY (grp_id) REFERENCES grp(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_grp_member_usr
    FOREIGN KEY (usr_id) REFERENCES usr(usr_id)
    ON DELETE CASCADE
);

-- Group Sessions (Session header)
CREATE TABLE IF NOT EXISTS grp_session (
  id INT AUTO_INCREMENT PRIMARY KEY,
  grp_id INT NOT NULL,
  session_type VARCHAR(32) NOT NULL,      -- 'cash' / 'tournament'
  session_stakes VARCHAR(32) NOT NULL,    -- e.g. '$1/$2' or '$50 buy-in'
  session_location VARCHAR(128),
  session_started_at DATETIME NOT NULL,
  session_ended_at DATETIME,
  notes VARCHAR(1028),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  KEY idx_grp_session_grp_id (grp_id),

  CONSTRAINT fk_grp_session_grp
    FOREIGN KEY (grp_id) REFERENCES grp(id)
    ON DELETE CASCADE
);

-- Group Session Participants (who attended)
CREATE TABLE IF NOT EXISTS grp_session_participant (
  session_id INT NOT NULL,
  member_id INT NOT NULL,  -- grp_member.id

  joined_at DATETIME NULL,
  left_at DATETIME NULL,

  PRIMARY KEY (session_id, member_id),
  KEY idx_participant_member_id (member_id),

  CONSTRAINT fk_participant_session
    FOREIGN KEY (session_id) REFERENCES grp_session(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_participant_member
    FOREIGN KEY (member_id) REFERENCES grp_member(id)
    ON DELETE RESTRICT
);

-- =========================================================
-- Cash game money movements (supports rebuys via transfers)
--
-- BUY_IN:    from_member_id = NULL, to_member_id   = player
-- CASH_OUT:  from_member_id = player, to_member_id = NULL
-- TRANSFER:  from_member_id = payer,  to_member_id = receiver
-- =========================================================
CREATE TABLE IF NOT EXISTS grp_session_txn (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  session_id INT NOT NULL,

  from_member_id INT NULL,
  to_member_id   INT NULL,

  txn_type ENUM('BUY_IN','CASH_OUT','TRANSFER') NOT NULL,
  amount_cents INT NOT NULL,
  txn_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notes VARCHAR(256) NULL,

  KEY idx_txn_session (session_id),
  KEY idx_txn_from (from_member_id),
  KEY idx_txn_to (to_member_id),

  CONSTRAINT fk_txn_session
    FOREIGN KEY (session_id) REFERENCES grp_session(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_txn_from_member
    FOREIGN KEY (from_member_id) REFERENCES grp_member(id)
    ON DELETE RESTRICT,

  CONSTRAINT fk_txn_to_member
    FOREIGN KEY (to_member_id) REFERENCES grp_member(id)
    ON DELETE RESTRICT,

  CONSTRAINT chk_txn_amount_pos CHECK (amount_cents > 0),
  CONSTRAINT chk_txn_not_same CHECK (
    from_member_id IS NULL OR to_member_id IS NULL OR from_member_id <> to_member_id
  )
);

-- DML: CRUD - INSERT, SELECT, UPDATE, DELETE

INSERT INTO usr(email, display_name, password_hash) VALUES ('fred@gmail.com', 'Fred', 'password');
INSERT INTO usr(email, display_name, password_hash) VALUES ('kah@gmail.com', 'Kah', 'anotherpassword');
INSERT INTO usr(email, display_name, password_hash, created_at, updated_at) VALUES ('mary@gmail.com', 'mary', 'mpass', NOW(), NOW());

SELECT * FROM usr;
SELECT * FROM usr WHERE usr_id = 2;