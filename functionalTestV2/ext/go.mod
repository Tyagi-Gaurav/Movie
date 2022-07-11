module github.com/functionalTest/ext

go 1.18

replace github.com/Movie/functionalTest/util => ../util

require github.com/Movie/functionalTest/util v0.0.0-00010101000000-000000000000

require (
	github.com/davecgh/go-spew v1.1.1 // indirect
	github.com/google/uuid v1.3.0
	github.com/pmezard/go-difflib v1.0.0 // indirect
	github.com/stretchr/testify v1.8.0 // indirect
	gopkg.in/yaml.v3 v3.0.1 // indirect
)
