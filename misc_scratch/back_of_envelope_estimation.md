# Back of the envelope estimation

# (Data Volume) Power of two 
 * Used for estimating **data volume**

# Latency numbers every programmer should know
 * https://gist.github.com/jboner/2841832
 * Memory is fast but disk is slow
 * Avoid disk seeks if possible
 * Simple compression algorithms are fast
 * Compress data before sending it over the internet if possible
 * Data centers are usually in different regions, and it takes time to send data between them.

# Availability numbers
 * High availability is the ability of a system to be continuously operational for a desirably long period of time.
 * SLA: A service level agreement (SLA) is a commonly used term for service providers. This is an agreement between you (the service provider) 
   and your customer, and this agreement formally defines the level of uptime your service will deliver.

# Common Estimations
 * QPS (Queries per second)
 * Peak QPS
 * Storage
 * Cache
 * Number of servers