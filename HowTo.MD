# How do I?

#### How do I connect to MySQL locally in docker container?
```
$ docker exec -it local.mysql bash
$ mysql -h localhost -p -u root
(pwd: password)
mysql> use movieMetadata
```

#### How do I generate GRPC stubs for the proto file?
```
./gradlew grpcService:generateProto
```