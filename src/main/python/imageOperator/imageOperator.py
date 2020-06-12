import numpy as np
from cv2 import cv2


class imageOperator:

    def __init__(self):
        pass

    ## stacks images to be a single long image.
    def stackImages(self,image,imageArr, i = 1):

        img = np.concatenate((image, imageArr[i]), axis=1)
        if i == (len(imageArr) - 1):
            return img
        else:
            i +=1
            return self.stackImages(img, imageArr , i)

    ## sorting an array of 4 points, must be 2d points, from top left -> top Right -> bottom Right -> bottom Left;
    def SortPoints(self, points):

        sortedList = sorted(points, key=lambda x: x[1], reverse=False)
        if sortedList[0][0] > sortedList[1][0]:
            temp = sortedList[0]
            sortedList[0] = sortedList[1]
            sortedList[1] = temp

        if sortedList[2][0] > sortedList[3][0]:
            temp = sortedList[2]
            sortedList[2] = sortedList[3]
            sortedList[3] = temp

        return [sortedList[3], sortedList[2], sortedList[0], sortedList[1]]

    # makes a perspektive transformation using the inputs
    def perspectiveTransform(self, width,height,image, matrix):
        result = cv2.warpPerspective(image, matrix, (width, height))
        return result

    # the transformation matrix is a math matrix, with inforation about where things were, and were they are
    # going to be, after the transformation
    def getTransformMatrix(self,width, height, points):

            nPts = np.float32([
                [0, 0],
                [width - 1, 0],
                [width - 1, height - 1],
                [0, height - 1]
            ], dtype="float32")

            points = np.float32([points[0],points[1],points[2],points[3]])
            matrix = cv2.getPerspectiveTransform(points, nPts)
            return matrix

    # finds a "contour" for the board, using  the mask, and returns the best aproximation of a square.
    # asuming the largest square is the best one.
    def getBestSquareContour(self,mask):

        # finding external   contours with simple aproximation.
        contours, hiarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

        # if there are no Contours then end it.
        if len(contours) == 0:
            return [], False

        # the job now is to go through every contour and check wich ones can be simplyfied to a square.
        appr = []
        for contour in contours:

            # this is math that just works, dont question it, dont change it,
            # it is a nightmare to adjust to find the correct values.
            epsilon = cv2.arcLength(contour, True)
            approx = cv2.approxPolyDP(contour, 0.04 * epsilon, True)  # now we have a simplified polygon.

            if len(approx) == 4:  # if the aproximation is a square
                if contour.size > 200:  # and size is considerable.
                    appr.append(approx)

        contours = sorted(appr, key=lambda x: -cv2.contourArea(x))
        if (len(appr) == 0):
            return None, False
        else:
            return contours[0], True
