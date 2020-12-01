import java.awt.*;

class Point {
    int x, y;
    Point(int x, int y){
        this.x=x;
        this.y=y;
    }
}

public class Main {
    public static void main(String[] args) {
        String dataSetPath = "C:\\Users\\illiu\\Comp_Graph_3\\src\\DS6.txt";
        New_Render render = new New_Render(960,540, 1, "DS6result.png", dataSetPath);
        render.setBackground(Color.WHITE.getRGB());
        render.drawPixels(render.coords, Color.BLACK.getRGB());
        render.paint(render.img.getGraphics());
        render.saveImage();
    }
}