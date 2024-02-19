#!/bin/bash
# Find all Java source files and store their paths in sources.txt
find -name "*.java" > sources.txt
# Set up the classpath with the JUnit library and the source directory
export CLASSPATH=lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:classes
# Compile the Java source files listed in sources.txt and output the compiled classes to the classes directory
javac @sources.txt -d classes
