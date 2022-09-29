# Go Notes

## Where Go Values Live
* Non-pointer Go values stored in local variables live on go-routine stack.
* Other memory allocations go on the heap.This is called dynamic memory allocation.
* Note that escaping to the heap must also be transitive: if a reference to a Go value is written into another Go value that has already been determined to escape, that value must also escape.
* Whether a Go value escapes or not is a function of the context in which it is used and the Go compiler's escape analysis algorithm
*  To identify live memory, the GC walks the object graph starting at the program's roots, pointers that identify objects that are definitely in-use by the program. 
    * Two examples of roots are local variables and global variables. 
    * The process of walking the object graph is referred to as scanning.

* Go GC is a mark-sweep GC

## KB vs KiB, MB vs MiB, GB vs GiB
* `KB = 10^3`or 1000 bytes while `KiB = 2^10` or 1024 bytes.
* MiB - Mibibytes
* GiB - Gibibytes

## Understanding GC Cost
* GC involves only 2 resources - CPU time, Memory
* The GC's memory costs consist of live heap memory, new heap memory allocated before the mark phase, and space for metadata that, even if proportional to the previous costs, are small in comparison.
* The GC's CPU costs are modeled as a fixed cost per cycle
* In Go, deciding when the GC should start is the main parameter which the user has control over.

## GOGC
* GOGC determines the trade-off between GC CPU and memory i.e. when should GC start.
* It works by determining the target heap size after each GC cycle
    * This is used as a target value for the total heap size in the next cycle.
* The GC's goal is to finish a collection cycle before the total heap size exceeds the target heap size. 
* Total heap size is defined as the live heap size at the end of the previous cycle, plus any new heap memory allocated by the application since the previous cycle.
* `Target heap memory = Live heap + (Live heap + GC roots) * GOGC / 100`
* The bigger the target, the longer GC can wait to start
* The heap target controls GC frequency: the bigger the target, the longer the GC can wait to start another mark phase and vice versa.
* Doubling GOGC will double heap memory overheads and roughly halve GC CPU cost
* GOGC may be configured through either the GOGC environment variable (which all Go programs recognize), or through the SetGCPercent API in the runtime/debug package.
* As GOGC increases, CPU overhead decreases, but peak memory increases
* As GOGC decreases, CPU overhead increases, but peak memory decreases
* Go GC has a minimum total heap size of 4 MiB
* The memory limit may be configured either via the GOMEMLIMIT environment variable which all Go programs recognize, or through the SetMemoryLimit function available in the runtime/debug package.
* This situation, where the program fails to make reasonable progress due to constant GC cycles, is called thrashing.

## GC Latency
* The Go GC, however, is not fully stop-the-world and does most of its work concurrently with the application.

## Virtual Memory
* Physical memory is memory housed in the actual physical RAM chip in most computers. 
* Virtual memory is an abstraction over physical memory provided by the operating system to isolate programs from one another. 
    * It's also typically acceptable for programs to reserve virtual address space that doesn't map to any physical addresses at all.
