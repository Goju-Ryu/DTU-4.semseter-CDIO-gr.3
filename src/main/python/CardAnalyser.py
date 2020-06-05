from Card import Card
from ImageProcessor import ImageProcessor
import numpy as np
import cv2
proc = ImageProcessor()

class CardAnalyser:

    def __init__(self):
        self.rotatingColor = (255,0,0)
        self.imgProc = ImageProcessor()

    #finds everyting that can be reduced to a Square, and creates a "Card" object from it.
    def findCards(self, img_color):
        # href: https://www.youtube.com/watch?v=U70holNWQhI

        # getting threshhold image
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
                    cv2.drawContours(img_color, appr, apprIndex , (0, 0, 255), 1)
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

        # cv2.imshow("image", image)
        # cv2.imshow("temp", result)

        return result

