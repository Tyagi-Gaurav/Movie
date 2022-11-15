resource "aws_key_pair" "myKey" {
  key_name   = "myKey"
  public_key = file("${var.PATH_TO_PUBLIC_KEY}")
}

resource "aws_instance" "example" {
  ami           = lookup(var.AMIS, var.AWS_REGION)
  instance_type = "t2.micro"
  key_name      = aws_key_pair.myKey.key_name #Allows us to login to instance by uploading our public key
  subnet_id     = aws_subnet.main-public-1.id
  security_groups = [aws_security_group.allow_ssh.id]
  provisioner "local-exec" {
    command = "echo ${aws_instance.example.private_ip} >> /tmp/private_ips.txt"
  }
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

output "ip" {
  value = aws_instance.example.public_ip
}