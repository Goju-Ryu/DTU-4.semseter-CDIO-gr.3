import cv2
import numpy as np

class BoardRuler:



    #nnybranch!
    def decorateImageRulerLines(self, image):
        height = image.shape[0]
        width = image.shape[1]

        widthPercent = width / 100
        heightPercent = height / 100

        top = 28.15
        bot = 27.18
        space = 14.28

        color = (0,0,0)
        # draw the first line on the image
        point1 = (0, int(heightPercent * (top)))
        point2 = (width, int(heightPercent * (top)))
        image = cv2.line(image, point1, point2, color, 1)

        point1 = (0, int(heightPercent * (100 - bot)))
        point2 = (width, int(heightPercent * (100 - bot)))
        image = cv2.line(image, point1, point2, color, 1)

        i = 0
        for i in range(0, 7):
            point1 = (int(widthPercent * (space * i)), 0)
            point2 = (int(widthPercent * (space * i)), width)
            image = cv2.line(image, point1, point2,color, 1)

        return image

    def cutImageWithRulerLines(self, image):
        height = image.shape[0]
        width = image.shape[1]

        widthPercent = width / 100
        heightPercent = height / 100

        top = 28.15
        bot = 27.18
        space = 14.28
        image = self.decorateImageRulerLines(image)
        #cv2.imshow("res",image)

        i=0
        arr = []
        for i in range (0,7):
            im = cv2.getRectSubPix(image, (98, 33), (1, 1))



    def isolate(self, img):

        # Get the Green Color
        image = cv2.GaussianBlur(img, (11, 11), 0)
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

        # define range of blue color in HSV
        lower_blue = np.array([60,60,60])
        upper_blue = np.array([132,230,230])
        # Threshold the HSV image to get only blue colors
        mask = cv2.inRange(hsv, lower_blue, upper_blue)



        pts, succes = self.findBoardContour(mask, image)
        if succes:
            p1 = (pts[0][0][0], pts[0][0][1])
            p2 = (pts[1][0][0], pts[1][0][1])
            p3 = (pts[2][0][0], pts[2][0][1])
            p4 = (pts[3][0][0], pts[3][0][1])

            nPts = np.float32([
                [0, 0],
                [600 - 1, 0],
                [600 - 1, 400 - 1],
                [0, 400 - 1]
            ], dtype="float32")
            pts = np.float32([p2, p1, p4, p3])

            matrix = cv2.getPerspectiveTransform(pts, nPts)
            result = cv2.warpPerspective(img, matrix, (600, 400))


            return result , True
        else:
            return None, False

    def findBoardContour (self, mask,  image):

        #cv2.imshow("mask", mask)
        contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL ,cv2.CHAIN_APPROX_SIMPLE)
        if len(contours) == 0:
            return [], False

        # sorting contours for size
        Sorted = sorted(range(len(contours)), key=lambda i: cv2.contourArea(contours[i]), reverse=True)

        for i in range(len(Sorted)):

            # reduces the contour vertices, with strange constants. that somehow works, look at reference.
            epsilon = cv2.arcLength(contours[i], True)
            approx = cv2.approxPolyDP(contours[i], 0.04 * epsilon, True)  # now we have a simplified polygon.

            appr = []
            apprIndex = 0
            if len(approx) == 4:
                if contours[i].size > 400:
                    print(contours[i].size)
                    # drawing contours
                    appr.append(approx)
                    cv2.drawContours(mask, contours, i, (100, 100, 100), 10)
                    cv2.drawContours(image, appr, apprIndex, (144, 247, 155), 10)
                    apprIndex += 1
            else:

                cv2.drawContours(image, contours, i, (255, 0, 0), 1)
                opr = []
                opr.append(approx)

                cv2.drawContours(image, opr, 0, (0, 255, 0), 1)

        #cv2.imshow("CONTOUR", image)
        if (len(appr) != 1):
            return None, False
        else:
            return appr[0],True
