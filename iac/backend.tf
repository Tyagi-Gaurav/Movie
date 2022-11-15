terraform {
  backend "s3" {
    bucket = "terraform-state-ou769g"
    key    = "terraform/example"
  }
}