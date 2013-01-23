#!/bin/sh

mvn clean dependency:copy-dependencies

java \
-Dorg.osgi.framework.storage.clean=onFirstInit \
-Dorg.osgi.framework.system.packages.extra=com.sun.management \
-jar bin/felix.jar
