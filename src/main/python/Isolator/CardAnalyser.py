import time

import numpy as np
import cv2

from Card import Card
from imageOperator.imageOperator import imageOperator

# has the responsibility to know about cards, and be able to find them as images.
class CardAnalyser:

    def __init__(self):
        self.imageOperator = imageOperator()

    # finds all card profiles in the sections, and returns the profile images
    def findCards(self, rowImages, rowImagesMasks):
        imageArray = []
        maskArray = []
        cards = []
        for i in range(0,len(rowImages)):

            # getting current images and making counters on theese
            image = rowImages[i]
            mask = rowImagesMasks[i]
            contour, succes = self.findContour(image,mask)
            maskArray.append(mask)

            if succes:

                # points needs to be retrieved from the various position they are placed in.
                p1 = (contour[0][0][0], contour[0][0][1])
                p2 = (contour[1][0][0], contour[1][0][1])
                p3 = (contour[2][0][0], contour[2][0][1])
                p4 = (contour[3][0][0], contour[3][0][1])
                points = self.imageOperator.SortPoints([p1, p2, p3, p4])

                # need these variables for a couple things.
                width = image.shape[1]
                height = image.shape[0]

                # this is a matrix you can think of as, where thing are to where things are going
                matrix = self.imageOperator.getTransformMatrix(width, height, points)

                # making the perspektive transforms
                profileImg = self.imageOperator.perspectiveTransform(width, height, image, matrix)

                cards.append( Card( profileImg,True ) )
                imageArray.append( profileImg )
            else:
                cards.append( Card( None , False ))
                imageArray.append( image )
                pass

        return imageArray, maskArray, cards

    # finds a "contour" for the board, using  the mask, and returns the best aproximation of a square.
    # asuming the largest square is the best one.
    def findContour(self,image,mask):

        # reducing the noise in the image, the kernel is a square of area where each iteration
        # of the coming operations take place
        kernel = np.ones((5, 5), np.uint8)              # finding the Contours
        mask = cv2.dilate(mask, kernel, iterations=1)
        mask = cv2.erode(mask, kernel, iterations=1)

        return self.imageOperator.getBestSquareContour(mask)

