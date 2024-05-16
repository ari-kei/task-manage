# Authサーバ
## 概要
タスク管理アプリの認証・認可を担うサーバ

## ディレクトリ構成
auth
  ├── AuthApplication.java
  ├── domain : ビジネスルールを記載
  ├── driver : DBや外部ファイルなどの外部リソースとのやり取りを記載
  └── interface : 外部とのインターフェース
    ├── authenticator : 認証関連
    └── verifier : 検証関連