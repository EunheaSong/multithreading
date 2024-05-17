package johnDoe;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageColorChanger {
    // & : AND 연산자
    // | : OR 연산자. |= : OR 연산하여 좌측 항에 대입.

    /*
    이미지의 하얀 부분을 보라색으로 변경해주는 예제.
     */

    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/resources/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        recolorSingleThreaded(originalImage, resultImage);

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftConner, int topConner,
                                    int width, int height) {
        for (int x = leftConner; x < leftConner + width && x < originalImage.getWidth(); x++) {
            for (int y = topConner; y < topConner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }
    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRgb = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRgb);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        //예제에서는 30미만이면 회색 계열이라고 판단하기로 함.
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    //rgb 색상 값은 16진수로 표현할 수 있다.
    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

    public static int getGreen(int rgb) {
        //녹색 채널 값은 8-15번째 비트에 위치하므로, 이를 추출하기 위해 전체 값을 오른쪽으로 8비트 시프트한 후, 하위 8비트만 남겨야 함.
        //8비트 만큼 우 시프트. => 000000FF 가 됨.
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getRed(int rgb) {
        //적색 채널 값은 16-23번째 비트에 위치하므로, 이를 추출하기 위해 전체 값을 오른쪽으로 16비트 시프트한 후, 하위 8비트만 남겨야 함.
        //16비트 만큼 우 시프트. => 000000FF 가 됨.
        return (rgb & 0x00FF0000) >> 16;
    }
}
