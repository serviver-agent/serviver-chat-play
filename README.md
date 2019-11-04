# serviver-chat-play

## ツール

* `sbt` (必須)
* `docker-compose` (必須)
* `httpie` (下記の動作確認で使ってるよ)

## 環境開発

```
$ cd docker/local-dev
$ docker-compose up
```

```
$ sbt
[serviver-chat-play] $ run
```

```
$ http get localhost:9000
$ http get localhost:9000/health
```


## API

最新の情報は下記らへんを参照

* https://github.com/serviver-agent/serviver-chat-play/blob/master/conf/routes
* https://github.com/serviver-agent/serviver-chat-play/tree/master/application/src/main/scala/controllers


## テスト

```
$ sbt
[serviver-chat-play] $ test
```

```
[info] UserInfosTableSpec:
[info] - should select
[info] - should insert
[info] - should update
[info] - should delete
[info] ChatChannelsTableSpec:
[info] - should select
[info] - should insert
[info] - should update
[info] - should delete
[info] UserAuthsTableSpec:
[info] - should select
[info] - should insert
[info] - should update
[info] - should delete
[info] UserTokensTableSpec:
[info] - should select
[info] - should insert
[info] - should update
[info] - should delete
[info] Run completed in 2 seconds, 422 milliseconds.
[info] Total number of tests run: 16
[info] Suites: completed 4, aborted 0
[info] Tests: succeeded 16, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.

[info] HomeControllerSpec:
[info] HomeController GET
[info] - should render the index page from a new instance of controller
[info] - should render the index page from the application
[info] - should render the index page from the router *** FAILED ***
[info]   404 was not equal to 200 (HomeControllerSpec.scala:40)
[info] ScalaTest
[info] Run completed in 5 seconds, 765 milliseconds.
[info] Total number of tests run: 3
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 2, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
```

マルチプロジェクト構成にしてから
`application/src/test/scala/HomeControllerSpec.scala` が
`conf/routes` を読んでくれなくなったので要修正
ただルートプロジェクトで `run` をすると読み込んでくれる。
