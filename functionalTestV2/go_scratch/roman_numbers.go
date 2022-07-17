package main

import "fmt"

/*
I - 1
V - 5
X - 10
L - 50
C - 100
D - 500
M - 1000
IV - 4
IX - 9
*/

func roman_to_numbers(romExp string) int {
	l := len(romExp)
	sum := 0
	for i := 0; i < l; i++ {
		a := romExp[i]
		//fmt.Println("A: ", string(a))
		switch a {
		case 'I':
			{
				if i+1 < l {
					b := romExp[i+1]
					if b == 'V' {
						sum += 4
						i++
					} else if b == 'X' {
						sum += 9
						i++
					} else {
						sum += 1
					}
				} else {
					sum += 1
				}
			}

		case 'V':
			sum += 5
		case 'X':
			{
				if i+1 < l {
					b := romExp[i+1]
					if b == 'L' {
						sum += 40
						i++
					} else if b == 'C' {
						sum += 90
						i++
					} else {
						sum += 10
					}
				} else {
					sum += 10
				}
			}
		case 'L':
			sum += 50
		case 'C':
			{
				if i+1 < l {
					b := romExp[i+1]
					if b == 'D' {
						sum += 400
						i++
					} else if b == 'M' {
						sum += 900
						i++
					} else {
						sum += 100
					}
				} else {
					sum += 100
				}
			}
		case 'D':
			sum += 500
		case 'M':
			sum += 1000
		default:
			sum += 0
		}
		//fmt.Println(sum)
	}
	return sum
}

func numbers_to_romans(num int) string {
	out := ""
	for {
		if num > 0 {
			if num >= 1000 {
				out = out + "M"
				num = num - 1000
			} else if num >= 900 {
				out = out + "CM"
				num = num - 900
			} else if num >= 500 {
				out = out + "D"
				num = num - 500
			} else if num >= 400 {
				out = out + "CD"
				num = num - 400
			} else if num >= 100 {
				out = out + "C"
				num = num - 100
			} else if num >= 90 {
				out = out + "XC"
				num = num - 90
			} else if num >= 50 {
				out = out + "L"
				num = num - 50
			} else if num >= 40 {
				out = out + "XL"
				num = num - 40
			} else if num >= 10 {
				out = out + "X"
				num = num - 10
			} else if num >= 9 {
				out = out + "IX"
				num = num - 9
			} else if num >= 5 {
				out = out + "V"
				num = num - 5
			} else if num >= 4 {
				out = out + "IV"
				num = num - 4
			} else if num >= 1 {
				out = out + "I"
				num = num - 1
			}
		} else {
			break
		}
		//fmt.Println(out, num)
	}
	return out
}

func main() {
	fmt.Println(roman_to_numbers("I"))
	fmt.Println(roman_to_numbers("II"))
	fmt.Println(roman_to_numbers("IX"))
	fmt.Println(roman_to_numbers("IV"))
	fmt.Println(roman_to_numbers("XXXIX"))     //39
	fmt.Println(roman_to_numbers("CCXLVI"))    //246
	fmt.Println(roman_to_numbers("DCCLXXXIX")) //789
	fmt.Println(roman_to_numbers("MMCDXXI"))   //2421

	fmt.Println(numbers_to_romans(2421)) //MMCDXXI
	fmt.Println(numbers_to_romans(789))  //DCCLXXXIX
	fmt.Println(numbers_to_romans(246))  //CCXLVI
	fmt.Println(numbers_to_romans(39))   //XXXIX
}
