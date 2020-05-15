#!/usr/bin/env bash

rm -rf ./submission

mkdir submission

pandoc proj1_sub.md -s -o proj1_sub.pdf
mv proj1_sub.pdf submission/

mkdir submission/q1
cp q1/mod.c submission/q1/
cp q1/Mod.java submission/q1/
cp q1/mod.pl submission/q1/
cp q1/mod.py submission/q1/

mkdir submission/q2
cp q2/sll_fixed.c submission/q2/

mkdir submission/q3
cp q3/TestM.java submission/q3/

mkdir submission/q4
cp q4/CFGTest.java submission/q4/
cp q4/CFG.java submission/q4/

zip -r Caleb-Smith-smit3484.zip submission
