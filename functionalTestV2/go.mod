module github.com/functionalTest

go 1.18

require (
	github.com/Movie/functionalTest/config v0.0.0-00010101000000-000000000000
	github.com/Movie/functionalTest/ext v0.0.0-00010101000000-000000000000
	github.com/Movie/functionalTest/util v0.0.0-00010101000000-000000000000
	github.com/ohler55/ojg v1.14.3
	github.com/stretchr/testify v1.8.0
	golang.org/x/tour v0.1.0
)

require (
	github.com/davecgh/go-spew v1.1.1 // indirect
	github.com/google/uuid v1.3.0 // indirect
	github.com/pmezard/go-difflib v1.0.0 // indirect
	gopkg.in/yaml.v3 v3.0.1 // indirect
)

replace github.com/Movie/functionalTest/ext => ./ext

replace github.com/Movie/functionalTest/config => ./config

replace github.com/Movie/functionalTest/util => ./util
