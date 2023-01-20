# C4 Model
* Use only those that add value
* Level 1: System Context
  * Shows big picture of the system
  * Focus on actors, roles, personas etc. (NOT technologies, protocols, and other low level detail)
  * Intended for everybody to understand
  * Permitted elements
    * Software Systems (Eg. Internet Banking System, Email System, Mainframe banking System)
    * People (eg. Personal Banking Customer)
* Level 2: Containers
  * Scope: Single Software System 
  * Container represents an application or datastore
  * A context or boundary under which some code is executed or some data is stored.
  * Each container is separately deployable/runnable thing or runtime environment.
  * Also shows responsibilities and how containers interact with one another (Protocol)
  * This diagram says nothing about deployment scenarios, clustering, replication, failover, etc.
  * Permitted elements
    * Software Systems
    * People (eg. Personal Banking Customer)
    * Containers within the software system in scope (Eg. Web Application, API Application, Mobile App)
* Level 3: Components
  * Scope: Single container
  * Group of related functionality encapsulated behind an interface
  * Collection of implementation classes behind an interface
  * All components inside a container typically execute in the same process space.
  * Technology and implementation detail comes here
  * Permitted elements
    * Software Systems
    * People (eg. Personal Banking Customer)
    * Other Containers within the parent software system of the container in scope (Eg. Web Application, API Application, Mobile App)
    * Components within the container in scope (Eg. Sign-In controller, Security Component, Reset Password Controller, Email Component)
* Level 4: Code
  * Scope: A single component
  * UML class diagrams
  * Entity relationship diagrams
  * Permitted elements
    * Code elements (eg. classes, interfaces, etc.)
  
* Other diagrams
  * Dynamic Diagrams
    * UML Sequence Diagram
    * UML Collaboration Diagram
  * Deployment Diagram
    * Mapping between containers and deployment nodes
  
# References
* c4model.com


