package main

import "fmt"

type Person struct {
	Name string
	Age  int
}

type IPAddr [4]byte

//Implement the Stringer interface
func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func (ip IPAddr) String() string {
	return fmt.Sprintf("%v.%v.%v.%v", ip[0], ip[1], ip[2], ip[3])
}

func main() {
	//Type Assertions
	var i1 interface{} = "Hello"

	s1 := i1.(string)
	fmt.Println(s1)

	s2, ok := i1.(float32) //Check if float32 can be assigned from i1 interface
	fmt.Println(s2, ok)

	//When we try to do it without the ok result, it results in panic.
	//s3 := i1.(float32) ---> Uncomment the following lines to see the error.
	//fmt.Println(s3)

	typeSwitches(21)
	typeSwitches("hello")
	typeSwitches(true)

	a := Person{"Uncle chips", 42}
	z := Person{"Space Commanders", 9001}
	fmt.Println(a, z)

	hosts := map[string]IPAddr{
		"loopback":  {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}

func typeSwitches(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Printf("Twice %v is %v\n", v, v*2)
	case string:
		fmt.Printf("%q is %v bytes long\n", v, len(v))
	default:
		fmt.Printf("I don't know about type %T!\n", v)
	}
}
