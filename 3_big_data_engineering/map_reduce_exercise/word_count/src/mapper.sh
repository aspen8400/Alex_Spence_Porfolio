#!/bin/bash

# the Hadoop-streaming environment does not read the standard bash startup files,
# so we must use a wrapper to explicitly set up the modules environment and load 
# the relevant modules

. /etc/profile.d/modules.sh
module load python/gnu/3.6.5
src/mapper.py

