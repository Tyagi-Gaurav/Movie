# Initialize terraform 
`terraform init`

# Use plan command first by outputting the plan to a file
`terraform plan -out plan.txt`

# Then terraform apply
`terraform apply plan.txt`

# Destroy terraform
`terraform destroy`

# Terraform format
`terraform fmt`

# Terraform graph
`terraform graph`

# Terraform get - Download and update modules
`terraform get`

# Terraform output [options][NAME]
`terraform output`

# Terraform refresh (Refresh the remote state)
`terraform refresh`

# Terraform state (Eg Rename a resource)
`terraform state` 

# Terraform validate
`terraform validate`

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

# Terraform state
   * Backup of previous state: terraform.tfstate.backuo
   * Remote state: terraform.tfstate

# Data sources
   * Data provided by AWS

# Template File
# Modules
   * terraform get
# VPCs
   * Each subnet has their own IP range
   * Instances in main-public can reach instances in main-private
   * Public subnet
     * All public subnets are connected to internet gateway 
     * Instances launched in this will have both public and private IP
   * Private Subnet
     * Instances launched in this will only have private IPs.
     * Only a few private subnets
       * 10.0.0.0/8 (From 10.0.0.0 -> 10.255.255.255)
       * 172.16.0.0/12
       * 192.168.0.0/16
     * What does /8 or /16 mean?
       * They are subnet masks 
       * IPv4 addresses represented in CIDR notation are called network masks, and they specify the number of bits in the prefix to the 
       address after a forward slash (/) separator.
       * 10.0.0.0/8
         * Network Mask: 255.0.0.0
         * Ex: 10.0.0.1
       * 10.0.0.0/24
         * Only 256 IP addresses
         * Subnet Mask: 255.255.255.0

# EBS (Elastic Block Storage)
  * Ephemeral Storage 
  * EBS storage can be added to an instance

# UserData
  * Executing commands at instance launch
    * Install extra software
    * Prepare instance to join a cluster
    * Mount Volumes
    * Only executed when instance is created and not when instance is launched.

# Route53
  * Using hostnames
  * Host domain name on AWS
  * Create a zone in Route53 and a record
  * To check host in namespace
    * `host -t A server1.gt.training.academy ns-1481.awsdns-57.org`
    * `host -t MX gt.training.academy ns-1481.awsdns-57.org`

# RDS
  * Managed Database Solution
    * Provides replication
    * Automated Snapshots
    * Automated Security updates
    * Easy instance replacement (For Vertical Scaling)
    * Create a subnet group
      * Specify which subnets database would be in
    * Create a parameter group
      * Allows you to specify parameters to change settings in the database
    * Create security group
      * Allows incoming traffic to the RDS instance