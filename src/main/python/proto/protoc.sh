#!/usr/bin/env bash
# Script for gennerating grpc files in python
python -m grpc_tools.protoc -I../../proto --python_out=. --grpc_python_out=. ../../proto/grpc.proto
#      ^choose module to run               ^placement of output