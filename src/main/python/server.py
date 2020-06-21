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

import json as json

import grpc

import proto.grpc_pb2 as grpc_pb2
import proto.grpc_pb2_grpc as grpc_pb2_grpc




class Greeter(grpc_pb2_grpc.GreeterServicer):

    def __init__(self):
        self.GUI = None
        self.GUIType = 0

    def SayHello(self, request, context):
        print('I got a message from \"%s\"!' % request.name)
        #TODO: make this inside an if statment depending on weather the user wants GUI og OpenCV

        print("request name: "+request.name)

        if(request.name == "ManGUI"):
            from GUI.ManGui import ManGUI
            self.GUI = ManGUI()
            self.GUIType = 1
        elif(request.name == "turnDrawstack"):
            from GUI.ManGuiTurnDrawstack import ManGUI
            self.GUI = ManGUI()
            self.GUIType = 1
        elif(request.name == "RevealCardGUI"):
            from GUI.ManGuiRevealCard import ManGUI

            h = json.dumps(
                {"DRAWSTACK": {"suit": "HEARTS", "rank": 2, "isFacedUp": "true"},
                 "SUITSTACKHEARTS": None,
                 "SUITSTACKCLUBS": None,
                 "SUITSTACKDIAMONDS": None,
                 "SUITSTACKSPADES": None,
                 "BUILDSTACK1": "inputDesired",
                 "BUILDSTACK2": {"suit": "SPADES", "rank": 4, "isFacedUp": "true"},
                 "BUILDSTACK3": {"suit": "DIAMONDS", "rank": 5, "isFacedUp": "true"},
                 "BUILDSTACK4": {"suit": "HEARTS", "rank": 6, "isFacedUp": "true"},
                 "BUILDSTACK5": {"suit": "CLUBS", "rank": 7, "isFacedUp": "true"},
                 "BUILDSTACK6": {"suit": "HEARTS", "rank": 9, "isFacedUp": "true"},
                 "BUILDSTACK7": {"suit": "HEARTS", "rank": 10, "isFacedUp": "true"}}
            )


            # self.GUI = ManGUI("d1,h2,,h4,,h6,h7,h8,ace1,,ace3,EE")
            print(h)
            self.GUI = ManGUI(h)
            self.GUIType = 1
        else:
            from GUI.OpenCv2 import ManGUI
            if self.GUIType == 0 or self.GUIType == 1 :
                self.GUI = ManGUI()
            self.GUIType = 2
            #print(x.run())


        b = self.GUI.run()
        #returnMSG ='Hello, %s! I\'m Python world! here is a msg for you:' % request.name
        returnMSG = b
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
