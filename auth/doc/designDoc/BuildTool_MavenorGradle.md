# ビルドツールにGradleとMavenどちらを選択するか
## 変更履歴
| 日付 | 変更内容 |
| ---- | ---- |
| 2024/5/29 | 初版 |

## 概要
本PJのローカル開発環境は、WindowsPC + WSL + VSCodeだった。  
依存する外部ライブラリをローカルで認識させる際、Gradleだとうまくいかなかった。  

## 思考の背景
Javaプロジェクトのビルドツールについて、ビルド速度の面などから当初Gradleを利用する想定だった。  
  
WSLにGradleプロジェクトを作成＋gradle.buildに依存関係を追加＋ビルド実行すると、ビルドは成功する一方で、ローカル環境のJavaファイルでは外部ライブラリの名前が解決できない状態となった。

## 選択肢と選択理由
1. Gradle利用 【×】  
ビルド速度面などでメリットがあったが、WSL環境+VSCodeでは利用できず断念。
WSLにGradleが入っていることや、PATHへの追加等、ネット上で見つかる対策は打ってみたが、改善されなかった  

1. Maven利用 【○】  
Gradleが利用できないことでMavenほぼ一択となった。  
特段詰まることなく利用開始できた。

## 学び
IDEだと気にしなかったこともVS Codeとなると気にしないといけない。  
とはいえ、VS Codeでいろんな開発が集約できるメリットは大きいよね。