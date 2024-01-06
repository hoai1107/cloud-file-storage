#!/bin/bash

aws configure set aws_access_key_id "test" \
&& aws configure set aws_secret_access_key "test" \
&& aws configure set region "ap-south-1" \
&& aws configure set output "json"

aws --endpoint-url=http://localhost:4566 --region ap-south-1 s3 mb s3://file-bucket