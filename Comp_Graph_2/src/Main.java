import java.awt.*;

public class Main {
    public static void main(String[] args) {
        String dataSetPath = "C:\\Users\\HP\\IdeaProjects\\Comp_Graph_2\\src\\DS6.txt";
        Render render = new Render(540,960, 1, "DS6result.png");
        render.setBackground(Color.WHITE.getRGB());
        render.paint(render.getCoords(dataSetPath), Color.BLACK.getRGB());
        render.saveImage();
    }
}