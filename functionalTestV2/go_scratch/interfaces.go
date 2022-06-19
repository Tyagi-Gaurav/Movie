package main

import "fmt"

//An interface type is defined as a set of method signatures.
//A value of interface type can hold any value that implements those methods.

//Interfaces are implemented implicitly
type I interface {
	M()
}

type T struct {
	S string
}

func (t *T) M() {
	//Handle nil receiver
	if t == nil {
		fmt.Println("<nil>")
		return
	}
	fmt.Println(t.S)
}

//Calling a method on an interface value executes the method of the same name on its underlying type.
func describe(i I) {
	if i == nil {
		fmt.Println("<nil>")
		return
	}
	fmt.Printf("(%v, %T", i, i)
}

func main() {
	var i I = &T{"Hello"}
	i.M()
	describe(i)

	var t *T    //nil type
	var a I = t //Assigning a nil structure to interface
	describe(a)
	a.M()

	//Type Assertions
	var i1 interface{} = "Hello"

	s1 := i1.(string)
	fmt.Println(s1)

	s2, ok := i1.(float32) //Check if float32 can be assigned from i1 interface
	fmt.Println(s2, ok)

	//When we try to do it without the ok result, it results in panic.
	//s3 := i1.(float32) ---> Uncomment the following lines to see the error.
	//fmt.Println(s3)
}
