# 初回設定
以下のツールをインストールしておくこと。
```cmd
git
corretto21jdk
maven
docker-desktop
```

# 起動手順
下記コマンドを実行し、DBサーバ(Docker)を起動する。
```cmd
docker-compose up -d
```

※DBサーバを停止する際は下記コマンドを実行
```cmd
docker-compose down -v
```

下記コマンドを実行し、バックエンドの開発サーバを起動する。

```cmd
cd 【クローンしたリポジトリのルートフォルダ】
./mvnw compile quarkus:dev
```

# 各種パス
## ベースURL
```
http://localhost:8080
```

## ユーザー検索
### エンドポイント
```
GET /user
```
### クエリパラメータ
| パラメータ   | 必須  | 説明                    |
| ------- | --- | --------------------- |
| `id` | No | ユーザIDの検索条件。完全一致検索。`userName`と併用した場合、AND検索になる。 |
| `userName` | No | ユーザ名の検索条件。ユーザ名(漢字/カナ)いずれかと部分一致するデータを抽出する。`id`と併用した場合、AND検索になる。 |
| `_sort` | No | 並べ替え条件。(`asc`：昇順(デフォルト) `desc`：降順) |
| `_order` | No | 並べ替え条件を適用する項目。(`id`：ユーザID(デフォルト) `userName`：ユーザ名(漢字) `userNameKana`：ユーザ名(カナ))
| `_start`  | No | 取得するデータの開始位置（デフォルト: 0）   |
| `_limit` | No | 取得データの件数（デフォルト: 10） |

## ユーザ登録
### エンドポイント
```
POST /user
```

## リクエストボディ (例)
```
Content-Type: application/json
{
  "userName": "山田太郎",
  "userNameKana": "ヤマダタロウ"
}
```

## ユーザ更新
### エンドポイント
```
PUT /user/【ユーザID】
```

## リクエストボディ (例)
```
Content-Type: application/json
{
  "userName": "山田太郎",
  "userNameKana": "ヤマダタロウ"
}
```

## ユーザ削除
### エンドポイント
```
DELETE /user/【ユーザID】
```