import cv2
import numpy as np

class BoardRuler:

    def isolate(self, img):
        image = cv2.GaussianBlur(img, (5,5), 0)

        #Creating a Mask
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
        lower_blue = np.array([50, 60, 60])
        upper_blue = np.array([90, 230, 230])
        mask = cv2.inRange(hsv, lower_blue, upper_blue)

        #noise Removal on Mask
        kernel = np.ones((5,5),np.uint8)

        #cv2.imshow("erode",erosion)
        mask1 = cv2.erode(mask, kernel, iterations=1)
        mask1 = cv2.dilate(mask1, kernel, iterations=2)
        mask1 = cv2.erode(mask1, kernel, iterations=1)
        #cv2.imshow("dilation", dilation)
        #bmask = cv2.GaussianBlur(mask, (3, 3), 0)
        pts, succes = self.findBoardContour(mask1, image)

        if succes:
            # Perspektive Transformation
            p1 = (pts[0][0][0], pts[0][0][1])
            p2 = (pts[1][0][0], pts[1][0][1])
            p3 = (pts[2][0][0], pts[2][0][1])
            p4 = (pts[3][0][0], pts[3][0][1])

            #sorting so theyre orderd clockwise.
            p1,p2,p3,p4 = self.SortPoints(p1,p2,p3,p4 , image)

            width = image.shape[1]
            height = image.shape[0]
            #making the points translation values
            nPts = np.float32([
                [0, 0],
                [width - 1, 0],
                [width - 1, height - 1],
                [0, height - 1]
            ], dtype="float32")
            pts = np.float32([p2, p1, p4, p3])

            # making the Perspektive Transform.
            matrix = cv2.getPerspectiveTransform(pts, nPts)
            result = cv2.warpPerspective(img, matrix, (width, height))
            mask = cv2.warpPerspective(mask, matrix, (width, height))

            return result ,mask, True
        else:
            return None , mask , False

    def findBoardContour(self, mask, image):

        # finding external   contours with simple aproximation.
        contours, hiarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

        # if there are no Contours then end it.
        if len(contours) == 0:
            return [], False

        for contour in contours:
            # Simplify into Polygon.
            
            """ box = cv2.convexHull(contour)
            box2 = self.extreme_points(box)"""
            epsilon = cv2.arcLength(contour, True)
            val = 0.04
            approx = cv2.approxPolyDP(contour, 0.04 * epsilon, True)  # now we have a simplified polygon.

            appr = []
            if len(approx) == 4: # if the aproximation is a square
                if contour.size > 200: # and size is considerable.
                    appr.append(approx)
                    #cv2.drawContours(mask, appr, -1, (144, 247, 155), 1)

        # when done we sort to find the largest contour.
        #cv2.imshow("im",mask)

        contours = sorted(contours, key=lambda x: cv2.contourArea(x))
        if (len(appr) == 0):
            return None, False
        else:
            return appr[0], True

    def SortPoints(self, p1, p2 , p3 , p4, image):

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


        for point in sortedList:
            image = cv2.circle(image, sortedList[0], 2, (255, 0, ), 2)
            image = cv2.circle(image, sortedList[1], 2, (0, 255, 255), 2)
            image = cv2.circle(image, sortedList[2], 2, (255, 0, 255), 2)
            image = cv2.circle(image, sortedList[3], 2, (255, 255, 255), 2)
        return sortedList[3], sortedList[2], sortedList[0], sortedList[1]

    def extreme_points(self, contour):
        #kilde
        #https://books.google.dk/books?id=w86PDwAAQBAJ&pg=PA228&lpg=PA228&dq=openCV+reduce+oply+to+4+points&source=bl&ots=q1J4Hk58wc&sig=ACfU3U1Y4r22M0HokjOUKBunPM_IYZ2CLA&hl=da&sa=X&ved=2ahUKEwigy56MzO_pAhVqkIsKHVXWD0UQ6AEwAHoECAgQAQ#v=onepage&q=openCV%20reduce%20oply%20to%204%20points&f=false

        extLeft = tuple(contour[contour[:, :, 0].argmin()][0])
        extRight = tuple(contour[contour[:, :, 0].argmax()][0])
        extTop = tuple(contour[contour[:, :, 1].argmin()][0])
        extBot = tuple(contour[contour[:, :, 1].argmax()][0])
        
        return [extLeft, extRight, extTop ,extBot]
        
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

        # cv2.imshow("res",image)
        top = 28.15 * heightPercent
        bot = 27.18 * heightPercent
        space = 14.28 * widthPercent
        image = self.decorateImageRulerLines(image)

        i=1
        arrTop =[]
        arrBot =[]

        #Cropping Top
        endH = int(top)
        for i in range (0,7):  #crop_img = img[y:y+h, x:x+w]

            startS = int((i) * space)
            endS = int((i + 1) * space)
            if endS > width:
                endS = width
            arrTop.append( image[0:endH, startS:endS])

        #Cropping Bottom
        startH = int((height - bot))
        for i in range (0,7):

            startS = int((i) * space)
            endS = int((i + 1) * space)

            if endS > width:
                endS = width
            arrBot.append(image[startH:(height), startS:endS])

        return arrTop , arrBot
