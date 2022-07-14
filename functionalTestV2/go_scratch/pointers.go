package main

import (
	"fmt"
	"strings"
)

type Vertex struct {
	X int
	Y int
}

type Vertex2 struct {
	Lat, Long float64
}

type Artist struct {
	Name, Genre string
	Songs       int
}

func main() {
	v := Vertex{1, 2}
	v.X = 4
	fmt.Println(v.X)

	p := &v //Pointer to Struct
	p.X = 1e9
	fmt.Println(v)

	//Struct field can be accessed through a struct pointer

	//Arrays
	primes := [6]int{2, 3, 5, 7, 11, 13}

	fmt.Println(primes)

	//Slice is a dynamically-sized, flexible view into the elements of an array.
	var s []int = primes[1:4]
	fmt.Println(s)

	slices()
	me1 := Artist{Name: "Matt", Genre: "Electro", Songs: 42}

	fmt.Printf("%s Released their %dth song\n", me1.Name, newRelease(me1))
	fmt.Printf("%s has total of %d songs\n", me1.Name, me1.Songs)

	me2 := &Artist{Name: "Matt", Genre: "Electro", Songs: 42}
	fmt.Printf("%s Released their %dth song\n", me2.Name, newRelease2(me2))
	fmt.Printf("%s has total of %d songs\n", me2.Name, me2.Songs)

	maps()
}

func maps() {
	var m = make(map[string]Vertex2)

	m["bell labs"] = Vertex2{
		40.6, -74.3,
	}

	m1 := make(map[string]int)

	m1["Answer"] = 42
	fmt.Println("The Value: ", m1["Answer"])

	m1["Answer"] = 48
	fmt.Println("The Value: ", m1["Answer"])

	delete(m1, "Answer")
	fmt.Println("The Value: ", m1["Answer"])

	v, ok := m1["Answer"]
	fmt.Println("The Value: ", v, " Present: ", ok)
	fmt.Println(wordMap("To be or Not To be"))

	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(pos(i), neg(2*i))
	}

	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}

func wordMap(s string) map[string]int {
	m := make(map[string]int)
	words := strings.Fields(s)

	for _, v := range words {
		m[v] = m[v] + 1
	}

	return m
}

//Function closure for adder
func adder() func(int) int {
	sum := 0
	return func(x int) int {
		sum += x
		return sum
	}
}

//Function Closure for fibonacci
func fibonacci() func() int {
	a, b := 0, 1
	return func() int {
		c := a + b
		r := a
		a = b
		b = c
		return r
	}
}

func newRelease(a Artist) int {
	a.Songs++
	return a.Songs
}

func newRelease2(a *Artist) int {
	a.Songs++
	return a.Songs
}

func slices() {
	//Slices are like references to arrays
	//A slice does not store any data, it just describes a section of an underlying array.

	//Changing the elements of the slice changes the elements of the underlying array.

	//Other slices that share the same underlying array will see those changes.
	names := [4]string{
		"John",
		"Paul",
		"George",
		"Ringo",
	}

	fmt.Println(names)

	a := names[0:2]
	b := names[1:3]
	fmt.Println(a, b)

	b[0] = "XXX"
	fmt.Println(a, b)
	fmt.Println(names)

	//This is an array literal
	c := [3]bool{true, true, false}
	fmt.Println(c)

	//Creates the same array as above, then builds a slice that references it.
	d := []bool{true, true, false}
	fmt.Println(d)

	//Default low/high bounds for slice are 0 and length
	//Following are equivalent: a[0:10], a[0:], a[:10], a[:]

	//Length of slice is the number of elements it contains.
	//Capacity of Slice is the number of elements in the underlying array counting from first element of the slice.
	s := []int{2, 3, 5, 7, 11, 13}
	printSlice(s)

	//Give it 0 length
	s = s[:0]
	printSlice(s)

	//Extend its length
	s = s[:4]
	printSlice(s)

	//Drop its first 2 values
	s = s[2:]
	printSlice(s)

	//Slices can be created with built-in function make
	a1 := make([]int, 5) //len(a1) = 5

	b1 := make([]int, 0, 5) //len(b1) = 0, cap(b1) = 5

	a1 = append(a1, 0)
	b1 = append(b1, 2)

	printSlice(a1)
	printSlice(b1)

}

func printSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}
