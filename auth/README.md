# Authサーバ
## 概要
タスク管理アプリの認証・認可を担うサーバ

## ディレクトリ構成
auth  
  ├── AuthApplication.java  
  ├── config : プロセス起動時の設定関連  
  ├── domains : ビジネスルールを記載  
  │   ├── entities：エンティティ  
  │   └── repositoryif：外部詳細とのインターフェース  
  ├── drivers : DBや外部ファイルなどの外部リソースとのやり取りを記載  
  └── interfaces : 外部とのインターフェース  
  │   └── authenticate : 認証関連  
  │   └── verify : 検証関連  