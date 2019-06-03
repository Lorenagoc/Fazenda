#!/bin/bash

find -name "*.java" >> makefile.txt
javac @makefile.txt