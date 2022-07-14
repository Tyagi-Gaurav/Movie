package main

import (
	"fmt"
	"sync"
	"time"
)

type SafeCounter struct {
	mu sync.Mutex
	v  map[string]int
}

func (c *SafeCounter) Inc(key string) {
	defer c.mu.Unlock()
	c.mu.Lock()

	c.v[key]++
}

func (c *SafeCounter) Value(key string) int {
	defer c.mu.Unlock()
	c.mu.Lock()

	return c.v[key]
}

func main() {
	c := SafeCounter{v: make(map[string]int)}

	for i := 0; i < 1000; i++ {
		go c.Inc("someKey")
	}

	time.Sleep(time.Second)
	fmt.Println(c.Value("someKey"))
}
