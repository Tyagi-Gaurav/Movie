package main

import "fmt"

//Index returns index of x in s, or -1, if not found
func Index[T comparable](s []T, x T) int {
	for i, v := range s {
		//v and x are type T, which has the built-in comparable
		//constraint, so we can use == here
		if v == x {
			return i
		}
	}

	return -1
}

type List[T any] struct {
	next *List[T]
	val  T
}

func main() {
	si := []int{10, 20, 15, -10}
	fmt.Println(Index(si, 15))

	//Index also works on a slice of strings
	ss := []string{"foo", "bar", "baz"}
	fmt.Println(Index(ss, "hello"))
}
