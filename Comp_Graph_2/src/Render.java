import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Render {
    BufferedImage img;
    int width;
    int height;
    String outPath;

    public Render(int tempWidth, int tempHeight, int mode, String path ) {
        width = tempWidth;
        height = tempHeight;
        outPath = path;
        img = new BufferedImage(width, height, mode);
    }

    public List<Integer> getCoords(String path) {
        List<Integer> result = new LinkedList<>();
        List<String> temp = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String tempStr;
            while((tempStr=br.readLine())!=null){
                temp = Arrays.asList(tempStr.split(" "));
                result.add(Integer.parseInt(temp.get(0)));
                result.add(Integer.parseInt(temp.get(1)));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public void setBackground(int color) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                img.setRGB(j, i, color);
            }
        }
    }

    public void paint(List<Integer> coords, int color) {
        for (int i = 0; i < coords.size(); i+=2)
            img.setRGB(coords.get(i), coords.get(i+1), color);
    }

    public void saveImage() {
        try {
            File outFile = new File(outPath);
            String ext = outPath.substring(outPath.length()-3);
            ImageIO.write(img, ext, outFile);
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}