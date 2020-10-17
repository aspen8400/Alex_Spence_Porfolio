#!/usr/bin/env python
#Reduce function for computing matrix multiply A*B

import sys
import operator

# Create data structures to hold the current values 


current_artist = None
track_ids = list()
terms = list()

# input comes from STDIN (stream data that goes to the program)
for line in sys.stdin:
    #print(line)

    # Remove leading and trailing whitespace
    line = line.strip()
    #print(line)

    # Get key/values and split by tab
    artist_id, track_id, term = line.split('\t', 2)

    # If we are still on the same key...
    if current_artist == artist_id :
        if track_id == 'nan' and term != 'nan' :
            terms.append(term)
        if term == 'nan' and track_id != 'nan' :
            track_ids.append(track_id)

    # Otherwise, if this is a new key...
    else:
        # If there are matches, print to STDOUT
        if len(terms) > 0 and len(track_ids) > 0 :
            terms.sort()
            track_ids.sort()
            for trm in terms :
                for trk_id in track_ids :
                    print('{},{},{}'.format(current_artist, trk_id, trm))
        # If there are no matches, clear lists and start new lists for new artist
        track_ids = []
        terms = []
        current_artist = artist_id
        if track_id == 'nan' and term != 'nan' :
            terms.append(term)
        if term == 'nan' and track_id != 'nan' :
            track_ids.append(track_id)

#Compute/output result for the last key

if len(terms) > 0 and len(track_ids) > 0 :
            terms.sort()
            track_ids.sort()
            for trm in terms :
                for trk_id in track_ids :
                    print('{},{},{}'.format(current_artist, trk_id, trm))

