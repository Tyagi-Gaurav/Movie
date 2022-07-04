module github.com/functionalTest

go 1.18

require golang.org/x/tour v0.1.0

require (
	github.com/Movie/functionalTest/config v0.0.0-00010101000000-000000000000 // indirect
	github.com/Movie/functionalTest/ext v0.0.0-00010101000000-000000000000 // indirect
)

replace github.com/Movie/functionalTest/ext => ./ext

replace github.com/Movie/functionalTest/config => ./config
