resource "aws_key_pair" "myKey" {
  key_name   = "myKey"
  public_key = file("${var.PATH_TO_PUBLIC_KEY}")
}

resource "aws_instance" "example" {
  ami           = lookup(var.AMIS, var.AWS_REGION)
  instance_type = "t2.micro"
  key_name      = aws_key_pair.myKey.key_name #Allows us to login to instance by uploading our public key

  #VPC Subnet
  subnet_id     = aws_subnet.main-public-1.id
  security_groups = [aws_security_group.allow_ssh.id]
  provisioner "local-exec" {
    command = "echo ${aws_instance.example.private_ip} >> /tmp/private_ips.txt"
  }

  #userData
  user_data = data.template_cloudinit_config.cloudinit_example.rendered
}

resource "null_resource" "provision" {
  provisioner "file" {
    source      = "script.sh"
    destination = "/tmp/script.sh"
  }
  provisioner "remote-exec" {
    inline = [
      "chmod +x /tmp/script.sh",
      "sudo /tmp/script.sh"
    ]
  }
  connection {
    host        = aws_instance.example.public_ip
    user        = var.INSTANCE_USERNAME
    private_key = file("${var.PATH_TO_PRIVATE_KEY}") #Create keys using keygen - ssh-keygen -f mykey
    type        = "ssh"
    port        = 22
    timeout     = "10s"
  }
}

resource "aws_ebs_volume" "ebs-volume-1" {
  availability_zone = "eu-west-1a"
  size = 20
  type = "gp2" # General purpose storage, SSD
  tags = {
    Name = "extra volume data"
  }
}

resource "aws_volume_attachment" "ebs-volume-1-attachment" {
  device_name = var.INSTANCE_DEVICE_NAME
  volume_id = aws_ebs_volume.ebs-volume-1.id
  instance_id = aws_instance.example.id
}

output "ip" {
  value = aws_instance.example.public_ip
}