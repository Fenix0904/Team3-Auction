package auction.base64;

import java.io.*;

public class Base64 {
    public static void main(String[] args) {
        String imagePath = "D:\\image.jpg";
        System.out.println("=================Encoder Image to Base 64!=================");
        String base64ImageString = encoder(imagePath);
        System.out.println("Base64ImageString = " + base64ImageString);

        System.out.println("=================Decoder Base64ImageString to Image!=================");
        decoder(base64ImageString, "D:\\decoded.jpg");

        System.out.println("DONE!");

    }

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = java.util.Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathOutFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathOutFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = java.util.Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }
}
