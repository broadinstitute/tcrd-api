#!/usr/bin/env bash
jar=swagger-codegen-cli.jar
apiDef=openapi.yaml
rootPackage=tcrd_api
serverPackage=${rootPackage}.server
serverApiPackage=${serverPackage}.api
serverModelPackage=${serverPackage}.model
sourceFolder=src/main/scala
rm ${sourceFolder}/io/swagger/server/*
rm ${sourceFolder}/tcrd_api/server/api/*
rm ${sourceFolder}/tcrd_api/server/model/*
java -jar $jar generate -l scala-akka-http-server -i ${apiDef} --api-package ${serverApiPackage} \
    --model-package ${serverModelPackage} --invoker-package ${serverPackage}
