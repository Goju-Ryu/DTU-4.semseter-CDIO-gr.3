
#Look into using this when everything else works.

#====================================
# This file contains instructions for what a Ui must contain
# It works much like an interface in java.
# all the classes wishing to function as a UI
# must thus inherit this "class"
#====================================
from abc import ABC, abstractmethod

class AbstractUI():
    @abstractmethod
    def foo(self):
        pass