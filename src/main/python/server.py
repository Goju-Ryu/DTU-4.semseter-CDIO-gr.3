# Copyright 2015 gRPC authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""The Python implementation of the GRPC helloworld.Greeter server."""
from concurrent import futures
import logging

import grpc

import proto.grpc_pb2 as grpc_pb2
import proto.grpc_pb2_grpc as grpc_pb2_grpc


class Greeter(grpc_pb2_grpc.GreeterServicer):

    def SayHello(self, request, context):
        print('I got a message from \"%s\"!' % request.name)
        #TODO: make this inside an if statment depending on weather the user wants GUI og OpenCV
        if(request.name == "Java world"):
            from GUI.ManGui import ManGUI
            x = ManGUI()
        else:
            pass #TODO: call openCV here
        b = x.run()
        returnMSG ='Hello, %s! I\'m Python world! here is a msg for you:' % request.name
        returnMSG += b
        return grpc_pb2.HelloReply(message=returnMSG)#request.name


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    grpc_pb2_grpc.add_GreeterServicer_to_server(Greeter(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    print("Hello World from python")
    logging.basicConfig()
    serve()
