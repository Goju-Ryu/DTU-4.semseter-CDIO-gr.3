#!/usr/bin/env bash
# Script for gennerating grpc files in python
cd "${BASH_SOURCE%/*}"  ||  (echo "ERROR: Could not find \${BASH_SOURCE%/*}" && exit )
#  ^go to relevant directory   ^or write an error and exit if failed

python -m grpc_tools.protoc --proto_path=../.. --python_out=.. --grpc_python_out=.. ../../proto/grpc.proto
#      ^choose module to run  ^homedir of proto   ^1st file out   ^2nd file out      ^proto to compile from