DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_name_kana VARCHAR(100) NOT NULL
);

INSERT INTO users (user_name, user_name_kana) VALUES
('山田太郎', 'ヤマダタロウ'),
('佐藤花子', 'サトウハナコ'),
('鈴木一郎', 'スズキイチロウ'),
('高橋美咲', 'タカハシミサキ'),
('田中健二', 'タナカケンジ'),
('伊藤裕子', 'イトウユウコ'),
('渡辺大輔', 'ワタナベダイスケ'),
('中村彩花', 'ナカムラアヤカ'),
('小林健太', 'コバヤシケンタ'),
('加藤真由美', 'カトウマユミ');