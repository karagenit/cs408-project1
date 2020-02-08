#!/usr/bin/env bash

# NOTE: Requires the junit and hamcrest JAR files to be in the current directory!

javac -cp .:junit-4.13.jar:hamcrest-core-1.3.jar TestM.java
java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestM
