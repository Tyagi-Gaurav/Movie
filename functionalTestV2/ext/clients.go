package ext

import (
	"log"
	"net/http"
	"strings"
)

type WebClient struct {
}

func (h WebClient) ExecuteGet(url string) (*http.Response, error) {
	//log.Println("GET :", url)
	resp, err := http.Get(url)

	if err != nil {
		log.Fatalln("Failure occurred: ", err)
	}

	return resp, nil
}

func (h WebClient) executePost(url string, contentType string, body string) (*http.Response, error) {
	//log.Printf("POST %v, ContentType: %v, body: %v", url, contentType, body)
	resp, err := http.Post(url, contentType, strings.NewReader(body))

	if err != nil {
		log.Fatalln("Failure occurred: ", err)
	}

	return resp, nil
}
