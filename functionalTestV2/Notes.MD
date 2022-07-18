
# Environment setup
 
* Setup GOPATH
Used to look for places where Go code is located.

* Checking Go Version
`go version`

* Update Go Version
Use Homebrew

* Creating a go module
```go mod init functionalTest```

* go.mod file defines the module's module path. 
* To publish a module from local use go mod edit

* Discover the go install path
```go list -f {{.Target}}''```

* Setting target for Go Binary
if you already have a directory like `$HOME/bin` in your shell path and you'd like to install your Go programs there, you can change the install target by setting the `GOBIN` variable using the go env command:

```go env -w GOBIN=/path/to/your/bin```

* Installing the binary
go install

* Unbuffered vs Buffered Channel
An unbuffered channel is used to perform synchronous communication between goroutines while a buffered channel is used for perform asynchronous communication. An unbuffered channel provides a guarantee that an exchange between two goroutines is performed at the instant the send and receive take place.