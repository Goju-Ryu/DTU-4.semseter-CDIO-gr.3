### HER SKAL VI LAVE REEL BILLED GENKENDELSE
import cv2
import numpy as np
import os
from operator import attrgetter


class CardValidator:
    class Symbol:
        def __init__(self):
            self.img = []
            self.name = "[Symbol name here]"

    class MatchedSymbol:
        def __init__(self):
            self.bestMatchDiff = None
            self.symbolName = None

    def __init__(self):
        self.compareSymbols = self.loadCompareSymbols()
        pass

    def setCardRankAndSuit(self, card):
        if card.exists:
            # Isolated cornor profile
            profileDim = card.profile.shape
            print(profileDim)
            profileHeight = profileDim[0]
            profileWidth = profileDim[1]
            print("h: " + str(profileHeight))
            print("w: " + str(profileWidth))


            # cardCornorProfile = card.profile[0:140, 0:50]
            cardCornorProfile = card.profile[0:(int(profileHeight/3)), 0:int((profileWidth/4))]
            cv2.imshow("cardCornorProfile", cardCornorProfile)

            # making the Threshold so we can use it to find the contours
            img = cv2.resize(cardCornorProfile , (100, 280))
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            thresh = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 1)

            thresh = cv2.bitwise_not(thresh)
            kernel = np.ones((3, 3), np.uint8)
            thresh = cv2.erode(thresh, kernel, iterations=1)
            thresh = cv2.dilate(thresh, kernel, iterations=2)
            #cv2.imshow("erode",erosion)

            # finding the contours, RETR_EXTERNAL = external figure contours. and CHAIN_APPROX_NONE means showing alll points
            contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

            i = 0
            results = []
            for contour in contours:
                if contour.size > 40:
                    # bounding rect is the smallest square that fits the contour.
                    x, y, w, h = cv2.boundingRect(contour)

                    # fix for when the King and Queen contours mix with the cards middle-image-border, and gets out of proportion
                    if h > 230:
                        print("this contour height is ____________________________________ " + str(h))
                        h = 95
                        w -= 10

                    p1 = (x, y)
                    p2 = (x + w, y)
                    p3 = (x + w, y + h)
                    p4 = (x, y + h)

                    # condition for ignoring irrelevant contours, to minimize noise
                    if w > 20 and h > 20:

                        # finding the perspective transformed image, then rotated.

                        imageTrans, mask, succes = self.fourPointTransform([[p1], [p2], [p3], [p4]], thresh)
                        imageTrans = cv2.rotate(imageTrans, cv2.ROTATE_180)
                        imageTrans = cv2.flip(imageTrans, 1)
                        imageTrans = cv2.resize(imageTrans, (70, 125))

                        cv2.imshow("nice" + str(i), imageTrans)
                        i += 1

                        # now give the image to see if it matches a prefinded standard for a symbol
                        mSymbol = self.matchCard2(imageTrans)

                        # if mSymbol.symbolName is "Queen":
                        #     print("Queen" + str(h))
                        #
                        # if mSymbol.symbolName is "Jack":
                        #     print("Jack" + str(h))

                        results.append(mSymbol)
                        cv2.drawContours(img, contour, -1, (0, 255, 0), 1)
                        cv2.rectangle(img, p1, p3, (255, 0, 0), 2)

            cv2.imshow("cornor profile contours and boundingRects", img)
            # sorts the list of symbol results from smallest differense value to greatest
            sortedList = sorted(results, key=attrgetter('bestMatchDiff'))


            # takes the best two matches and saves them in a new list. These should be the rank and the suit for the card
            if len(sortedList) >= 2:
                bestTwoMatches = sortedList[0:2]
            else:
                bestTwoMatches = sortedList

            # for matches in bestTwoMatches:
            #     print(matches.symbolName + "  : " + str(matches.bestMatchDiff), end = "\n")
            # print("\n\n")

            matchNames = []
            for match in bestTwoMatches:
                matchNames.append(match.symbolName)

            if len(bestTwoMatches) == 2:
                return bestTwoMatches[0].symbolName, bestTwoMatches[1].symbolName
            if len(bestTwoMatches) == 1:
                return bestTwoMatches[0].symbolName, "no match"
            if len(bestTwoMatches) == 0:
                return "no match", "no match"
        # Takes threshholded image over contour in card cornor
        else:
            return "empty", "empty"
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

            matchedSymbol = self.MatchedSymbol()
            matchedSymbol.bestMatchDiff = bestMatchDiff
            matchedSymbol.symbolName = symbolName

        return matchedSymbol

    def loadCompareSymbols(self):
        compareSymbols = []

        for symbol in ["Hearts", "Spades", "Clubs", "Diamonds", "Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
                       "Eight", "Nine", "Ten", "Jack", "Queen", "King"]:
            compareSymbol = self.Symbol()
            filename = symbol + ".png"
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
