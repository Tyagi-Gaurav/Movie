package ext

import "net/http"

type Getter interface {
	//TODO Add response handler and error handler
	ExecuteGet(url string) (resp *http.Response, err error)
}

type HealthCheck struct {
}

func (h HealthCheck) ExecuteGet(url string) (resp *http.Response, err error) {
	return nil, nil
}
