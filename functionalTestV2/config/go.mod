module github.com/Movie/functionalTest/config

go 1.18

replace github.com/Movie/functionalTest/util => ../util

require (
	github.com/Movie/functionalTest/util v0.0.0-00010101000000-000000000000
	gopkg.in/yaml.v3 v3.0.1
)
