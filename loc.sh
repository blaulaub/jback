#!/bin/bash
find . -type d | grep 'src/main/java$' | while read DIR; do find $DIR -type f -name \*.java; done | while read FILE; do cat $FILE; done | sed 's|/\*|\n&|g;s|*/|&\n|g' | sed '/\/\*/,/*\//d' | egrep -v '^\s*(//|$)' | wc -l
