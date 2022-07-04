# syntax=docker/dockerfile:1
FROM golang:1.18-alpine

WORKDIR /app

COPY ./ ./

RUN go mod download &&  \
    go get github.com/Movie/functionalTest/config && \
    go get github.com/Movie/functionalTest/ext

CMD ["CGO_ENABLED=0","go", "test"]
