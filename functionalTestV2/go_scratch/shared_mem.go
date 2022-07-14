package main

import (
	"log"
	"net/http"
	"time"
)

const (
	numPollers     = 2
	pollInterval   = 60 * time.Second
	statusInterval = 10 * time.Second
	errTimeout     = 10 * time.Second
)

var urls = []string{
	"http://www.google.com",
	"http://golang.org/",
	"http://blog.golang.org",
}

//Represents last known state of the URL
type State struct {
	url    string
	status string
}

func logState(s map[string]string) {
	log.Println("Current State:")
	for k, v := range s {
		log.Printf("%s %s", k, v)
	}
}

/*
	Maintains a map that stores the state of URLs being polled,
	and prints the current state every updateInterval nanoseconds.
	It returns a chan state to which resource state should be sent.
*/
func StateMonitor(updateInterval time.Duration) chan<- State {
	updates := make(chan State)
	urlStatus := make(map[string]string)
	ticker := time.NewTicker(updateInterval)
	go func() {
		for {
			select {
			case <-ticker.C: //On each ticker, log status of Map
				logState(urlStatus)
			case s := <-updates: //On each State update, apply the
				urlStatus[s.url] = s.status
			}
		}
	}()
	return updates
}

func (r *Resource) Poll() string {
	resp, err := http.Head(r.url)
	if err != nil {
		log.Println("Error ", r.url, err)
		r.errCount++
		return err.Error()
	}
	r.errCount = 0
	return resp.Status
}

type Resource struct {
	url      string
	errCount int
}

//Sleeps for an appropriate interval before sending the Resource to Done.
func (r *Resource) Sleep(done chan<- *Resource) {
	time.Sleep(pollInterval + errTimeout*time.Duration(r.errCount))
	done <- r
}

/*
	in - Input Channel (Note the Arrow location)
	out, update: Send Channel
*/
func Poller(in <-chan *Resource, out chan<- *Resource, update chan<- State) {
	for r := range in {
		s := r.Poll()
		update <- State{r.url, s} //State of url to response code. This will send state update.
		out <- r                  //Write resource to  out
	}
}

func main() {
	//Create channels for input and output
	pending, complete := make(chan *Resource), make(chan *Resource)

	//Launch State Monitor
	status := StateMonitor(statusInterval) //Returns the channel to send updates.

	//Launch some pollers
	for i := 0; i < numPollers; i++ {
		go Poller(pending, complete, status)
	}

	//Send some resources to the pending queue to start processing in Pollers
	go func() {
		for _, url := range urls {
			pending <- &Resource{url: url}
		}
	}()

	for r := range complete {
		go r.Sleep(pending)
	}
}
