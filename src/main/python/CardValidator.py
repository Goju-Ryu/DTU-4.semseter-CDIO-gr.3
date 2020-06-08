### HER SKAL VI LAVE REEL BILLED GENKENDELSE
import cv2
import numpy as np
import os


class CardValidator:
    class Symbol:
        def __init__(self):
            self.img = []
            self.name = "[Symbol name here]"

    def __init__(self):
        self.compareSymbols = self.loadCompareSymbols()
        pass

    def setCardRankAndSuit(self, card):
        # Isolated cornor profile
        cardCornorProfile = card.profile[0:140, 0:70]
        cv2.imshow("cardCornorProfile", cardCornorProfile)

        # making the Threshold so we can use it to find the contours
        img = cv2.bitwise_not(cardCornorProfile)
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        _, thresh = cv2.threshold(gray, 127, 255, 0)

        # finding the contours, RETR_EXTERNAL = external figure contours. and CHAIN_APPROX_NONE means showing alll points
        contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

        i = 0
        results = []
        for contour in contours:
            # bounding recy is the smallest square that fits the contour.
            x, y, w, h = cv2.boundingRect(contour)

            p1 = (x, y)
            p2 = (x + w, y)
            p3 = (x + w, y + h)
            p4 = (x, y + h)

            # finding the perspective transformed image, then rotated.
            imageTrans, mask, succes = self.fourPointTransform([[p1], [p2], [p3], [p4]], gray)
            imageTrans = cv2.rotate(imageTrans, cv2.ROTATE_180)
            imageTrans = cv2.resize(imageTrans, (70, 125))

            cv2.imshow("nice" + str(i), imageTrans)
            i += 1

            # now give the image to see if it matches a prefinded standard for a symbol
            results.append(self.matchCard2(imageTrans))

        return None

    # Takes threshholded image over contour in card cornor
    def matchCard2(self, image):
        symbols = self.compareSymbols

        symbolName = "[Card rank name here]"

        # initial difference values. Less is better, as can be seen in comparison in following code
        bestMatchDiff = 10000

        for symbol in symbols:
            diff = int(np.sum(cv2.absdiff(image, symbol.img)) / 255)

            if diff < bestMatchDiff:
                bestMatchDiff = diff
                symbolName = symbol.name

        return symbolName

    def loadCompareSymbols(self):
        compareSymbols = []

        for symbol in ["Hearts", "Spades", "Clubs", "Diamonds", "Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
                       "Eight", "Nine", "Ten", "Jack", "Queen", "King"]:
            compareSymbol = self.Symbol()
            filename = symbol + ".jpg"
            compareSymbol.name = symbol
            filepath = os.path.dirname(os.path.abspath(__file__)) + "/Card_Imgs/"
            compareSymbol.img = cv2.imread(filepath + filename, cv2.IMREAD_GRAYSCALE)  # pic is 125*70 (before it was 100*70)

            compareSymbols.append(compareSymbol)

        return compareSymbols



    # __________________________ METHODS BELOW WILL BE IN I HELPER CLASS __________________________

    def SortPoints(self, p1, p2, p3, p4, image):

        points = [p4, p3, p2, p1]
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

    def fourPointTransform(self, pts, image):
        # Perspektive Transformation
        p1 = (pts[0][0][0], pts[0][0][1])
        p2 = (pts[1][0][0], pts[1][0][1])
        p3 = (pts[2][0][0], pts[2][0][1])
        p4 = (pts[3][0][0], pts[3][0][1])

        # sorting so theyre orderd clockwise.
        p1, p2, p3, p4 = self.SortPoints(p1, p2, p3, p4, image)

        width = image.shape[1]
        height = image.shape[0]
        # making the points translation values
        nPts = np.float32([
            [0, 0],
            [width - 1, 0],
            [width - 1, height - 1],
            [0, height - 1]
        ], dtype="float32")
        pts = np.float32([p2, p1, p4, p3])

        # making the Perspektive Transform.
        matrix = cv2.getPerspectiveTransform(pts, nPts)
        result = cv2.warpPerspective(image, matrix, (width, height))

        mask = None
        return result, mask, True
