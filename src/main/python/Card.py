class Card():

    # Author : Hans
    def __init__(self, profile, exists):
        self.exists = exists
        self.profile = profile
        self.number = None
        self.color = None
        self.compCenter = None
        self.compPoints = None
        self.compContour = None
        self.counter = 1

        self.suit = ""
        self.rank = ""

    # Author : Hans
    def initiateCard(self, contour, points):
        self.compContour = contour
        self.compPoints = points
        return self

    # Author : Hans
    def setProfile(self, image):
        self.profile = image


"""class Card():


    def Card(self):
        self.cardProfileImage = None
        self.number= None
        self.color= None
        self.compCenter= None
        self.compPoints= None
        self.compContour= None
        self.counter = 1

    def initiateCard(self, image, contour, points):
        self.compContour = contour
        self.compPoints = points
        self.cardProfileImage = self.createProfile(image)
        return self

    def createProfile(self, image):
        x, y, w, h = cv2.boundingRect(self.compContour)

        p1 = (self.compPoints[0][0][0], self.compPoints[0][0][1])
        p2 = (self.compPoints[1][0][0], self.compPoints[1][0][1])
        p3 = (self.compPoints[2][0][0], self.compPoints[2][0][1])
        p4 = (self.compPoints[3][0][0], self.compPoints[3][0][1])

        image = cv2.circle(image, p1, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p2, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p3, 2, (255, 0, 0), 2)
        image = cv2.circle(image, p4, 2, (255, 0, 0), 2)

        nPts = np.float32([
            [0, 0],
            [400 - 1, 0],
            [400-1, 600 - 1],
            [0, 600 -1]
        ], dtype="float32")
        pts = np.float32([p2,p1,p4,p3])

        matrix = cv2.getPerspectiveTransform(pts, nPts)
        result = cv2.warpPerspective(image, matrix, (400,600))

        cv2.imshow("image", image)
        cv2.imshow("temp", result)

        return result
"""
