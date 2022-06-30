package main

import (
	"fmt"
	"log"
	"net"
	"net/http"
	"strings"
	"time"
)

type RequestFilter func(*http.Request) bool
type Middleware func(http.HandlerFunc) http.HandlerFunc

type Host string
type HostList []Host
type HostSet map[Host]interface{}

type Filters []RequestFilter
type Stack []Middleware
type Endpoint struct {
	Handler    http.HandlerFunc
	Allow      Filters
	Middleware Stack
}

//Type declaration for a function
type HostFilter func(h Host) bool

func (s HostSet) Add(h Host) {
	s[h] = struct{}{}
}

func (s HostSet) Remove(h Host) {
	delete(s, h)
}

func (s HostSet) Contains(h Host) bool {
	_, found := s[h]
	return found
}

func (s HostList) Select(hf HostFilter) HostList {
	result := make(HostList, 0, len(s))

	for _, h := range s {
		if hf(h) {
			result = append(result, h)
		}
	}

	return result
}

func (f HostFilter) Or(g HostFilter) HostFilter {
	return func(h Host) bool {
		return f(h) || g(h)
	}
}

func (f HostFilter) And(g HostFilter) HostFilter {
	return func(h Host) bool {
		return f(h) && g(h)
	}
}

var HasGo HostFilter = func(h Host) bool {
	return strings.Contains(string(h), "go")
}

var IsAcademic HostFilter = func(h Host) bool {
	return strings.Contains(string(h), "academy")
}

var IsDotOrg HostFilter = func(h Host) bool {
	return strings.HasSuffix(string(h), ".org")
}

func CIDR(cidrs ...string) RequestFilter {
	nets := make([]*net.IPNet, len(cidrs))

	for i, cidr := range cidrs {
		//TODO: handle err
		_, nets[i], _ = net.ParseCIDR(cidr)
	}

	return func(r *http.Request) bool {
		//TODO: handle err
		host, _, _ := net.SplitHostPort(r.RemoteAddr)
		ip := net.ParseIP(host)
		for _, net := range nets {
			if net.Contains(ip) {
				return true
			}
		}

		return false
	}
}

func Allow(f RequestFilter) Middleware {
	return func(h http.HandlerFunc) http.HandlerFunc {
		return func(w http.ResponseWriter, r *http.Request) {
			if f(r) {
				h(w, r)
			} else {
				//TODO
				w.WriteHeader(http.StatusForbidden)
			}
		}
	}
}

func PasswordHeader(password string) RequestFilter {
	return func(r *http.Request) bool {
		return r.Header.Get("X-Password") == password
	}
}

func Method(methods ...string) RequestFilter {
	return func(r *http.Request) bool {
		for _, m := range methods {
			if r.Method == m {
				return true
			}
		}
		return false
	}
}

func Logging(f http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		fmt.Printf("[%v] - %s %s\n", time.Now(), r.Method, r.RequestURI)
		f(w, r)
	}
}

func SetHeader(key, value string) Middleware {
	return func(f http.HandlerFunc) http.HandlerFunc {
		return func(w http.ResponseWriter, r *http.Request) {
			w.Header().Set(key, value)
			f(w, r)
		}
	}
}

func hello(w http.ResponseWriter, r *http.Request) { //This is a http.HandlerFunc type
	fmt.Fprintf(w, "Hello\n")
}

func (f Filters) Combine() RequestFilter {
	return func(r *http.Request) bool {
		for _, filter := range f {
			if !filter(r) {
				return false
			}
		}
		return true
	}
}

func (s Stack) Apply(f http.HandlerFunc) http.HandlerFunc {
	g := f
	for _, mware := range s {
		g = mware(g)
	}
	return g
}

func (e Endpoint) Build() http.HandlerFunc {
	allowFilter := e.Allow.Combine()
	restricted := Allow(allowFilter)(e.Handler)

	return e.Middleware.Apply(restricted)
}

func main() {
	s := make(HostSet)

	s.Add("golang.org")
	s.Add("google.com")
	s.Add("gopheracademy.org")
	s.Remove("google.com")

	hostNames := HostList{"golang.org", "google.com", "gopheracademy.org"}

	for _, n := range hostNames {
		fmt.Printf("%s? %v\n", n, s.Contains(n))
	}

	fmt.Printf("%v\n", hostNames.Select(IsDotOrg))

	goHosts := hostNames.Select(IsDotOrg.Or(HasGo))
	academies := hostNames.Select(IsDotOrg.And(IsAcademic))

	fmt.Printf("Go sites: %v\n", goHosts)
	fmt.Printf("Academy sites: %v\n", academies)

	//Allow Returns a Middleware which takes handler function (hello) as input and returns a handler function.
	//Logging takes a handler function and
	http.HandleFunc("/hello", Logging(Allow(CIDR("127.0.0.1/32"))(hello)))

	myEndpoint := Endpoint{
		Handler: hello,
		Allow: Filters{
			CIDR("127.0.0.1/32"),
		},
		Middleware: Stack{
			Logging,
			SetHeader("X-Foo", "Bar"),
		},
	}

	http.HandleFunc("/hello2", myEndpoint.Build())

	log.Fatal(http.ListenAndServe(":1217", nil))
}
