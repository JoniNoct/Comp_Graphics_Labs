import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class New_Render extends Component {
    BufferedImage img;
    int width;
    int height;
    String outPath;
    Vector<Point> heads;
    List<Point> coords;

    public New_Render(int tempWidth, int tempHeight, int mode, String path, String pathDS) {
        width = tempWidth;
        height = tempHeight;
        outPath = path;
        img = new BufferedImage(width, height, mode);
        coords = getCoords(pathDS);
        heads = convexHull(coords, coords.size());
    }

    public List<Point> getCoords(String path) {
        List<Point> result = new LinkedList<>();
        List<String> temp = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String tempStr;
            while((tempStr=br.readLine())!=null){
                temp = Arrays.asList(tempStr.split(" "));
                result.add(new Point(Integer.parseInt(temp.get(1)), Integer.parseInt(temp.get(0))));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0; // collinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // Prints convex hull of a set of n points.
    public Vector<Point> convexHull(List<Point> points, int n) {
        // Initialize Result
        Vector<Point> hull = new Vector<Point>();

        // Find the leftmost point
        int l = 0;
        for (int i = 1; i < n; i++)
            if (points.get(i).x < points.get(l).x)
                l = i;

        // Start from leftmost point, keep moving
        // counterclockwise until reach the start point
        // again. This loop runs O(h) times where h is
        // number of points in result or output.
        int p = l;
        int q = 0;
        do {
            // Add current point to result
            System.out.println(points.get(p).x);
            System.out.println(points.get(p).y);
            hull.add(points.get(p));

            // Search for a point 'q' such that
            // orientation(p, x, q) is counterclockwise
            // for all points 'x'. The idea is to keep
            // track of last visited most counterclock-
            // wise point in q. If any point 'i' is more
            // counterclock-wise than q, then update q.
            q = (p + 1) % n;

            for (int i = 1; i < n; i++) {
                // If i is more counterclockwise than
                // current q, then update q
                if (orientation(points.get(p), points.get(i), points.get(q)) == 2)
                    q = i;
            }

            // Now q is the most counterclockwise with
            // respect to p. Set p as q for next iteration,
            // so that q is added to result 'hull'
            p = q;

        } while (p != l); // While we don't come to first
        // point

        // Print Result
        return hull;
    }

    public void setBackground(int color) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                img.setRGB(j, i, color);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < heads.size(); i++) {
            if (i!=(heads.size()-1))
                g2d.drawLine(heads.get(i).x, heads.get(i).y, heads.get(i + 1).x, heads.get(i + 1).y);
            else
                g2d.drawLine(heads.get(i).x, heads.get(i).y, heads.get(0).x, heads.get(0).y);
        }
    }

    public void drawPixels(List<Point> coords, int color) {
        for (int i = 0; i < coords.size(); i++)
            img.setRGB(coords.get(i).x, coords.get(i).y, color);
    }

    private BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    private BufferedImage createTransformed(
            BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public void saveImage() {
        try {
            File outFile = new File(outPath);
            String ext = outPath.substring(outPath.length()-3);
            ImageIO.write(createFlipped(img), ext, outFile);
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
