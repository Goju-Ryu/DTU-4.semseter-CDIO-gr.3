import cv2
import numpy as np

class BoardRuler:



    #nnybranch!
    def decorateImageRulerLines(self, image):

        height = image.shape[0]
        width  = image.shape[1]

        widthPercent = width/100
        heightPercent= height/100

        #cv2.rectangle(image, ( int(4*widthPercent*14),0 ), ( width , int(heightPercent * 30) ), (0, 0, 255),-1)
        color = (0, 255, 0)
        p1 = (0, int(heightPercent * 35))
        p2 = (width, int(heightPercent * 35))
        image = cv2.line(image, p1, p2, color, 1)

        i = 0
        for i in range ( 0,7):
            point1= ( int(widthPercent*(14.28)*(i+1)) , 0 )
            point2= ( int(widthPercent*(14.28)*(i+1)) , height )
            image = cv2.line(image, point1, point2, color, 1 )

        cv2.imshow("ruler", image)

    def cutImageWithRulerLines(self, image):
        height = image.shape[0]
        width = image.shape[1]

        widthPercent = width / 100
        heightPercent = height / 100
        LinesVer = {int(heightPercent * 35), int(heightPercent * 35) }

    def isolate(self, img):

        # Get the Green Color
        image = cv2.GaussianBlur(img, (11, 11), 0)
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

        # define range of blue color in HSV
        lower_blue = np.array([35,50,50])
        upper_blue = np.array([105,255,255])
        # Threshold the HSV image to get only blue colors
        mask = cv2.inRange(hsv, lower_blue, upper_blue)

        # Bitwise-AND mask and original image
        #res = cv2.bitwise_and(image, image, mask=mask)
        #cv2.imshow("Mask", mask)
        #cv2.imshow("img",image)
        # Create a Contour for that.
        # return a Cuttet image.


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
            result = cv2.warpPerspective(image, matrix, (600, 400))

            cv2.imshow("res",result)

    def findBoardContour (self, mask,  image):


        cv2.imshow("mask", mask)
        contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL ,cv2.CHAIN_APPROX_SIMPLE)
        if len(contours) == 0:
            return [], False
        # sorting contours for size
        Sorted = sorted(range(len(contours)), key=lambda i: cv2.contourArea(contours[i]), reverse=True)

        for i in range(len(Sorted)):

            # reduces the contour vertices, with strange constants. that somehow works, look at reference.
            epsilon = cv2.arcLength(contours[i], True)
            approx = cv2.approxPolyDP(contours[i], 0.1 * epsilon, True)  # now we have a simplified polygon.

            appr = []
            apprIndex = 0

            opr = []
            opr.append(approx)
            cv2.drawContours(image, contours, i, (255, 0, 0), 5)
            cv2.drawContours(image, opr, 0, (0, 255, 0), 5)

            if len(approx) == 4:
                if contours[i].size > 200:
                    print(contours[i].size)
                    # drawing contours
                    appr.append(approx)
                    cv2.drawContours(mask, contours, i, (100, 100, 100), 10)

                    cv2.drawContours(image, appr, apprIndex, (0, 0, 255), 10)
                    apprIndex += 1
            #cv2.imshow("contourmask", mask)
            cv2.imshow("contour",image)


            if (len(appr) != 1):
                return None, False
            else:
                return appr[0],True
