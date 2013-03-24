Banshie
=======

A benchmarking framework to evaluate information extraction quality.

[![Build Status](https://travis-ci.org/whiskeysierra/banshie.png?branch=master)](http://travis-ci.org/whiskeysierra/banshie)

## Requirements
 - Java 1.6 or higher
 - Ant
 - Maven 3
 - LaTeX (optional)

## Building

Run a complete build with the following command

    ant compile
    
Each module is build using its individual Maven POM. If you want to build a single module, just change to the particular directory and run the maven command.

    cd <directory>
    mvn compile
    
Please note, that all required dependencies need to be installed in the local maven repository first. You can install all modules in the required order by running the following command in the root directory:

    ant install
