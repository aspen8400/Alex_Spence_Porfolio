#!/usr/bin/env python
# -*- encoding: utf-8 -*-

# USAGE:
#   python Lab1.py Sample_Song_Dataset.db

import sys
import sqlite3
import pandas as pd
import string
from collections import Counter
import timeit 
import time

def print_full(df):
    """ Fully print a Pandas DataFrame

    Inputs:
        df (dataframe): Pandas DataFrame

    Outputs:
        None. Prints DataFrame to terminal display.

    """
    pd.set_option('display.max_rows', len(df))
    pd.set_option('display.width', 120)
    print(df)
    pd.reset_option('display.max_rows')


# The database file should be given as the first argument on the command line
db_file = db_file = "/Users/alex/Dropbox/Grad_School/NYU/GitHub/DS-GA-1004-labs/lab-1-sqlite-aspen8400/Sample_Song_Dataset.db" #sys.argv[1]

# We connect to the database using 
with sqlite3.connect(db_file) as conn:
    # We use a "cursor" to mark our place in the database.
    # We could use multiple cursors to keep track of multiple
    # queries simultaneously.
    cursor = conn.cursor()

    # This query counts the number of tracks from the year 1998
    # year = ('1998',)
    # cursor.execute('SELECT count(*) FROM tracks WHERE year=?', year)

    # Since there is no grouping here, the aggregation is over all rows
    # and there will only be one output row from the query, which we can
    # print as follows:
    # print('Tracks from {}: {}'.format(year[0], cursor.fetchone()[0]))
    # The [0] bits here tell us to pull the first column out of the 'year' tuple
    # and query results, respectively.

    #1
    def one_quest(track_id) :
        cursor.execute("""  SELECT tracks.artist_id, artists.artist_name, artist_term.term 
                            FROM tracks 
                            INNER JOIN artists ON tracks.artist_id = artists.artist_id 
                            INNER JOIN artist_term ON tracks.artist_id = artist_term.artist_id 
                            WHERE tracks.track_id =?""", track_id)
        return cursor.fetchall()
    track_id_1 = ('TRMMWLD128F9301BF2',)
    track_one = one_quest(track_id_1)
    one_df = pd.DataFrame(data = track_one, columns = ['artist_id', 'artist_name', 'term'])
    print_full(one_df)

    # 2
    duration = (3020,)
    cursor.execute('SELECT track_id FROM tracks WHERE duration >?', duration)
    durations = cursor.fetchall()
    print("track_ids with duration > 3020 are %d \n" %(duration[0])) 
    two = pd.DataFrame(data = durations, columns = ['track_id > 3020'])
    print_full(two)

    #3 10 shortest tracks ordered by increasing duration btw 2010 and 2014 inclusive
    start_end = (2010, 2014)
    cursor.execute("""  SELECT track_id 
                        FROM tracks 
                        WHERE year>= ? AND year <= ? 
                        ORDER BY duration ASC""", start_end)
    three = cursor.fetchmany(10)
    three_df = pd.DataFrame(data = three, columns = ['shortest duration track_id btw 2010 and 2014'])
    print_full(three_df)

    #4 
    cursor.execute("""  SELECT term, COUNT(term) 
                        FROM artist_term 
                        GROUP BY term 
                        ORDER BY COUNT(term) DESC""")
    four = cursor.fetchmany(20)
    four_df = pd.DataFrame(data = four, columns = ['term', 'term count'])
    print('The twenty most frequently used terms in descending order: \n', four_df)

    # 5
    cursor.execute("""  SELECT artists.artist_name
                        FROM tracks
                        INNER JOIN artists ON tracks.artist_id = artists.artist_id
                        WHERE tracks.duration = (SELECT MAX(tracks.duration) FROM tracks) """)
    five = cursor.fetchone()
    print("Artist name assosiated with longest duration: ", five[0])

    #6
    cursor.execute('SELECT AVG(duration) FROM tracks')
    print('Average duration of all tracks: ', cursor.fetchone()[0])

    #7
    cursor.execute("""  SELECT COUNT(track_id) 
                        FROM tracks
                        LEFT JOIN artist_term ON tracks.artist_id = artist_term.artist_id 
                        WHERE artist_term.term is NULL""")
    print("# of tracks with null terms:", cursor.fetchone()[0])
    
    # 8
    min_time_one = 0
    i = 0
    for i in range(99) :
        start = time.clock()
        one_quest(track_id_1)
        finish = time.clock()
        duration = finish - start
        if min_time_one == 0 or duration < min_time_one :
            min_time_one = duration
    
    def eight_quest() :
        cursor.execute("""  CREATE INDEX eight_idx 
                            ON tracks (artist_id)""")
        return cursor.fetchone()
                        
    start = time.clock()
    eight = eight_quest
    finish = time.clock()
    time_8 = finish - start

    print("1", min_time_one,"\n", "8 ", time_8)
    if min_time_one > time_8 :
        print("Question 1 min time takes %f more seconds than question 8 " %(min_time_one - time_8))
    if time_8 > min_time_one :
        print("Question 8 min time takes %f more seconds than question 1 " %(time_8 - min_time_one))

    #9

    cursor.execute("""  BEGIN TRANSACTION """)
    cursor.execute("""  DELETE FROM tracks  
                        WHERE track_id in (SELECT track_id
                        FROM tracks 
                        INNER JOIN artist_term 
                        ON tracks.artist_id = artist_term.artist_id
                        WHERE term = ?)""",('eurovision winner',))
    cursor.execute("""  ROLLBACK TRANSACTION""")