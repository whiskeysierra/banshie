#!/bin/sh

mvn clean dependency:copy-dependencies

java -jar bin/felix.jar
