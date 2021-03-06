#!/bin/bash

echo "You are about to create a new module.";
echo -n "Name: ";
read name;

cd $(dirname $0)

mkdir ${name};
cd ${name};
mkdir -p src/{main,test}/{java,resources}
mkdir -p src/{main,test}/java/org/whiskeysierra/banshie

echo "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">
    <parent>
        <groupId>org.whiskeysierra.banshie</groupId>
        <artifactId>banshie-parent</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../banshie-parent</relativePath>
    </parent>
    <name>Banshie </name>
    <description>

    </description>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>${name}</artifactId>
    <packaging>bundle</packaging>
    <dependencies>
        <dependency>
            <groupId>org.whiskeysierra.banshie</groupId>
            <artifactId>banshie-api</artifactId>
            <version>\${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.felix</groupId>
                <artifactId>maven-bundle-plugin</artifactId>
                <configuration>
                    <instructions>
                        <Bundle-SymbolicName>\${project.artifactId}</Bundle-SymbolicName>
                    </instructions>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>" > pom.xml