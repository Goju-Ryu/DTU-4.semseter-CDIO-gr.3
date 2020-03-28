import org.opencv.highgui.VideoCapture;

public class main {
    public static void main(String[] args) {
        nu.pattern.OpenCV.loadLibrary();
        System.out.println("hej");
        VideoCapture vid = new VideoCapture(0);
    }
}
