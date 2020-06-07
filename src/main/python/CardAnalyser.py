import time

from Card import Card
from ImageProcessor import ImageProcessor
import numpy as np
import cv2
proc = ImageProcessor()

class CardAnalyser:

    def findCards(self, rowImages, rowImagesMasks):
        i = 0
        arr = []
        Srr = []
        mrr = []
        for i in range(0,len(rowImages)):
            image = rowImages[i]
            mask = rowImagesMasks[i]

            contour, succes, mask1 = self.findContour(image,mask)

            mrr.append(mask1)
            if succes:
                arr.append( self.perspectiveTransform(image,contour))
                Srr.append(True)
            else:
                arr.append( image )
                Srr.append(False)
                pass

        return Srr, arr, mrr

    def findContour(self,image,mask):

        # no need for a Threshold image because the mask is already there. from before.
        kernel = np.ones((5, 5), np.uint8)              # finding the Contours
        mask = cv2.dilate(mask, kernel, iterations=1)
        mask = cv2.erode(mask, kernel, iterations=1)
        mask = cv2.bitwise_not(mask)

        contours, _ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
        if len(contours) == 0:
            return [], False

        succes = False
        cardContour = []

        for contour in contours:

            """for point in contour:
                image = cv2.circle(image, (point[0][0], point[0][1]), 2, (255, 0, 0), 1)
            """
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
            return None,False, mask

    def perspectiveTransform(self, image,points):
        # Perspektive Transformation
        p1 = (points[0][0][0], points[0][0][1])
        p2 = (points[1][0][0], points[1][0][1])
        p3 = (points[2][0][0], points[2][0][1])
        p4 = (points[3][0][0], points[3][0][1])

        # sorting so theyre orderd clockwise.
        p1, p2, p3, p4 = self.SortPoints(p1, p2, p3, p4)

        width = image.shape[1]
        height = image.shape[0]
        # making the points translation values
        nPts = np.float32([
            [0, 0],
            [width - 1, 0],
            [width - 1, height - 1],
            [0, height - 1]
        ], dtype="float32")
        points = np.float32([p2, p1, p4, p3])

        # making the Perspektive Transform.
        matrix = cv2.getPerspectiveTransform(points, nPts)
        result = cv2.warpPerspective(image, matrix, (width, height))

        return result

    def SortPoints(self, p1, p2 , p3 , p4):

        points = [p4,p3,p2,p1]
        sortedList = sorted(points, key=lambda x: x[1], reverse=False)
        if sortedList[0][0] > sortedList[1][0]:
            temp = sortedList[0]
            sortedList[0] = sortedList[1]
            sortedList[1] = temp

        if sortedList[2][0] > sortedList[3][0]:
            temp = sortedList[2]
            sortedList[2] = sortedList[3]
            sortedList[3] = temp

        return sortedList[3], sortedList[2], sortedList[0], sortedList[1]

    def findCardsOld(self, img_color):

        thresh_image = proc.getThreshImage(img_color)
        cv2.imshow("tresh",thresh_image)

        # this is a command reference several places to do it this way. that finds contours.
        contours, _ = cv2.findContours(thresh_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        # if nothing is found, then return that result.
        if len(contours) == 0:
            return [], False

        cards = []
        for i in range(len(contours)):

            # reduces the contour vertices, with strange constants. that somehow works, look at reference.
            peri = cv2.arcLength(contours[i], True)
            approx = cv2.approxPolyDP(contours[i], 0.04 * peri, True) # now we have a simplified polygon.
            pts = np.float32(approx)   # creates the polygon into vertices, in order m upper left to right to down.

            apprIndex = 0
            appr = []
            # if the vertices ammount is 4. then we count it as a card.

            if len(approx) == 4:
                appr.append(approx)
                if contours[i].size > 25  :
                    # for each square we find we create a Card object.
                    card = Card()
                    card.initiateCard(contours[i], pts)

                    # getting the profile image and setting it
                    profile = self.createProfile(pts, img_color)
                    card.setProfile(profile)

                    # adding to list.
                    cards.append(card)

                    # drawing contours
                    cv2.drawContours(img_color, appr, apprIndex , (144, 244, 155), 1)
                    apprIndex += 1

        cv2.imshow("contours" ,img_color)
        return cards, True

    def createProfile(self,pts, image):

        p1 = (pts[0][0][0], pts[0][0][1])
        p2 = (pts[1][0][0], pts[1][0][1])
        p3 = (pts[2][0][0], pts[2][0][1])
        p4 = (pts[3][0][0], pts[3][0][1])

        """#Setting marks on the image for where the found corners are 
        image = cv2.circle(image, p1, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p2, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p3, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p4, 2, (255, 0, 0), 2)"""

        # creating two sets of points, one is the set of New points upper left corner, upper right, lower left, lower right. the other the tracked points.
        nPts = np.float32([
            [0, 0],
            [400 - 1, 0],
            [400-1, 600 - 1],
            [0, 600 -1]
        ], dtype="float32")
        pts = np.float32([p2,p1,p4,p3])

        matrix = cv2.getPerspectiveTransform(pts, nPts)
        result = cv2.warpPerspective(image, matrix, (400,600))


        return result

