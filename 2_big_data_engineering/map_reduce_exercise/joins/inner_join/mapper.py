#!/usr/bin/env python
# map function for matrix multiply

# The input tables should be contained in a folder in HDFS of the form
#   music_small/artist_term.csv
#   music_small/track.csv

# Input files are assumed to have lines of the form "col1, col2, col3, etc."

#Input arguments:
# None

import os
import sys


# are we reading from artist_term.csv or track.csv file?
READING_artist = False
READING_track = False

# Hadoop may break each input file into several small chunks for processing
# and the streaming mode only shows us one row (line of text) at a time.
#
# If we want to know what file the input data is coming from, this is
# stored in the environment variable `mapreduce_map_input_file`:
if 'artist_term' in os.environ['mapreduce_map_input_file']:
    READING_artist = True
elif 'track' in os.environ['mapreduce_map_input_file']:
    READING_track = True
else:
    raise RuntimeError('Could not determine input file!')

# input comes from STDIN (stream data that goes to the program)
for line in sys.stdin:

    # Remove leading and trailing whitespace
    line = line.strip()

    # Split line into array of entry data
    entry = line.split(",")

    # Set row, column, and value for this entry

    # If this is an entry in matrix A...
    if READING_artist:
        # Generate the necessary key-value pairs
        # key = artist_id
        artist_id  = entry[0]
        track_id = 'nan'
        term = entry[1]
        print('{}\t{}\t{}'.format(artist_id, track_id, term))
        
    # Otherwise, if this is an entry in matrix B...
    else:
        # Generate the necessary key-value pairs
        # key = artist_id
        artist_id = entry[5]
        track_id = entry[0]
        term = 'nan'
        print('{}\t{}\t{}'.format(artist_id, track_id, term))
