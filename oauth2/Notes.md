## About Oauth2 

# Diagrams
https://app.diagrams.net/#G16jsKiOVyKhlBEANwm2DxsQpYRsK9gUgp
General Gauth: https://swimlanes.io/u/mcghmm30h
Authorisation grant in detail: https://swimlanes.io/u/WlOs9eNsZ

# What is Oauth2?
* Letting someone who controls a resource allow a software application to access that resource on their behalf without impersonating them.
* The application requests authorization from the owner of the resource and receives tokens that it can use to access the resource.
* A good security system should be nearly invisible when all is functioning properly.
* An authorization framework
  * Getting right of access from one component of a system to another.
* Terms
  * Resource Owner
    * Has access to an API and can delegate access to that API.
    * Usually a person with access to web browser
  * Protected Resource
    * A component that resource owner has access to
    * For most part, it's a Web-API.
  * Client
    * Software that access the protected resource on behalf of the owner.
    * Whatever software consumes the API that makes up the protected resource.
  * Authorisation Server
    * Trusted by protected resource to issue special purpose security credentials - Oauth tokens
  * CoAP
    * Constrained application protocol
  * JOSE
    * JSON object signing and Encryption
  * POP
    * OAuth Proof of Possession tokens
* Oauth systems often follow the principle of TOFU: Trust on First Use
  * In this model, first time a security decision is to be made, without any context or configuration under which the decision can be made, the user is prompted.
* OAuths Components
  * Access Tokens
    * An artifact issued by Authorisation server to a client that indicates the rights that the client has been delegated.
    * OAuth tokens are opaque to the clients, which means that the clients has no need to look at the token itself. 
  * Scopes
    * A representation of a set of rights at a protected resource
  * Refresh Tokens
    * Clients use refresh tokens to request new access tokens without involving the resource owner
  * Authorisation Grants
    * Means by which an Oauth client is given access to a protected resource, and if successful it results in a client getting a token.