#!/bin/bash
echo starting removing files
cd Running
rm * -r
echo remove complete
echo start copying from $1
cp /java/projects/$1/* /java/Running/ -a
echo copying complete


