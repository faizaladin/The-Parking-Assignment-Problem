import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Point;

public class PointPlotter extends JPanel {

    private ArrayList<Spot> spots;

    public PointPlotter(ArrayList<Spot> spots) {
        this.spots = spots;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < spots.size(); i++){
            Spot spot = spots.get(i);
            int x = (int) spot.getLoc().getX();
            int y = (int) spot.getLoc().getY();
            g.setColor(Color.RED);
            g.fillOval(x, y, 5, 5); // Draw a small red circle for each point
        }
    }
}
