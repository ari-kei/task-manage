# Authサーバ
## 概要
タスク管理アプリの認証・認可を担うサーバ

## ディレクトリ構成
auth  
  ├── AuthApplication.java  
  ├── domains : ビジネスルールを記載  
  ├── drivers : DBや外部ファイルなどの外部リソースとのやり取りを記載  
  └── interfaces : 外部とのインターフェース  
    ├── authenticate : 認証関連  
    └── verify : 検証関連  