# Node JS notes

## Blocking I/O
* Blocks execution of thread until I/O completes
* I/O blocking could happen for database, disk, RAM, network

## Non-Blocking I/O
* Polling the resource until data is available in a loop
    * This is called busy-waiting

## Event De-multiplexing
*  Synchronous event demultiplexer
   * Collects and queues the I/O events that come from a set of watched resources and block until new events are available to process.
   * Each event returned by the event demultiplexer is processed. At this point, the resource associated with each event is guaranteed to be ready to read
     and to not block during the operation. When all the events are processed, the flow will block again on the event demultiplexer until new events are again
     available to be processed. This is called the event loop.

## Reactor pattern
* Have a handler associated with each I/O operation which will be invoked as soon as event is produced and proceesed by event loop
* The application generates a new I/O operation by submitting a request to the Event Demultiplexer.
* The application also specifies a handler, which will be invoked when the operation completes.
* When a set of I/O operations completes, the Event Demultiplexer pushes the
  new events into the Event Queue.
* At this point, the Event Loop iterates over the items of the Event Queue.
* For each event, the associated handler is invoked.
* The handler, which is part of the application code, will give back the
  control to the Event Loop when its execution completes

## Notes
* Each operating system has its own interface for the Event Demultiplexer
  * epoll on Linux
  * kqueue on Mac OS X
  * I/O Completion Port API (IOCP) on Windows
* Node uses V8 chrome engine