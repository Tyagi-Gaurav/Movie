package config

type AppConfig struct {
	Scheme string
	Host   string
	Port   int
}

//TODO May be read this information from a YAML file.
var Configs = map[string]AppConfig{
	"AppA": {Scheme: "http", Host: "localhost", Port: 9090},
	"AppB": {Scheme: "https", Host: "localhost", Port: 9091},
}
