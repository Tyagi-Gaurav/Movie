# Rate Limiting
## High Level Diagram
https://drive.google.com/file/d/1NIxbBfd7jkmLrj2mZa6BWrOH7v6Z10Ib/view?usp=sharing

## Factors to consider
* Client is an unreliable place to enforce rate limiting because client requests could be easily be forged by malicious actors. 
* API gateway is a fully managed service that supports 
  * Rate limiting
  * SSL termination
  * Authentication
  * IP whitelisting
  * Servicing static content, etc.
* Popular Rate Limiting algorithms
  * Token Bucket
    * A token bucket is a container that has pre-defined capacity. 
    * Tokens are put in the bucket at preset rates periodically.
    * Once the bucket is full, no more tokens are added.
    * Each request consumes one token
    * When the request arrives, we check if there are enough tokens in the bucket.
    * Requires 2 parameters
      * Number of buckets
      * Refill rate
  * Leaking Bucket
    * Implemented with a FIFO queue
    * When a request arrives, the system checks if the queue is full? If not, it is queued, otherwise, it is dropped.
    * Takes the following parameters
      * Queue or Bucket size
      * Outflow rate
        * How many requests can be processed at a fixed rate, usually in seconds.
  * Fixed Window counter
    * The algorithm divides the timeline into fix-sized time windows and assign a counter for each window.
    * Each requests increments the counter by one.
    * Once the counter reaches the pre-defined threshold, new requests are dropped until a new time window starts.
    * A major problem with this algorithm
      * A burst of traffic at the edges of time windows could cause more requests than allowed quota to go through.
      * Eg. 
        * We want 10 requests to go in a minute, and between 1:00:30 and 1:01:00 if there are 5 requests, and then again 5 requests between 
        1:01:00 and 1:01:30. 
        * If the quota resets at the human friendly round minute, then for a one-minute window between 1:00:30 and 1:01:30 we've allowed 10 requests to go through.  
  * Sliding Window log
    * It fixes the problems of fixed window counter
    * It keeps track of request timestamps usually in cache, such as sorted sets of Redis
    * When a new request comes in, remove all outdated timestamps.
      * Outdated timestamps are those that are older than current time window
    * Add timestamp of the new request to the log
    * If the log size is the same or lower than the allowed count, request is accepted, otherwise its rejected.
    * Example:
      * Assume that we're limiting to 2 requests a minute
      * A request arrives at 1:00:01. The log is empty, so this request is allowed.
      * A new request arrives at 1:00:30, the timestamp 1:00:30 is inserted into the log. 
        * After insertion the log size is 2, not larger than allowed count, thus the request is allowed.
      * A new request arrives at 1:00:50, and the timestamp is inserted into the log. 
        * After insertion, the log size is 3, larger than the allowed size 2, therefore the request is rejected even though the 
        timestamp remains in the log.
      * A new request arrives at 1:01:40.
        * Requests in the range [1:00:40, 1:01:40] are within the latest time frame, but requests sent before 1:00:40 are outdated.
  * Sliding Window counter
    * Combines fixed window counter and sliding window log

## Design
* Where do we store the rules
  * Rules can be configured in a yaml file and stored on disk
* What happens when rule is exceeded?
  * APIs return a HTTP response code 429 to the client.
* Rate limiter in a distributed environment
  * Race conditions
    * Locks should be used to solved this. Options are,
      * Lua scripts
      * Sorted Sets
  * Synchronization issues
    * We could use centralized data stores like Redis
* Monitoring
  * How do we know rate limiting algorithm is effective?
  * Measure total number of requests received
  * Measure total number of requests allowed
  * Measure total number of requests dropped

    