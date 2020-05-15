#!/usr/bin/env bash

# NOTE: Requires the junit and hamcrest JAR files to be in the current directory!

javac -cp .:asm-all-5.2.jar CFG.java
javac -cp .:asm-all-5.2.jar:junit-4.13.jar:hamcrest-core-1.3.jar CFGTest.java

java -cp .:asm-all-5.2.jar:junit-4.13.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore CFGTest
