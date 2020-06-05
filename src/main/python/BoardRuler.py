import cv2

class BoardRuler:
    def decorateImageRulerLines(self, image):

        height = image.shape[0]
        width = image.shape[1]

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

        CardArray = {}

        i = 0
        for i in range(0 , 6):
            pass
            #CardArray.append(image[start_row:end_row, start_col:end_col])

        i = 6
        for i in range(0 , 14):
            cv2.imshow(str(i), image)

        pass