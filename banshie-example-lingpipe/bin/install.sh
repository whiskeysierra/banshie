#!/bin/sh

mvn install:install-file \
-DgroupId=com.aliasi \
-DartifactId=lingpipe \
-Dversion=4.1.0 \
-Dpackaging=jar \
-DgeneratePom=true \
-Dfile=lingpipe-4.1.0.jar