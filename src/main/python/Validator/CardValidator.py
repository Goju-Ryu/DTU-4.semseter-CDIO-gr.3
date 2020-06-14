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
            profileDim = card.profile.shape
            print(profileDim)
            profileHeight = profileDim[0]
            profileWidth = profileDim[1]
            print("h: " + str(profileHeight))
            print("w: " + str(profileWidth))


            # cardCornorProfile = card.profile[0:140, 0:50]
            # cardCornorProfile = card.profile[0:(int(profileHeight/3)), 0:int((profileWidth/4))]
            cardCornorProfile = card.profile[0:(int(profileHeight/3.5)), 0:int((profileWidth/6))]
            cv2.imshow("cardCornorProfile", cardCornorProfile)


            cornorProfileDim = cardCornorProfile.shape
            cornorProfileHeight = cornorProfileDim[0]
            cornorProfileWidth = cornorProfileDim[1]

            # making the Threshold so we can use it to find the contours
            # img = cv2.resize(cardCornorProfile, (cornorProfileWidth/2, cornorProfileHeight/2))
            # img = cv2.resize(cardCornorProfile, (cornorProfileWidth*2, cornorProfileHeight*2))
            # img = cv2.resize(cardCornorProfile, (100, 280))
            img =  cardCornorProfile

            cv2.imshow("cardCornorResized", img)
            gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
            thresh = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 11, 1)

            thresh = cv2.bitwise_not(thresh)
            kernel = np.ones((2, 2), np.uint8)
            thresh = cv2.erode(thresh, kernel, iterations=1)
            # thresh = cv2.dilate(thresh, kernel, iterations=1)
            #cv2.imshow("erode",erosion)

            # finding the contours, RETR_EXTERNAL = external figure contours. and CHAIN_APPROX_NONE means showing alll points
            contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

            i = 0
            results = []
            for contour in contours:
                print("contour size: " + str(contour.size))
                # if contour.size > 40:
                if contour.size > cornorProfileHeight/7:

                    # bounding rect is the smallest square that fits the contour.
                    x, y, w, h = cv2.boundingRect(contour)

                    # fix for when the King and Queen contours mix with the cards middle-image-border, and gets out of proportion
                    # if h > 230:
                    #     print("this contour height is ____________________________________ " + str(h))
                    #     h = 95
                    #     w -= 10

                    p1 = (x, y)
                    p2 = (x + w, y)
                    p3 = (x + w, y + h)
                    p4 = (x, y + h)

                    # condition for ignoring irrelevant contours, to minimize noise
                    if w > cornorProfileWidth/5 and h > cornorProfileHeight/14:

                        # finding the perspective transformed image
                        sortedPts = self.operator.SortPoints([p1, p2, p3, p4])
                        matrix = self.operator.getTransformMatrix(w, h, sortedPts)
                        imageTrans = self.operator.perspectiveTransform(w, h, thresh, matrix)
                        imageTrans = cv2.resize(imageTrans, (70, 125))

                        # cv2.imshow("nice" + str(i), imageTrans)
                        i += 1

                        # now give the image to see if it matches a prefinded standard for a symbol
                        mSymbol = self.matchCard2(imageTrans)

                        # if
                        # cv2.imshow("chaos here" + str(h), imageTrans)
                        # if mSymbol.symbolName == "10":
                        #     print("10" + str(h))
                        #     cv2.imshow("10", imageTrans)
                        #
                        # if mSymbol.symbolName == "11":
                        #     print("11" + str(h))
                        #     cv2.imshow("11", imageTrans)

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


