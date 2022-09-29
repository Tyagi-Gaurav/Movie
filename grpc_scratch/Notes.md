# GRPC Notes

* When you develop a gRPC application the first thing that you do is define a service interface.
* On the server side, the server implements that service definition and runs a gRPC server to handle client calls
* As the wire transport pro‐ tocol, gRPC uses HTTP/2, which is a high-performance binary message protocol with support for bidirectional messaging.
* The de facto implementation of REST is HTTP

### Benefits of GRPC
  * gRPC implements protocol buffers on top of HTTP/2, which makes it even faster for inter-process communication
  * gRPC uses a protocol buffer–based binary protocol to communicate with gRPC services and clients
  * gRPC fosters a contract-first approach for developing applications
  * Its strongly typed
  * It has duplex streaming
  * It has built-in commodity features
      * RPC offers built-in support for commodity features such as authentication, encryption, resiliency (deadlines and timeouts), metadata exchange, compres‐ sion, load balancing, service discovery, and so on

### Drawbacks of GRPC
  * It may not be suitable for external-facing services
    
### GRPC Communication Patterns
  * Simple RPC (Unary RPC)
  * Server-Streaming RPC
    * The server sends back a sequence of responses after getting a clients request message.
    * The sequence of multiple responses is known as a "stream".
    * After sending all the server responses, the server marks the end of the stream by sending the server’s 
    status details as trailing metadata to the client.
  * Client-Streaming RPC
    * Client sends multiple messages to the server instead of a single request.
    * 
  * Bidirectional-Streaming RPC
    * The key idea behind this business use case is that once the RPC method is invoked either the client or
    service can send messages at any arbitrary time.

### GRPC under the hood
  * For encoding messages, gRPC uses protocol buffers. 
  * Protocol buffers are a language-agnostic, platform-neutral, extensible mechanism for serializing structured data.
  * Protocol buffer encoded stream consists of the following
    * Tag
      * The tag is made up of field index and wire type.
      * The field index is the unique number we assigned to each message field when defining the message in the proto file.
      * The wire type is based on the field type, which is the type of data that can enter the field.
    * Value
  * Different encoding techni‐ ques are used by protocol buffers to encode the different types of data. Eg. UTF-8 for String.
  * Encoding Techniques
    * Varints (Variable length integers)
  * Length-Prefixed Message Framing

### GRPC OVER HTTP/2
   * In HTTP/2, all communication between a client and server is performed over a single TCP connection that can carry any 
    number of bidirectional flows of bytes.
     * Stream: A bidirectional flow of bytes within an established connection. A stream may carry one or more messages.
     * Frame: The smallest unit of communication in HTTP/2. Each frame contains a frame header, which at minimum identifies the
       stream to which frame belongs.
     * Message: A complete sequence of frames that map to a logical HTTP message that consists of one or more frames. This allows
       the messages to be multiplexed, by allowing the client and server to break down the message into independent frames.
   * When the client application creates a gRPC channel, behind the scenes it creates an HTTP/2 connection with the server. 
   * Once the channel is created we can reuse it to send multiple remote calls to the server. These remote calls are mapped 
     to streams in HTTP/2. 
   * Messages that are sent in the remote call are sent as HTTP/2 frames.
   * Request message is the one that initiates remote call. It consists of
     * Request Headers
     * length-Prefixed Message
     * end of stream flag
   * “half-close the connection” means the client closes the connection on its side so the client is no longer able to 
     send messages to the server but still can listen to the incoming messages from the server.