package main

import "log"

type Work struct {
}

//Recover is only useful inside defer functions.

func server(workChan <-chan *Work) {
	for work := range workChan {
		go safelyDo(work)
	}
}

func safelyDo(work *Work) {
	defer func() {
		if err := recover(); err != nil {
			log.Println("Work failed", err)
		}
		//Recover can also alter the return value.
	}()
	do(work)
}

func do(work *Work) {
	//if do(work) panics, the result will be logged and the goroutine will exit cleanly without disturbing the others.
}
