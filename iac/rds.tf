resource "aws_db_parameter_group" "" {
  family = ""
}

resource "aws_db_subnet_group" "mariadb-subnet" {
  name = "mariadb-subnet"
  subnet_ids = [${aws.main}]
}