import cv2
import numpy as np

class ImageProcessor:

    def __init__(self):
        self.variable = 12

    def getThreshImage(self, image):
        image = cv2.GaussianBlur(image, (11, 11), 0)
        hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

        # define range of blue color in HSV
        lower_blue = np.array([30, 80, 60])
        upper_blue = np.array([112, 230, 230])
        # Threshold the HSV image to get only blue colors
        mask = cv2.inRange(hsv, lower_blue, upper_blue)
        return mask


    def getThreshImageOld(self,image):
        # color conversion, because different types of images are needed for operations not to crash
        image = cv2.cvtColor(image, cv2.COLOR_BGRA2BGR)
        img = cv2.cvtColor(image, cv2.COLOR_BGRA2GRAY)

        # equalising is spreading out the amount of pixel values to be more evenly spread,
        thr = cv2.equalizeHist(img)
        _,thr = cv2.threshold(thr, 220, 255, cv2.THRESH_BINARY)

        # color conversion, because different types of images are needed for operations not to crash
        # adaptive threshold is thresholding not from the overall image but smaller parts.
        thr = cv2.cvtColor(thr, cv2.COLOR_GRAY2BGR)
        img = cv2.adaptiveThreshold(img, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 115, 2)
        img = cv2.cvtColor(img, cv2.COLOR_GRAY2BGR)

        # creating a Mask
        masked = cv2.bitwise_and(thr, img)
        masked = cv2.bitwise_and(image, masked)
        masked =  cv2.cvtColor(masked, cv2.COLOR_BGRA2GRAY)

        return masked

