#!/bin/bash

ALLOWED_FOLDERS="US110 US111 US112"
flag=0

for d in ./*/; do
  if [ -f $d"Makefile" ] && [[ $ALLOWED_FOLDERS =~ $(basename $d) ]]; then
    cd $d
    make &> /dev/null
    if [ $? -ne 0 ]; then
      echo "Error compiling $d"
      flag=1
      cd ..
      continue
    fi
    output=$(valgrind ./prog 2>&1)
    echo "$output"
    if echo $output | grep -q "definitely lost"; then
      echo "Memory leak detected in $d"
      flag=1
    fi
    cd ..
  fi
done
if [ $flag -eq 1 ]; then
  exit 1
fi
echo "No memory leaks found in $ALLOWED_FOLDERS"
exit 0

