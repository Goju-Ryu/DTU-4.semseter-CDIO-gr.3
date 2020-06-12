import time

import numpy as np
import cv2

from Card import Card
from imageOperator.imageOperator import imageOperator


class CardAnalyser:

    def __init__(self):
        self.imageOperator = imageOperator()

    def findCards(self, rowImages, rowImagesMasks):
        i = 0
        arr = []
        mrr = []
        cards = []
        for i in range(0,len(rowImages)):
            image = rowImages[i]
            mask = rowImagesMasks[i]

            contour, succes, mask1 = self.findContour(image,mask)

            mrr.append(mask1)
            if succes:

                p1 = (contour[0][0][0], contour[0][0][1])
                p2 = (contour[1][0][0], contour[1][0][1])
                p3 = (contour[2][0][0], contour[2][0][1])
                p4 = (contour[3][0][0], contour[3][0][1])
                points = [p1, p2, p3, p4]

                profileImg = self.imageOperator.perspectiveTransform(image, points)
                cards.append( Card( profileImg , True ) )
                arr.append( profileImg )
            else:
                cards.append( Card( None , False ))
                arr.append( image )
                pass

        return arr, mrr, cards


    def findContour(self,image,mask):

        # no need for a Threshold image because the mask is already there. from before.
        kernel = np.ones((5, 5), np.uint8)              # finding the Contours
        mask = cv2.dilate(mask, kernel, iterations=1)
        mask = cv2.erode(mask, kernel, iterations=1)
        mask = cv2.bitwise_not(mask)

        contours, _ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
        if len(contours) == 0:
            return [], False, None

        succes = False
        cardContour = []

        for contour in contours:

            # Simplify into Polygon.
            epsilon = cv2.arcLength(contour, True)
            approx = cv2.approxPolyDP(contour, 0.04 * epsilon, True)  # now we have a simplified polygon.

            if len(approx) == 4:  # if the aproximation is a square
                if contour.size > 200:  # and size is considerable.
                    cardContour.append(approx)
                    cv2.drawContours(image, approx, -1, (0, 0, 255), 1)
                    succes = True

        if succes:
            return cardContour[0],succes, mask
        else:
            return 0, False , mask
