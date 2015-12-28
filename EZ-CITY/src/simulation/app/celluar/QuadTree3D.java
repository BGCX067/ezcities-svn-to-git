package simulation.app.celluar;

import java.util.ArrayList;
import java.util.List;

import draw.Geometry.Pnt3D;

/**
 * Created by IntelliJ IDEA.
 * User: jetik
 * Date: 24-Feb-2011
 * Time: 13:57:23
 * To change this template use File | Settings | File Templates.
 */
public class QuadTree3D {
    QuadTreeNode searchSpace;

    public QuadTree3D(QuadTreeNode searchSpace) {
        this.searchSpace = searchSpace;
    }

    public void addPoint(Pnt3D point) {
        addPoint(point, searchSpace);
    }

    @Override
    public String toString() {
        return "QuadTree3D{" +
                "searchSpace=" + searchSpace +
                '}';
    }


    public void addPoint(Pnt3D point, QuadTreeNode actualNode) {
        int quadIndex = actualNode.calculateQuadrantIndex(point, actualNode.getCenter());
        QuadTreeNode nodeForPoint = actualNode.children[quadIndex];
        if (nodeForPoint == null) {
            QuadTreeNode newNode = new QuadTreeNode(actualNode.calculateNewCenter(quadIndex), actualNode.getQuadrantWidth() / 2);
            newNode.associateObject(point);
            // sets appropriate child to new node and changes actualNode.leaf = false
            actualNode.setChild(quadIndex, newNode);
        } else {
            // We need to add existing point to deeper level
            Pnt3D toSplit = nodeForPoint.getPoint();
            if (toSplit!=null) {
                nodeForPoint.associateObject(null);
                addPoint(toSplit, nodeForPoint);
            }
            // We will also add a new point
            addPoint(point, nodeForPoint);

        }
    }

    public ArrayList<Pnt3D> search(Pnt3D leftBottomBack, Pnt3D rightTopFront) {
        if (
                leftBottomBack.getX() > rightTopFront.getX() ||
                leftBottomBack.getY() > rightTopFront.getY() ||
                leftBottomBack.getZ() > rightTopFront.getZ()
                ) {
            throw new IllegalArgumentException("Invalid search region, first search argument must be point at left bottom back corner of 3D space, second point at " +
                    "right top front.\n Points are: \n" + leftBottomBack + "\n" + rightTopFront);
        }
        ArrayList result = new ArrayList<Pnt3D>();
        searchSpace.search(result, searchSpace, leftBottomBack, rightTopFront);
        return result;
    }


    /**
     * Traverses whole quad tree, allowing visitor to perform some operations on the node
     * @param visitor
     */
    public void visit(Visitor visitor) {
        visit(visitor, searchSpace);
    }

    private void visit(Visitor visitor, QuadTreeNode node) {
        visitor.visit(node);
        if (!node.isLeaf()) {
            for (int quadIndex = 0; quadIndex < 8; quadIndex++) {
                QuadTreeNode quadrant = node.getChild(quadIndex);
                if (quadrant!=null) {
                    visit(visitor, quadrant);
                }
            }
        }
    }
}
