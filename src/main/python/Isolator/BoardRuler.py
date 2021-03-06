import cv2
import numpy as np

from imageOperator.imageOperator import imageOperator


# this class has the responsibility to know about the board syntax, where there are supose to be cards
# placed, then it holds methods to isolate the board, and decorate it with ruler lines.
class BoardRuler:

    #constructor
    def __init__(self):
        self.imageOperator = imageOperator()

    #isolate the board from the background using the initial settings.
    def isolate(self, img, Settings):

        image = cv2.GaussianBlur(img, (5,5), 0)

        #getting the settings values.
        HueMin, HueMax = Settings.getHue()
        SatMin, SatMax = Settings.getSat()
        ValMin, ValMax = Settings.getVal()

        # creating the mask
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        lower_blue = np.array([HueMin, SatMin, ValMin])
        upper_blue = np.array([HueMax, SatMax, ValMax])
        mask = cv2.inRange(hsv, lower_blue, upper_blue)

        #a kernel is used in erode and dilate, as the area is operation fills.
        kernel = np.ones((5,5),np.uint8)

        pts, succes = self.imageOperator.getBestSquareContour(mask)

        if succes:
            # Getting a Point Matrix.
            p1 = (pts[0][0][0], pts[0][0][1])
            p2 = (pts[1][0][0], pts[1][0][1])
            p3 = (pts[2][0][0], pts[2][0][1])
            p4 = (pts[3][0][0], pts[3][0][1])
            points  = self.imageOperator.SortPoints([p1,p2,p3,p4])

            # need these variables for a couple things.
            width   = image.shape[1]
            height  = image.shape[0]

            # this is a matrix you can think of as, where thing are to where things are going
            matrix  = self.imageOperator.getTransformMatrix(width, height, points)

            # making the perspektive transforms
            result  = self.imageOperator.perspectiveTransform(width,height,image,matrix)
            mask    = self.imageOperator.perspectiveTransform(width,height,mask,matrix)

            return result ,mask, True
        else:
            return None , mask , False

    #draws lines one the image for where we want to place images.
    def decorateImageRulerLines(self, image):

        height = image.shape[0]
        width = image.shape[1]

        widthPercent = width / 100
        heightPercent = height / 100

        top = 28.15
        bot = 27.18
        space = 14.28

        color = (0,0,0)

        # draw the first line on the image.
        point1 = (0, int(heightPercent * (top)))
        point2 = (width, int(heightPercent * (top)))
        image = cv2.line(image, point1, point2, color, 1)

        # draw the second line on the image.
        point1 = (0, int(heightPercent * (100 - bot)))
        point2 = (width, int(heightPercent * (100 - bot)))
        image = cv2.line(image, point1, point2, color, 1)

        i = 0
        # for each vertical line we want, draw it.
        for i in range(0, 7):
            point1 = (int(widthPercent * (space * i)), 0)
            point2 = (int(widthPercent * (space * i)), width)
            image = cv2.line(image, point1, point2,color, 1)

        return image

    # cuts the image into section arrays.
    def cutImageWithRulerLines(self, image):


        height = image.shape[0]
        width = image.shape[1]

        widthPercent = width / 100
        heightPercent = height / 100

        top = 28.15 * heightPercent
        bot = 27.18 * heightPercent
        space = 14.28 * widthPercent

        arrTop =[]
        arrBot =[]

        #Cropping Top Images out
        endH = int(top)
        for i in range (0,7):  #crop_img = img[y:y+h, x:x+w]

            startS = int((i) * space)
            endS = int((i + 1) * space)
            if endS > width:
                endS = width
            arrTop.append( image[0:endH, startS:endS])

        #Cropping Bottom images out
        startH = int((height - bot))
        for i in range (0,7):

            startS = int((i) * space)
            endS = int((i + 1) * space)

            if endS > width:
                endS = width
            arrBot.append(image[startH:(height), startS:endS])

        return arrTop , arrBot
