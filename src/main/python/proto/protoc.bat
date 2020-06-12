cd %~dp0  ||  (echo "ERROR: Could not find \${BASH_SOURCE%/*}" && exit )
python -m grpc_tools.protoc --proto_path=..\\.. --python_out=.. --grpc_python_out=.. ..\\..\\proto\\grpc.proto
