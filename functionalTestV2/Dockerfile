# syntax=docker/dockerfile:1
FROM golang:1.18-alpine

WORKDIR /app

COPY ./ ./

RUN go mod download &&  \
    go get github.com/Movie/functionalTest/config && \
    go get github.com/Movie/functionalTest/ext && \
    go get github.com/functionaltest/util && \
    go get github.com/stretchr/testify/require && \
    go get github.com/ohler55/ojg/jp && \
	go get github.com/ohler55/ojg/oj && \
    go mod tidy

CMD ["CGO_ENABLED=0","go", "test"]
