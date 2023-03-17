#!/bin/bash

#javacコマンドでコンパイルする対象クラスを指定
COMPILE_TARGET_CLASS=src/main/java/com/example/batchstudy/service/Download.java
#コンパイルクラスの出力先
COMPILE_OUTPUT_DESTINATION=src/main/resources/script/do_download_java_result
#指定したクラスを出力先にコンパイル
javac $COMPILE_TARGET_CLASS -d $COMPILE_OUTPUT_DESTINATION

#jarファイルの出力先
JAR_OUTPUT_FILE=src/main/resources/script/do_download_java_result/Download.jar
#manifestファイルの指定と作成
JAR_MANIFESTFILE=src/main/resources/script/do_download_java_result/download.mf
if [[ -f $JAR_MANIFESTEFILE ]]; then
  echo "manifestファイルは既に存在しています"
else
  echo "Manifest-Version: 1.0\nMain-Class: com.example.batchstudy.service.Download\n" > $JAR_MANIFESTFILE
fi

cd src/main/resources/script/do_download_java_result

#jarファイルに圧縮するクラスの指定
JAR_TARGET_CLASS=com/example/batchstudy/service/Download.class

#jarファイルの作成
sleep 5;jar -cvfm Download.jar download.mf $JAR_TARGET_CLASS

#作成したjarファイルを実行
sleep 5;java -jar Download.jar

