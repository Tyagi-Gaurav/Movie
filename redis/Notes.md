# Redis


* Redis has two different forms of persistence available for writing in-memory data to disk in a compact format. 
  * The first method is a point-in-time dump either when certain conditions are met (a number of writes in a given period) or when one of the 
  two dump-to-disk commands is called.
  * The other method uses an append-only file that writes every command that alters data in Redis to disk as it happens.
* Redis allows us to store keys that map to any one of five different data structure types
  * STRINGs
  * LISTs
    * Ordered sequence of items
  * SETs
    * Hold unique items in an unordered fashion
  * HASHes (Sorted Sets)
    * Key & Value (Scores)
    * Values are limited to floats
  * ZSETs (Sorted Sets)
    * Key & Value (Scores)
    * Values are limited to floats
    * Mapping of members to scores
* Keeping data safe and ensuring performance
  * Persistence Options
    * Snapshotting
    * Append-only file
* Redis Transactions
  * 