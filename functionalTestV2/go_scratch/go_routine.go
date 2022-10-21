package main

import (
	"fmt"
	"time"
)

//goroutine -> Lightweight thread managed by Go runtime.
//Goroutines run in the same address space, so access to shared memory must be synchronized.
//Goroutines are multiplexed onto multiple OS threads so if one should block, others continue to run.

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

func sum(a []int, c chan int) {
	sum := 0

	for _, v := range a {
		sum += v
	}

	c <- sum //send sum tp c
}

func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	close(c)
}

func fibonacci2(c, quit chan int) { //Two channels c & quit.
	x, y := 0, 1
	for {
		select {
		case c <- x: //Producing on c
			x, y = y, x+y
		case <-quit: //Listening on quit
			fmt.Println("quit")
			return
		}
	}
}

func main() {
	go say("world")
	say("hello")

	s := []int{7, 2, 8, -9, 4, 0}

	//Channels are typed conduit through which you can send and receive values with channel operator <-
	//Data flows in direction of the arrow.

	c := make(chan int)
	go sum(s[:len(s)/2], c)
	go sum(s[len(s)/2:], c)

	x, y := <-c, <-c // Receive from c
	fmt.Println(x, y, x+y)

	//In unbuffered channel there is no capacity to hold any value before it's received.
	//Channels can be buffered. Provide a buffer length as the second argument to make to initialise a buffered channel.
	//Sends to a buffered channel block only when the buffer is full. Receives block when the buffer is empty.
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println(<-ch)
	fmt.Println(<-ch)

	//A sender can close a channel to indicate no more values will be sent. Receivers can test whether a channel
	//has been closed.
	fib := make(chan int, 10)
	go fibonacci(cap(fib), fib)
	for i := range fib {
		fmt.Println(i)
	}

	//select lets go routine wait on multiple communication operations.
	fib2 := make(chan int)
	quit := make(chan int)
	go func() {
		for i := 0; i < 10; i++ {
			fmt.Println(<-fib2) //Read from fib2
		}
		quit <- 0
	}()
	fibonacci2(fib2, quit)
}
