module github.com/Movie/functionalTest/test

go 1.18

replace github.com/Movie/functionalTest/config => ../config

require (
	github.com/Movie/functionalTest/config v0.0.0-00010101000000-000000000000 // indirect
	github.com/Movie/functionalTest/ext v0.0.0-00010101000000-000000000000 // indirect
)

replace github.com/Movie/functionalTest/ext => ../ext
