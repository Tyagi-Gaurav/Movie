package ext

import (
	"log"
	"net/http"
)

type Getter[T any] interface {
	ExecuteGet(url string) (http.Response, error)
}

type WebClient struct {
}

func (h WebClient) ExecuteGet(url string) (*http.Response, error) {
	resp, err := http.Get(url)

	if err != nil {
		log.Fatalln("Failure occurred: ", err)
	}

	return resp, nil
}
