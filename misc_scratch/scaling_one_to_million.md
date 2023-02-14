## Scaling from Zero to Million Users
* Separating web/mobile traffic (web tier) and database (data tier) servers allows them to be scaled independently
* Vertical Scaling vs Horizontal Scaling
  * When traffic is low, vertical scaling is a great option, and the simplicity of vertical scaling is its main advantage.
  * Vertical scaling has a hard limit. It is impossible to add unlimited CPU and memory to a single server.
  * Vertical scaling does not have failover and redundancy
* Load Balancer
  * Evenly distributes incoming traffic among webservers
* Database Replication
  * Master/Slave
    * A master database generally only supports write operations
    * A slave database gets copies of the data from the master database and only supports read operations
    * Most applications require a much higher ratio of reads to writes; thus, the number of slave databases in a system is usually larger 
    than the number of master databases.
    * Advantages
      * Better performance as read operations are distributed across a number of machines.
      * Reliability
      * High availability
    * In case of disaster
      * If only one slave database is available and it goes offline, read operations will be directed to the master database temporarily. 
      * If the master database goes offline, a slave database will be promoted to be the new master. 
      * A new slave database will replace the old one for data replication immediately.
      * In production systems, promoting a new master is more complicated as the data in a slave database might not be up to date. 
* Caching
  * Decide when to use the cache
  * Expiration Policy
  * Consistency
  * Mitigate Failures 
    * Should not be SPOF
  * Eviction Policy
    * Once the cache is full, any requests to add items to the cache might cause existing items to be removed. Eg. LRU, LFU, FIFO etc.
* CDN (Content Delivery Network)
  * Considerations to use CDNs
    * Cost: These are run by third-party providers, and you get charged for data transfers in and out of CDN. 
    * Setting appropriate cache expiry
