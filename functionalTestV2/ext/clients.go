package ext

import (
	"log"
	"net/http"
	"strings"

	"github.com/Movie/functionalTest/util"
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

	util.PanicOnError(err)

	return resp, nil
}

func (h WebClient) executePostWithHeaders(url string, contentType string, body string,
	headers map[string]string) (*http.Response, error) {
	client := &http.Client{}
	req, err := http.NewRequest("POST", url, strings.NewReader(body))
	util.PanicOnError(err)

	for k, v := range headers {
		req.Header.Set(k, v)
	}
	req.Header.Set("Content-Type", contentType)

	//log.Printf("Request %v", req)
	resp, err := client.Do(req)
	util.PanicOnError(err)

	return resp, nil
}

func (h WebClient) executeDeleteWithHeaders(url string, contentType string,
	headers map[string]string) (*http.Response, error) {
	client := &http.Client{}
	req, err := http.NewRequest("DELETE", url, nil)
	util.PanicOnError(err)

	for k, v := range headers {
		req.Header.Set(k, v)
	}
	req.Header.Set("Content-Type", contentType)

	//log.Printf("Request %v", req)
	resp, err := client.Do(req)
	util.PanicOnError(err)

	return resp, nil
}
