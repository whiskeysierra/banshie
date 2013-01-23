#!/bin/sh

mvn clean dependency:copy-dependencies

java -Dorg.osgi.framework.storage.clean=onFirstInit -jar bin/felix.jar
