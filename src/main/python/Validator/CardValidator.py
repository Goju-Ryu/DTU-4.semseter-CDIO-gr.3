### HER SKAL VI LAVE REEL BILLED GENKENDELSE
import cv2
import numpy as np
import os
from operator import attrgetter
from imageOperator.imageOperator import imageOperator


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
        self.operator = imageOperator()
        pass

    def setCardRankAndSuit(self, card):
        if card.exists:
            # Isolated cornor profile
            # these are used to calculate the percentage values of the Corner Images.
            height = card.profile.shape[0]
            width  = card.profile.shape[1]

            # the image her is the corner image, where we are going to look for contours in.
            image = card.profile[0:(int(height/3.5)), 0:int((width/6))]
            cornerHeight = image.shape[0]
            cornerWidth  = image.shape[1]

            gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
            gray = cv2.bilateralFilter(gray, 9,10, 10)
            thresh = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 13, 1  )

            kernel = np.ones((2, 2), np.uint8)
            thresh = cv2.bitwise_not(thresh)
            thresh = cv2.erode(thresh, kernel, iterations=2)

            # finding the contours, RETR_EXTERNAL = external figure contours. and CHAIN_APPROX_NONE means showing alll points
            contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

            i = 0
            results = []
            for contour in contours:

                if contour.size > cornerHeight/7:

                    # bounding rect is the smallest square that fits the contour.
                    x, y, w, h = cv2.boundingRect(contour)
                    p1 = (x, y)
                    p2 = (x + w, y)
                    p3 = (x + w, y + h)
                    p4 = (x, y + h)

                    # condition for ignoring irrelevant contours, to minimize noise
                    if w > cornerWidth/5 and h > cornerHeight/14:
                        i += 1

                        # finding the perspective transformed image
                        sortedPts = self.operator.SortPoints([p1, p2, p3, p4])
                        matrix = self.operator.getTransformMatrix(w, h, sortedPts)
                        imageTrans = self.operator.perspectiveTransform(w, h, thresh, matrix)
                        imageTrans = cv2.resize(imageTrans, (70, 125))

                        # now give the image to see if it matches a prefinded standard for a symbol
                        mSymbol = self.matchCard2(imageTrans)
                        out = thresh.copy()
                        out = cv2.cvtColor(out,cv2.COLOR_GRAY2BGR)

                        #cv2.imshow(mSymbol.symbolName, imageTrans)

                        cv2.putText(out, mSymbol.symbolName, (0, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2, cv2.LINE_AA)
                        self.MASK = out

                        results.append(mSymbol)
                        cv2.drawContours(image, contour, -1, (0, 255, 0), 1)
                        cv2.rectangle(image, p1, p3, (255, 0, 0), 2)

            # sorts the list of symbol results from smallest differense value to greatest
            #cv2.imshow("cornor profile contours and boundingRects", image)
            sortedList = sorted(results, key=attrgetter('bestMatchDiff'))

            # takes the best two matches and saves them in a new list. These should be the rank and the suit for the card
            if len(sortedList) >= 2:
                bestTwoMatches = sortedList[0:2]
            else:
                bestTwoMatches = sortedList

            matchNames = []
            for match in bestTwoMatches:
                matchNames.append(match.symbolName)

            if len(bestTwoMatches) == 2:
                return bestTwoMatches[0].symbolName, bestTwoMatches[1].symbolName , self.MASK
            if len(bestTwoMatches) == 1:
                return bestTwoMatches[0].symbolName, "no match", self.MASK
            if len(bestTwoMatches) == 0:
                return "no match", "no match", self.MASK
        else:
            return "empty", "empty", self.MASK

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

        for symbol in ["Hearts", "Spades", "Clubs", "Diamonds", "1", "2", "3", "4", "5", "6", "7",
                       "8", "9", "10", "11", "12", "13"]:
            compareSymbol = self.Symbol()
            filename = symbol + ".png"
            compareSymbol.name = symbol
            filepath = os.path.dirname(os.path.abspath(__file__)) + "/Card_Imgs/"
            # filepath = os.path.dirname(os.path.abspath(__file__)) + "/Card_Imgs_mod/"
            compareSymbol.img = cv2.imread(filepath + filename, cv2.IMREAD_GRAYSCALE)  # pic is 125*70 (before it was 100*70)

            compareSymbols.append(compareSymbol)

        return compareSymbols



