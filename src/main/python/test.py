import numpy as np
import matplotlib.pyplot as plt
from cv2 import cv2

from skimage import measure


from BoardRuler import BoardRuler
from CardAnalyser import CardAnalyser
from Video import SVideo

rec = SVideo()
cardAnal = CardAnalyser()
ruler = BoardRuler()

while True:
    img = rec.getFrame()
    if cv2.waitKey(1) & 0xFF == ord('q'):
        rec.release()
        break

    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # define range of blue color in HSV
    lower_blue = np.array([50, 60, 60])
    upper_blue = np.array([90, 230, 230])
    mask = cv2.inRange(hsv, lower_blue, upper_blue)


    contours = measure.find_contours(mask, 0.8)

    n=0
    for contour in contours:
        print(n)
        contour = measure.subdivide_polygon(contour, degree=2, preserve_ends=True)
        contours[n] = measure.approximate_polygon(contour, tolerance=40)
        n +=1

    mask = cv2.cvtColor(mask, cv2.COLOR_GRAY2BGR)
    for contour in contours:
        for point in contour:

            image = cv2.circle(mask, (int(point[1]),int(point[0])), 2, (255, 0, 0), 2)



    cv2.imshow("mask",mask)
