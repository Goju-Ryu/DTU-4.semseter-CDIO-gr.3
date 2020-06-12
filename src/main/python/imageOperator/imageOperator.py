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

    def perspectiveTransform(self, width,height,image, matrix):
        result = cv2.warpPerspective(image, matrix, (width, height))
        return result

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


