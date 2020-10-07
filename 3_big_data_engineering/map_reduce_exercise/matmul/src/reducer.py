#!/usr/bin/env python
#Reduce function for computing matrix multiply A*B

import sys
import operator

# Create data structures to hold the current row/column values 

###
# (if needed; your code goes here)
###


current_key = None
values = list()

# input comes from STDIN (stream data that goes to the program)
for line in sys.stdin:
    #print(line)

    # Remove leading and trailing whitespace
    line = line.strip()
    #print(line)

    # Get key/value and split by tab
    key, inner_key, value = line.split('\t', 2)

    # Parse key/value input (your code goes here)
    #key = int(key)
    inner_key = int(inner_key)
    value = float(value)

    # If we are still on the same key...
    if key == current_key :
        #Process key/value pair
        values.append((inner_key,value))

    # Otherwise, if this is a new key...
    else:
        # If this is a new key and not the first key we've seen
        if current_key:
            values.sort(key=lambda x: x[0])
            #print(current_key)
            #print(values)
            output = 0
            for i in range(len(values)-1) :
                if values[i][0] == values[i+1][0] :
                    output = output + values[i][1] * values[i +1][1]
            #print(values)
            #compute/output result to STDOUT
            print('{},{}'.format(current_key,output))

        # Process input for new key
        current_key = key
        values = [(inner_key, value)]

#Compute/output result for the last key

if current_key :
    values.sort(key=lambda x: x[0])
    output = 0
    for i in range(len(values)-1) :
        if values[i][0] == values[i+1][0] :
            output = output + values[i][1] * values[i +1][1]
            
    #compute/output result to STDOUT
    print('{},{}'.format(current_key,output))

