variable "AWS_ACCESS_KEY" {}
variable "AWS_SECRET_KEY" {}
variable "AWS_REGION" {
  default = "eu-west-1"
}

variable "INSTANCE_DEVICE_NAME" {
  default = "/dev/xvdh"
}

variable "AMIS" {
  type = map(string)
  default = {
    eu-west-1 = "ami-0f29c8402f8cce65c"
  }
}
variable "PATH_TO_PUBLIC_KEY" {
  default = "/Users/gauravtyagi/workspace/personal/Movie/iac/mykey.pub"
}
variable "PATH_TO_PRIVATE_KEY" {
  default = "/Users/gauravtyagi/workspace/personal/Movie/iac/mykey"
}
variable "INSTANCE_USERNAME" {
  default = "ubuntu"
}