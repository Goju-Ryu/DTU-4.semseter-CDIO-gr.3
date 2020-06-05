import cv2
import numpy as np


def editorWindow(self):
    pass

cap = cv2.VideoCapture("video_01.mp4")
while True:
    empt, img = cap.read()
    img = cv2.resize(img,(600,400))

    hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    # define range of blue color in HSV
    lower_blue = np.array([60, 60, 60])
    upper_blue = np.array([90, 230, 230])
    mask = cv2.inRange(hsv, lower_blue, upper_blue)

    cv2.imshow("img",mask)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        cap.release()
        break