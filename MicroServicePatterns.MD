### Functional Decomposition
* The software architecture of a computing system is the set of structures needed to reason about the system, which comprise software elements, relations among them, and properties of both.
* 4+1 view model
    * Defines 4 different views of software architecture
        * Logical View
            * Software ellements - classes, packages, relationships(Inheritance, Associations, etc) between them
        * Implementation View
            * Output of a build system
            * Modules that represent package code and executable code.
        * Process View
            * Components at runtime.
            * Each element is a process.
        * Deployment
            * How processes map to machines ie. physical or virtual machines.
            * The relationships between machines represent networking
    * The +1 in the model are the scenarios.
        * Each scenario describes how all the architectual elements within a particular "view" collaborate to handle the request.
* Architectural Styles
    * Layered Architecture
        * A layered architecture can be applied to any of the 4 views above.
        * The popular 3-tier architecture is applied to logical view
            * Presentation Layer
            * Business Logic Layer
            * Persistence Layer
    * Hexagonal Architecture
        * Organizes logicl view in a way that places business logic at the center.
        * The business logic has one or more ports. A port defines a set of operations and is how the business logic interacts with the outside world.
            * Inbound Port - Ex: Service Interface
            * Outbound Port - Ex: Repository Interface			
        * Instead of presentation layer, there are one or more inbound adapters that handle requests from the outside.
            * An inbound adapter handles requests from the out- side world by invoking an inbound port
        * Instead of database layer, there are one or more outbound adapters that handle requests from the outside.
* Services
    * All services should be loosely coupled.
* Defining an applications microservice architecture
    * User Stories (Use cases)
        * Identify system operations
        * Entities from nouns and operations from verbs
        * Create high level domain model (A much simpler model than would be implemented)
    * Identify services (Entities/Classes/Bounded Context)
    * Define Service Collaborations (Sync/Async)
        * Classify each system operations (Command vs Query vs Event)
    * Challenges
        * A particular decomposition would be impractical due to too many round-trips between services
        * Synchronous communication between services reduces availability
        * Maintain data consistency across services
*  Decomposing strategies
    * By Business capabilities
    * By DDD Sub-Domains
* Decomposition Guidelines
    * SRP (Single Responsibility Principle)
    * CCP (Common Closure Principle)
        * Classes in a package should be closed together against the same kind of changes.
        * If two classes change in lockstep because of the samee underlying reason, then they belong in the same package. 
* Obstacles to decomposing
    * Network Latency
    * Synchronous IPC reduces availability
        * If in order to fulfill a request, my service depends on other services to be available, then having  synchronous communication would mean that other services should be available. If they're not, then I cannot fulfill the request. Hence availability reduces. 

### Interprocess Communication
* The REST maturity model
    * Level 0
        * Clients of a level 0 service invoke the service by making HTTP POST requests to its sole URL endpoint. 
        * Each request specifies the action to perform, the target of the action (for example, the business object), and any parameters.
    * Level 1
        * A level 1 service supports the idea of resources. 
        * To perform an action on a resource, a client makes a POST request that specifies the action to perform and any parameters.
    * Level 2
        * A level 2 service uses HTTP verbs to perform actions: GET to retrieve, POST to create, and PUT to update.
        * The request query parameters and body, if any, specify the actions' parameters. This enables services to use web infrastructure such as caching for GET requests.
    * Level 3
        * The design of a level 3 service is based on the terribly named HATEOAS (Hypertext As The Engine Of Application State) principle. 
        * The basic idea is that the representation of a resource returned by a GET request contains links for performing actions on that resource. 
        * For example, a client can cancel an order using a link in the representation returned by the GET request that retrieved the order. 
        * The benefits of HATEOAS include no longer having to hard-wire URLs into client code

* Drawbacks of REST
    * Reduced availability. Because the client and service communicate directly with- out an intermediary to buffer messages, they must both be running for the duration of the exchange.
    * Clients must know the locations (URLs) of the service instances(s).
    * Clients must use what is known as a service discovery mechanism to locate service instances.
    * Fetching multiple resources in a single request is challenging
    * It’s sometimes difficult to map multiple update operations to HTTP verbs

* Service Discovery
    * Benefits
        * All aspects of service discovery are entirely handled by the deployment platform. 
        * Neither the services nor the clients contain any service discovery code. 
        * Consequently, the service discovery mechanism is readily available to all services and clients regardless of which language or framework they’re written in.
    * Drawbacks
        * It only supports the discovery of services that have been deployed using the platform. 
        * For example, as mentioned earlier when describing application-level discovery, Kubernetes-based discovery only works for services running on Kubernetes.

* Asynchronous Messaging
    * There are several different kinds of messages
        * Document: A generic message that contains only data
        * Command: A message that’s the equivalent of an RPC request
        * Event: A message indicating that something notable has occurred in the sender. An event is often a domain event, which represents a state change of a domain object such as an Order, or a Customer
    * Handling duplicate messages
        * Write Idempotent message handlers
        * Track messages and discard duplicates
    * Transactional Messaging
        * Use database table as temporary message queue
    * Eliminating synchronous communication
      * Replicate Data
        * Service keeps replica of data that it needs when processing requests. 
        * Data is kept upto date by subscribing to events published by the services that own the data.
      * Finish processing after returning a response
        * Validate the request using only the data available locally
        * Update its database, including inserting messages into the OUTBOX table
        * Return a response to its client
        