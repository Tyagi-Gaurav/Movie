package main

import (
	"fmt"
	"strings"
)

type Host string
type HostList []Host
type HostSet map[Host]interface{}

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

func IsDotOrg(h Host) bool {
	return strings.HasSuffix(string(h), ".org")
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
}
