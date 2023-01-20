provider "cloudinit" {}

data "template_file" "init_script" {
  template = file("scripts/init.cfg")
  vars = {
    region = var.AWS_REGION
  }
}


data "template_file" "shell_script" {
  template = file("scripts/volumes.sh")
  vars = {
    DEVICE = var.INSTANCE_DEVICE_NAME
  }
}

data "template_cloudinit_config" "cloudinit_example" {
  gzip = false
  base64_encode = false


  part {
    filename = "init.cfg"
    content_type = "text/cloud-config"
    content = "data.template_file.init_script.rendered "
  }

  #just a shell script instead of
  part {
    content = data.template_file.shell_script.rendered
    content_type = "text/x-shellscript"
  }
}