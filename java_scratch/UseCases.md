
#Download DataSet from https://datasets.imdbws.com/

#title.ratings.tsv
tconst (TitleId)
Rating
NumOfVotes

#title.akas.tsv (32 million rows)
tconst (TitleId)
Ordering
Title (Multi-Lingual)
Region
language
types
attributes
isOriginalTitle

#name.basics.tsv
nconst
primaryName
birthYear
deathYear
primaryProfession
knownForTitles (List of TitleIds)

#title.crew.tsv
tconst (TitleId)
directors (List of nconst)
writers (Blank or )

#title.episode.tsv
tconst (TitleId)
parentTconst (TitleId)
seasonNumber
episodeNumber

#title.principals.tsv
tconst (TitleId)
Ordering
nconst (Name)
Category
Job
Characters (Array)

#Tasks
[x] Create separate interface for each of the above files. 
* For a given movie, get all the data - name, characters,
* For a given episode, get all the data - name, characters, seasons, etc. 
* Get top 10 movies by rating of all time
* Get top 10 movies by rating in a given year
* Get all movies by an actor
* Get all movies by a director
* Get all movies common between an actor and director
* Get all common movies of a given set of actors
* Use Json format for data interchange for now.
* Get total number of titles