package wbif.sjx.common.Process.ActiveContour.PhysicalModel;

import ij.gui.PolygonRoi;
import ij.gui.Roi;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Stephen on 07/09/2016.
 */
public class NodeCollection extends HashSet<Node> {

    /**
     * Sets all nodes in NodeCollection to the same fixed_x value
     * @param fixed_x
     */
    public void setFixedX(boolean fixed_x) {
        Iterator<Node> itor = iterator();
        while (itor.hasNext()) {
            itor.next().setFixedX(fixed_x);

        }
    }

    /**
     * Sets all nodes in NodeCollection to the same fixed_y value
     * @param fixed_y
     */
    public void setFixedY(boolean fixed_y) {
        Iterator<Node> itor = iterator();
        while (itor.hasNext()) {
            itor.next().setFixedY(fixed_y);

        }
    }

    public Node getSpecialNode(int special) {
        Node node = null;

        Iterator<Node> itor = iterator();
        while (itor.hasNext()) {
            node = itor.next();
            if (node.getSpecial() == special) {
                return node;
            }
        }

        return node;

    }

    public Node getRandomNode() {
        Iterator<Node> iterator = iterator();

        return iterator.next();

    }

    /**
     * Returns the coordinates of all nodes in the NodeCollection.  Works in a clockwise manner from a randomly selected
     * node (only works for contours, not grids).
     * @return 2D array of coordinates.  First column are X-values, second column are Y-values.
     */
    public double[][] getNodeCoordinates() {
        double[][] coords = new double[size()][2];

        Node node = getRandomNode();
        int ID = node.getID();
        coords[0][0] = node.getX();
        coords[0][1] = node.getY();
        node = node.getRightNeighbour();

        int i=1;
        while (node.getID() != ID) {
            coords[i][0] = node.getX();
            coords[i][1] = node.getY();
            node = node.getRightNeighbour();
            i++;

        }

        return coords;

    }

    public Roi getROI() {
        double[][] coords = getNodeCoordinates();
        int[] newXCoords = new int[coords.length];
        int[] newYCoords = new int[coords.length];

        for (int i=0;i<coords.length;i++) {
            newXCoords[i] = (int) coords[i][0];
            newYCoords[i] = (int) coords[i][1];
        }

        return new PolygonRoi(newXCoords,newYCoords,newXCoords.length,Roi.POLYGON);

    }

    /**
     * Checks if any Nodes are listed as having moved at the last step.  If they haven't it returns false.
     * @return
     */
    public boolean anyNodesMoved() {
        for (Node node:this) {
            if (node.hasMoved()) return true;
        }

        return false;

    }
}