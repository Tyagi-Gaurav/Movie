# Java Virtual Machine

## Heap Generations
* Java objects are created in Heap and Heap is divided into three parts or generations for sake of garbage collection in Java.
* These are called as Young generation, Tenured or Old Generation and Perm Area of heap. 
* New Generation is further divided into three parts known as Eden space, Survivor 1 and Survivor 2 space.

## Garbage Collection

### Mark & Sweep
* Mark – this is where the garbage collector identifies which pieces of memory are in use and which aren't.
* Sweep – this step removes objects identified during the “mark” phase.
* Disadvantages
  * Since JVM has to keep track of object reference creation/deletion, this activity requires more CPU power than the original application
  * Programmers have no control over the scheduling of CPU time dedicated to freeing objects that are no longer needed

### Serial Garbage Collectors
* This GC implementation freezes all application threads when it runs
* To enable: `-XX:+UseSerialGC` 

### Parallel Garbage Collectors
* Uses multiple threads for managing heap space
* but it also freezes other application threads while performing GC
* Number of gc threads can be controlled by `-XX:ParallelGCThreads=<N>`
* Maximum pause time goal (gap [in milliseconds] between two GC) is specified with the command-line option `-XX:MaxGCPauseMillis=`
* To enable: `-XX:+UseParallelGC`

### CMS Garbage Collector
* Uses multiple garbage collector threads for garbage collection
* To enable: `-XX:+UseConcMarkSweepGC`

### G1 Garbage Collector
* Designed for applications running on multi-processor machines with large memory space.
* G1 collector partitions the heap into a set of equal-sized heap regions, each a contiguous range of virtual memory.
* G1 shows a concurrent global marking phase (i.e. phase 1, known as Marking) to determine the liveness of objects throughout the heap.
* To enable: `-XX:+UseG1GC`
* After the mark phase is complete, G1 knows which regions are mostly empty. It collects in these areas first, which usually yields a 
significant amount of free space

### Z Garbage Collector
* Scalable low latency GC
* ZGC performs all expensive work concurrently, without stopping the execution of application threads for more than 10 ms
* It uses load barriers with colored pointers to perform concurrent operations when the threads are running, and they're used to keep track of heap usage.
* It uses load barriers with colored pointers to perform concurrent operations when the threads are running, and they're used to keep track of heap usage.
* To enable: `-XX:+UseZGC`

### Epsilon GC
* Epsilon is a no-op garbage collector
* Handles memory allocation but does not implement any actual memory reclamation mechanism. 
* Once the available Java heap is exhausted, the JVM will shut down.
* Why use Epsilon GC? 
  * Performance testing
  * Memory pressure testing
  * VM interface testing
  * Extremely short lived jobs
  * Last-drop latency improvements
  * Last-drop throughput improvements