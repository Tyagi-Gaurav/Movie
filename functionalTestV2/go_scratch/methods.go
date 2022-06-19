package main

import (
	"fmt"
	"math"
)

//You can define methods on types. A method is a function with a special receiver argument.
//The receiver argument appears in its own argument list between the func keyword and the method name.
type Point struct {
	X, Y float64
}

type MyFloat float64

//With Value receivers, the method operates on the copy of the original value.
func (p Point) Abs() float64 {
	return math.Sqrt(p.X*p.X + p.Y*p.Y)
}

func Abs2(p Point) float64 {
	return math.Sqrt(p.X*p.X + p.Y*p.Y)
}

//You can only declare a method with a receiver whose type is defined in the same package as the method.
//You cannot declare a method with a receiver whose type is defined in another package
//(which includes the built-in types such as int).
func (mf MyFloat) Abs() float64 {
	if mf < 0 {
		return float64(-mf)
	}
	return float64(mf)
}

//Pointer Receivers
//In order to change the receiver object, we must use a pointer receiver.
func (v *Point) Scale(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

//Methods with value receivers take either a value or pointer as the receiver when they are called.
//Methods with pointer receivers can only take pointers.
func main() {
	p := Point{3, 4}
	fmt.Println(p.Abs())
	fmt.Println(Abs2(p))

	p.Scale(10)

	fmt.Println(p.Abs())
}
