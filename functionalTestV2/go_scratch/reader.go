package main

import (
	"fmt"
	"io"
	"os"
	"strings"
)

type MyReader struct{}

func (m MyReader) Read(b []byte) (int, error) {
	b[0] = 'A'
	return 1, nil
}

type rot13Reader struct {
	r io.Reader
}

func (rot rot13Reader) Read(b []byte) (int, error) {
	x := make([]byte, 8)
	n, err := rot.r.Read(x)

	if err == io.EOF {
		return 0, io.EOF
	}

	for i := 0; i < n; i++ {
		if x[i] >= 65 && x[i] <= 90 {
			b[i] = ((x[i]-65)+13)%26 + +65
		} else if x[i] >= 97 && x[i] <= 123 {
			b[i] = ((x[i]-97)+13)%26 + 97
		} else {
			b[i] = x[i]
		}

		//fmt.Printf("Read %s with value %d and transformed to %d\n", string(x[i]), x[i], b[i])
	}

	return n, nil
}

func main() {
	reader := strings.NewReader("Hello, Reader!")

	b := make([]byte, 8)

	for { //Infinite loop
		n, err := reader.Read(b)
		fmt.Printf("n = %v err = %v b = %v\n", n, err, b)
		fmt.Printf("b[:n] = %q\n", b[:n])
		if err == io.EOF {
			break
		}
	}

	s := strings.NewReader("Lbh penpxrq gur pbqr!")
	r := rot13Reader{s}
	io.Copy(os.Stdout, &r)
}
