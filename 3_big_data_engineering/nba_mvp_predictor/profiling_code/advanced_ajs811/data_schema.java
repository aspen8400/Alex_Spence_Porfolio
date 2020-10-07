//Results of data profiling
//Numeric columns
stat    num_records     min     max     range   avg
ast	5404	        1.2	48.7	47.0	15.0
drr	5404	        0.0	38.0	38.0	14.0
ewa	5404	        -7.0	32.3	39.0	3.0
gp	5404	        17.0	85.0	68.0	66.0
index	5404	        1.0	5404.0	5403.0	2702.0
mpg	5404	        7.0	43.1	36.0	24.0
orr	5404	        0.0	22.0	22.0	5.0
per	5404	        0.0	31.76	31.0	14.0
rank	5404	        1.0	366.0	365.0	169.0
rebr	5404	        0.0	26.7	26.0	10.0
season	5404	        2003.0	2018.0	15.0	2010.0
to	5404	        2.0	29.6	27.0	11.0
ts%	5404	        0.338	0.725	0.0	0.0
usg	5404	        0.0	42.5	42.0	18.0
va	5404	        -208.7	969.2	1177.0	99.0

//String columns
stat    num_records     unique_values
player	5404	        1087
team	5404	        303

//Note: I did not include maximum string size here because it's irrelevant for analysis.  The length of a name or team name has no impact on player performance.

//Data Schema
//rank
data type:      int
description:    player rank based on per statistic by player and season.

//gp
data type:      int
description:    games played by player and season.

//mpg
data type:      Double
description:    minutes per game played by player and season.

//ts%
data type:      Double
description:    true shooting percentage by player and season.
                factors in the value of three-point shots, free throws, and two-point attempts
                = Points/ [2*(Field Goals Attempted+0.44*Free Throws Attempted)]
                https://stats.nba.com/help/glossary/#tspct            

//ast
data type:      Double
description:    assist ratio by player and season.
                assists expressed as a percentage of players uses
                = AST*100/(FGA+[.44*FTA]+AST+TOs)
                https://hoopshabit.com/2013/08/18/stat-central-understanding-strengths-shortcomings-of-assist-rate-metrics/

//to
data type:      Double  
description:    turnover ratio by player and season.
                Percentage of plays that end in a player or teams turnover
                https://stats.nba.com/help/glossary/#tovpct
                = 100 * TOV / (FGA + 0.44 * FTA + TOV)

//usg
data type:      Double
description:    usage rate by player and season.
                The percentage of team plays used by a player when he is on the floor.
                https://stats.nba.com/help/glossary/#usgpct 
                =  100 * ((FGA + 0.44 * FTA + TOV) * (Tm MP / 5)) / (MP * (Tm FGA + 0.44 * Tm FTA + Tm TOV))

//orr
data type:      Double
description:    offensive rebound rate by player and season.
                percentage of available offensive rebounds a player grabbed while on the floor
                https://stats.nba.com/help/glossary/#orebpct
                = 100 * (ORB * (Tm MP / 5)) / (MP * (Tm ORB + Opp DRB))

//drr
data type:      Double
description:    defensive rebound rate by by player and season.
                percentage of available defensive rebounds a player grabbed while on the floor
                https://stats.nba.com/help/glossary/#drebpct
                = 100 * (DRB * (Tm MP / 5)) / (MP * (Tm DRB + Opp ORB) 

//rebr
data type:      Double
description:    rebound rate by player and season.
                percentage of available rebounds a player grabbed while on the floor
                https://stats.nba.com/help/glossary/#rebpct
                = 100 * (TRB * (Tm MP / 5)) / (MP * (Tm TRB + Opp TRB))

//per
data type:      Double
description:    player efficiency rating by player and season.
                https://www.basketball-reference.com/about/per.html 

//va
data type:      Double
description:    value added by player and season.
                = ([Minutes * (PER - PRL)] / 67). 
                PRL (Position Replacement Level) = 11.5 for power forwards, 11.0 for point guards, 10.6 for centers, 10.5 for shooting guards and small forwards
                http://bbs.clutchfans.net/index.php?threads/espn-john-hollinger-reveals-new-statistics-va-and-ewa.165308/       

//ewa
data type:      Double
description:    estimated wins added by player and season.
                = Value Added divided by 30
                http://bbs.clutchfans.net/index.php?threads/espn-john-hollinger-reveals-new-statistics-va-and-ewa.165308/ 

//player
data type:      String
description:    player name.

//team
data type:      String
description:    team of player listed. 

//season
data type:      Int
description:    season. 


Note:   unless otherwise noted, all formulas are referenced from basketball-reference:
        https://www.basketball-reference.com/about/glossary.html 



