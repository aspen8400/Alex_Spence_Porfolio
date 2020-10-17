#!/usr/bin/env python
#Reduce function for computing matrix multiply A*B

import sys
import operator
from statistics import mean

# Create data structures to hold the current values 

current_artist = None
years = list()
durs = list()
terms = list()
master = list()
years_calc = list()
durs_calc = list()
terms_calc = list()

# input comes from STDIN (stream data that goes to the program)
for line in sys.stdin:
    #print(line)

    # Remove leading and trailing whitespace
    line = line.strip()
    #print(line)

    # Get key/values and split by tab
    artist_id, year, dur, term = line.split('\t', 3)
    if year == 'nan' :
        pass
    else :
        year = int(year)
        dur = float(dur)

    # If we are still on the same key...
    if current_artist == artist_id :
        if year == 'nan' :
            terms.append(term)
        elif term == 'nan' :
            years.append(year)
            durs.append(dur)

    # Otherwise, if this is a new artist_id...
    else:
        # If there are years/durations/terms for this artist_id, generate calc lists
        if len(years) > 1 and len(durs) > 1 and terms  :
            for i in range(len(durs)-1) :
                for trm in terms :
                    years_calc.append(years[i])
                    durs_calc.append(durs[i])
                    terms_calc.append(trm)
            #print(years_calc)
            #print(durs_calc)
            #print(terms_calc)
            max_year = max(years_calc)
            avg_dur = mean(durs_calc)
            count_term = len(terms_calc)
            print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
        #IF there are years/durations, but no terms for this artist_id, perform aggregations
        elif len(years) == 1 and len(durs) ==1 and len(terms) > 1 :
            max_year = year
            avg_dur = dur 
            count_term = len(terms)
            print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
        elif len(years) ==1 and len(durs) ==1 and len(terms) ==1 :
            max_year = year
            avg_dur = dur
            count_term = 1
            print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
        elif len(years) == 1 and len(durs) ==1 :
            max_year = year
            avg_dur = dur
            count_term = 0
            print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))   

        #get all lists ready after calculation or artist_id in artist_term, but not tracks
        years_calc = []
        durs_calc = []
        terms_calc = []
        terms = []
        years = []
        durs = []
        if year == 'nan' :
            terms.append(term)
        if term == 'nan' :
            years.append(year)
            durs.append(dur)
        current_artist = artist_id

#Compute/output result for the last key

# If there are years/durations/terms for this artist_id, generate calc lists
        # If there are years/durations/terms for this artist_id, generate calc lists
        # If there are years/durations/terms for this artist_id, generate calc lists
if len(years) > 1 and len(durs) > 1 and terms  :
    for i in range(len(durs)-1) :
        for trm in terms :
            years_calc.append(years[i])
            durs_calc.append(durs[i])
            terms_calc.append(trm)
    #print(years_calc)
    #print(durs_calc)
    #print(terms_calc)
    max_year = max(years_calc)
    avg_dur = mean(durs_calc)
    count_term = len(terms_calc)
    print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
#IF there are years/durations, but no terms for this artist_id, perform aggregations
elif len(years) == 1 and len(durs) ==1 and len(terms) > 1 :
    max_year = year
    avg_dur = dur 
    count_term = len(terms)
    print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
elif len(years) ==1 and len(durs) ==1 and len(terms) ==1 :
    max_year = year
    avg_dur = dur
    count_term = 1
    print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term))
elif len(years) == 1 and len(durs) ==1 :
    max_year = year
    avg_dur = dur
    count_term = 0
    print('{},{},{},{}'.format(artist_id, max_year, avg_dur, count_term)) 

