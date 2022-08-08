package ascii_art.img_to_char;
import image.Image;
import java.awt.*;
import java.util.HashMap;

public class BrightnessImgCharMatcher {
    private int SIZE = 16;
    private final Image img;
    private final String font;
    private final HashMap<Image, Float> cache = new HashMap<>(); //cache

    /**
     * constructer for the BrightnessImgCharMatcher class
     * @param img image object rpresenting the image wished to be converted to ascii art
     * @param font font of ascii chars
     */
    public BrightnessImgCharMatcher(Image img , String font) {
        this.img = img;
        this.font = font;}

    /**
     * calculates brightness of single char
     *
     * @param Char of which brightness will be calculated
     * @return char brightness
     */
    private float calculateCharBrightness(char Char) {
        boolean[][] charBooleanRepresentation = CharRenderer.getImg(Char, SIZE,  font);
        int whitePixels = 0;
        for (boolean[] pixelRow : charBooleanRepresentation)
        {
            for (boolean isWhite : pixelRow)
            {
                if (isWhite)
                {
                    whitePixels++;
                }
            }
        }
        return (float) whitePixels / (SIZE * SIZE);
    }

    /**
     * calaculates brightness of all chars in a given array
     *
     * @param charArray array of chars whose brightness should be calculated
     * @return array of respecting brightness
     */
    private float[] calculateCharArrayBrightness(Character[] charArray)
    {
        float[] CharArrayBrightness = new float[charArray.length];
        for (int i = 0; i < charArray.length; i++)
        {
            CharArrayBrightness[i] = calculateCharBrightness(charArray[i]);
        }
        return CharArrayBrightness;
    }

    
    /**
     * normalize single brightness
     *
     * @param brightness brightness to be normailzed
     * @param minB min brightness of source array
     * @param maxB max brightness of source array
     * @return normalized brightness
     */
    private float calculatedStretch(float brightness, float minB, float maxB)
    {
        return (brightness - minB) / (maxB - minB);
    }

    /**
     * normalize brightness of array
     * @param brightnesses source array of brightnesses
     * @return respecting array of normalized brightnesses
     */
    private float[] calculateArrayLinearStretch(float[] brightnesses) {
        float[] normalizedBrightnesses = new float[brightnesses.length];
        float maxBrightness = 0;
        float minBrightness = 1;
        for (float brightness : brightnesses) {
            if (brightness > maxBrightness)
            {
                maxBrightness = brightness;
            }
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
        }

        for (int i = 0; i < brightnesses.length; i++)
        {
            normalizedBrightnesses[i] = calculatedStretch(brightnesses[i], minBrightness, maxBrightness);
        }

        return normalizedBrightnesses;
    }

    /**
     * this method converts a single 3D colored pixel to 1D grey scale pixel
     * @param coloredPixel Color consisting of three parameteres representing the RGB scale
     * @return float value of pixel in GreyScale
     */
    private float convert2GreyPixel(Color coloredPixel)
    {
        return (float) (coloredPixel.getRed() * 0.2126 + coloredPixel.getGreen() * 0.7152 + coloredPixel.getBlue() *
                0.0722) / 255F;
    }


    /**
     * Relies on Image API, calculates average brightness of a given image
     * @param img image whos avergae brightness we wish to calculate
     * @return image brightness
     */
    private float calculateImageAverageBrightness(Image img)
    {
        if (cache.containsKey(img)){System.out.println("cache");return cache.get(img);}
        float brightnessSum = 0 ;
        int numPixels = 0;
        assert img != null;
        for (Color pixel : img.pixels())
            {
                float greyPixel = convert2GreyPixel(pixel);
                brightnessSum += greyPixel;
                numPixels ++;
            }
        float avgBrightness = brightnessSum / numPixels;
        cache.put(img,avgBrightness);
        return avgBrightness;
    }


    /**
     * Converts an image to ascii art
     * @param numCharsInRow size of char row that will represent the original image at the ascii art 
     * @param availableChars pool of chars that will generate the ascii art version of the image
     * @param charBrightness array indicating the brightnesses of the latter chars
     * @return the original image formatted to ascii art represented by 2D array of chars 
     */
    private char[][] convertImageToAscii (int numCharsInRow , Character [] availableChars , float [] charBrightness)
    {
        int pixels = img.getWidth() / numCharsInRow;
        int imgIdx = 0;
        int rows = img.getHeight()/pixels;
        int cols = img.getWidth()/pixels;
        char[][] asciiArt = new char[rows][cols];
        for(Image subImage : img.squareSubImagesOfSize(pixels))
        {
            float average = calculateImageAverageBrightness(subImage);
            float minDist = Math.abs(average- charBrightness[0]);
            int idx = 0;
            for (int i = 0 ; i < charBrightness.length ; i++)
            {
                if (Math.abs(average - charBrightness[i]) < minDist)
                {
                    minDist = Math.abs(average - charBrightness[i]);
                    idx = i ;
                }
            }

            asciiArt[imgIdx / cols][imgIdx % cols] = availableChars[idx];
            imgIdx++;
        }
        return asciiArt;

    }

    /**
     * process given chars and creates from them ascii art
     * @param numCharsInRow size of char row that will represent the original image at the ascii art
     * @param charSet pool of chars that will generate the ascii art version of the image
     * @return the original image formatted to ascii art represented by 2D array of chars
     */
        public char[][] chooseChars ( int numCharsInRow, Character[] charSet)
        {
            if (charSet != null){
            float [] charBrightness = calculateCharArrayBrightness(charSet);
            float [] normalizedCharBrightness = calculateArrayLinearStretch(charBrightness);
            return convertImageToAscii(numCharsInRow , charSet , normalizedCharBrightness);}
            else{return new char[][] {{}};}
        }

}
