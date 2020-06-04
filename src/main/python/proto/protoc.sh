#!/usr/bin/env bash
# Script for gennerating grpc files in python
cd "${BASH_SOURCE%/*}"  ||  (echo "ERROR: Could not find \${BASH_SOURCE%/*}" && exit )
python -m grpc_tools.protoc -I../../proto --python_out=. --grpc_python_out=. ../../proto/grpc.proto
#      ^choose module to run               ^placement of output