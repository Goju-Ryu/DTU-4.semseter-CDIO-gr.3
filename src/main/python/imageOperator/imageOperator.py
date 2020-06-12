import numpy as np


class imageOperator:

    def __init__(self):
        pass



    ## stacks images to be a single long image.
    def stackImages(self,image,imageArr, i = 1):

        img = np.concatenate((image, imageArr[i]), axis=1)
        if i == (len(imageArr) - 1):
            return img
        else:
            i +=1
            return self.stackImages(img, imageArr , i)
