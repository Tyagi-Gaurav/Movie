# Initialize terraform 
`terraform init`

# Use plan command first by outputting the plan to a file
`terraform plan -out plan.txt`

# Then terraform apply
`terraform apply plan.txt`

# Destroy terraform
`terraform destroy`

# Terraform variable types
 * Simple
   * string
   * bool
 * Complex
   * List(type)
   * Map(type)
   * Set(type)
   * Object is like a map, but each element could have different type
   * A tuple is like a list, but each element could have different type