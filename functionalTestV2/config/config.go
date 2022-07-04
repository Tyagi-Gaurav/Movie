package config

import "fmt"

type AppConfig struct {
	scheme      string
	host        string
	port        int
	contextPath string
	healthCheck string
}

func (a AppConfig) HealthCheckUrl() string {
	return fmt.Sprintf("%v://%v:%d%v%v", a.scheme, a.host, a.port, a.contextPath, a.healthCheck)
}

//TODO May be read this information from a YAML file.
var Configs = map[string]AppConfig{
	"movie": {scheme: "http", host: "localhost", port: 8080, contextPath: "/content/upload", healthCheck: "/actuator/healthcheck"},
	"user":  {scheme: "http", host: "localhost", port: 8080, contextPath: "/user", healthCheck: ""},
}
