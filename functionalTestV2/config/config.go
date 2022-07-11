package config

import (
	"fmt"
	"io/ioutil"

	"github.com/Movie/functionalTest/util"
	"gopkg.in/yaml.v3"
)

type AppConfig struct {
	Scheme       string `yaml:"scheme"`
	Host         string `yaml:"host"`
	Port         int    `yaml:"port"`
	ContextPath  string `yaml:"contextPath"`
	HealthCheck  string `yaml:"healthCheck"`
	StatusPath   string `yaml:"statusPath"`
	ActuatorPath string `yaml:"actuatorPath"`
	MetricsPath  string `yaml:"metricsPath"`
}

type Credentials struct {
	UserName string `yaml:"userName"`
	Password string `yaml:"password"`
}

type YamlConfig struct {
	Apps             map[string]AppConfig `yaml:"apps"`
	AdminCredentials Credentials          `yaml:"admin-credentials"`
}

var yamlConfig = &YamlConfig{}
var Configs map[string]AppConfig
var GlobalCredentials Credentials

func init() {
	data, err := ioutil.ReadFile("config/config.yml")
	util.PanicOnError(err)
	err = yaml.Unmarshal(data, yamlConfig)
	util.PanicOnError(err)
	Configs = yamlConfig.Apps
	GlobalCredentials = yamlConfig.AdminCredentials
}

func (a AppConfig) HealthCheckUrl() string {
	return fmt.Sprintf("%v://%v:%d%v%v", a.Scheme, a.Host, a.Port, a.ContextPath, a.HealthCheck)
}

func (a AppConfig) StatusUrl() string {
	return fmt.Sprintf("%v://%v:%d%v%v", a.Scheme, a.Host, a.Port, a.ContextPath, a.StatusPath)
}

func (a AppConfig) MetricsUrl() string {
	return fmt.Sprintf("%v://%v:%d%v%v", a.Scheme, a.Host, a.Port, a.ContextPath, a.MetricsPath)
}

func (a AppConfig) ConfigPublishUrl() string {
	return fmt.Sprintf("%v://%v:%d%v%v", a.Scheme, a.Host, a.Port, a.ContextPath, a.ActuatorPath)
}

func (a AppConfig) CreateUrlV2() util.URLResolver {
	return func(path string) string {
		return fmt.Sprintf("%v://%v:%d%v%v", a.Scheme, a.Host, a.Port, a.ContextPath, path)
	}
}
