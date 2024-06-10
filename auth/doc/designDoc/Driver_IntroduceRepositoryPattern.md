# データモデルとドメイン固有のエンティティを分離するために選んだRepository(改)
## 変更履歴
| 日付 | 変更内容 |
| ---- | ---- |
| 2024/5/30 | 初版 |
## 概要
データベースへのアクセスとAUTHドメイン固有のロジックを明確に分けたい。  
その際にどのような設計が適切かを考えてみた。

## 思考の背景
データベースへのアクセスと、ドメイン固有のロジックを完全に分離したい。  
1つのモチベーションとして、SOLID原則の「S(単一責任の原則)」がある。それぞれのクラスの責務を明確にして、変更が影響を及ぼす範囲を切り分けたい。  

また、上記に含まれてはいるが、より具体的なモチベーションとして「データモデル設計を将来の変更をすべて見越して、完璧に設計できる」という自信は全く持ってないというものがある。  
ソフトウェアはソフトであるべきで、仕様の変更はあって然るべきである。その将来不確実性を完全に予測して(完全に予測できた場合にそれは不確実ではないが)、設計することは不可能である。  

では具体的にどのような設計とすべきかを考えようと思った。

## 選択肢と選択理由
### DAO [×]  
DAOパターンは、データアクセスとロジックの分離を目的としたパターン。  
一方で、その中心にあるのはデータモデルで、データモデルをそのまま反映したDAOがロジック部分に漏れ出てくる。  

### Repositoryパターン [△]  
DDDで出てくるRepositoryパターン。  
こちらもデータアクセスとロジックの分離を目的としているが、DAOと違う点はその中心がドメイン固有のエンティティである点。  
ドメイン層にはRepositoryインターフェースを配置し、ドメインロジックはインターフェースに依存。その実装はインフラ層で行うことで依存性逆転の法則にも従う。  
気になる点は、そのまま適用するとこちらも結果としてテーブルの構造がドメインロジックに漏れ出てくることになる。

### Repositoryパターン＋Spring bootのRepository [○]  
Repositoryパターンをそのまま適用した場合、テーブル構造がそのままロジックに漏れ出てくる。  
その懸念を払しょくするために、実際のデータベースアクセスをSpring bootのRepositoryに任せることにした。  
今回のモチベーションは「データモデリングの変更をドメインロジックに影響させたくない」というもの。  
Repositoryパターンで利用する構造はドメイン固有のエンティティに依存し、データアクセスはSpring bootのRepository、Entityに依存することで、このことを実現させた。

### 総論
**認証というドメインにおいては少々冗長に見えたが、ドメイン固有のエンティティとデータ構造を完全に分離するという点で応用できる結論だと思料**

## 学び
DDDとしてのRepositoryパターンとSpring bootのRepositoryの違いについて、自分なりに解釈するいい機会となった。  
また、既知のDAOも選択肢に含めたことで、何を中心と考える設計パターンなのかという観点に着目できたのもいい機会の一つとなった。