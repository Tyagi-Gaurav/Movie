resource "aws_route53_zone" "gt_training_academy" {
  name = "gt.training.academy"
}

resource "aws_route53_record" "server1-record" {
  name    = "server1.gt.training.academy"
  type    = "A" #Resolving to IP Address
  ttl = "300"
  zone_id = aws_route53_zone.gt_training_academy.zone_id
  records = ["104.236.247.8"] #Could use elastic IP address
}

resource "aws_route53_record" "www-record" {
  name    = "www.gt.training.academy"
  type    = "A" #Resolving to IP Address
  ttl = "300"
  zone_id = aws_route53_zone.gt_training_academy.zone_id
  records = ["104.236.247.8"]
}

resource "aws_route53_record" "mail1-record" {
  name    = "gt.training.academy"
  type    = "MX" #Resolving to IP Address
  ttl = "300"
  zone_id = aws_route53_zone.gt_training_academy.zone_id
  records = [
    "1 aspmx.l.google.com.",
    "5 alt1.aspmx.l.google.com.",
    "5 alt2.aspmx.l.google.com.",
    "10 aspmx2.googlemail.com.",
    "10 aspmx3.googlemail.com.",
  ]
}

output "ns-servers" {
  value = aws_route53_zone.gt_training_academy.name_servers
}