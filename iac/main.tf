variable "myvar" {
  type    = string
  default = "hello terraform"
}

variable "myMap" {
  type = map(string)
  default = {
    mykey = "my value"
  }
}

variable "myList" {
  type    = list(any)
  default = [1, 2, 3]
}