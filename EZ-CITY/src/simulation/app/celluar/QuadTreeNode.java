package simulation.app.celluar;

import java.util.Arrays;
import java.util.List;

import draw.Geometry.Pnt3D;
import draw.Geometry.Rec3D;


public class QuadTreeNode {
    public QuadTreeNode[] children = new QuadTreeNode[8];

    // Center of the quadrant associated with this searchSpace
    private Pnt3D center = new Pnt3D(0, 0, 0);
    private double[] color = {0.0f, 1.0f, 1.f, 0.7f};
    
    private float quadrantWidth;
    // Precalculated halfWidth
    private float halfWidth;

    public QuadTreeNode(double centerX, double centerY, double centerZ, float quadrantWidth) {
        this(new Pnt3D(centerX, centerY, centerZ), quadrantWidth);
    }

    public QuadTreeNode(Pnt3D center, float quadrantWidth) {
        this.center = center;
        setQuadrantWidth(quadrantWidth);
    }

    public float getQuadrantWidth() {
        return quadrantWidth;
    }

    public void setQuadrantWidth(float quadrantWidth) {
        this.quadrantWidth = quadrantWidth;
        this.halfWidth = quadrantWidth / 2;
    }

    public QuadTreeNode[] getChildren() {

        return children;
    }

    public void setChildren(QuadTreeNode[] children) {
        this.children = children;
    }

    private Pnt3D point;

    public boolean isLeaf() {
        return leaf;
    }

    private boolean leaf = true;

    @Override
    public String toString() {
        return "Node{" +
                "\n center=" + center +
                "\n children=" + (children == null ? null : Arrays.asList(children)) +
                "\n point=" + point +
                "\n leaf=" + leaf +
                "\n}";
    }

    protected int calculateQuadrantIndex(Pnt3D point, Pnt3D compareWith) {
        int result = 0;
        if (point == null) {
            throw new IllegalStateException("Point cannot be null");
        }
        if (compareWith == null) {
            throw new IllegalStateException("Point to be compared cannot be null");
        }
        if (point.getX() > compareWith.getX()) result += 1;
        if (point.getY() > compareWith.getY()) result += 2;
        if (point.getZ() > compareWith.getZ()) result += 4;
        return result;
    }

    public void associateObject(Pnt3D cube) {
        this.point = cube;
        if(cube != null){
        	   this.color = cube.getColor();
        }
     
    }

    public Pnt3D getCenter() {
        return center;
    }

    public Pnt3D getPoint() {
        return point;
    }

    /**
     * @param node
     * @param leftBottomBack - Coord. f.e. 0,0,0
     * @param rightTopFront  - Coord. f.e. 10,10,10
     * @return
     */
    public void search(List<Pnt3D> result, QuadTreeNode node, Pnt3D leftBottomBack, Pnt3D rightTopFront) {
        if (node.isLeaf()) {
            Pnt3D quadPoint = node.getPoint();
            // Search region intersects with searchSpace, but point may not be in it
            // We will add the point only if it is within search region
            if (
                    leftBottomBack.getX() <  quadPoint.getX() && quadPoint.getX() < rightTopFront.getX() &&
                    leftBottomBack.getY() <  quadPoint.getY() && quadPoint.getY() < rightTopFront.getY() &&
                    leftBottomBack.getZ() <  quadPoint.getZ() && quadPoint.getZ() < rightTopFront.getZ()
                    ) {
                result.add(quadPoint);
            }
            // It is necessary to traverse deeper into the octal tree
        } else {
            for (int quadIndex = 0; quadIndex < 8; quadIndex++) {
                QuadTreeNode quadrant = node.getChild(quadIndex);
                if ((quadrant!= null) && quadrant.intersects(leftBottomBack, rightTopFront)) {
                    search(result, quadrant, leftBottomBack, rightTopFront);
                }
            }
        }

    }

    public boolean intersects(Pnt3D leftBottomBack, Pnt3D rightTopFront) {
        return !(
                center.getX() - halfWidth > rightTopFront.getX() ||
                center.getX() + halfWidth < leftBottomBack.getX() ||
                center.getY() - halfWidth > rightTopFront.getY() ||
                center.getY() + halfWidth < leftBottomBack.getY() ||
                center.getZ() - halfWidth > rightTopFront.getZ() ||
                center.getZ() + halfWidth < leftBottomBack.getZ()
        );
    }

    private float halfWidth() {
        return quadrantWidth / 2;
    }

    QuadTreeNode getChild(int quadrantIndex) {
        return children[quadrantIndex];
    }

    public void setChild(int quadIndex, QuadTreeNode node) {
        this.leaf = false;
        this.children[quadIndex] = node;
    }

    /**
     * Returns a point representing the 3D region in space, related to this searchSpace
     *
     * @param quadIndex
     * @return
     */
    public Rec3D getQuadrantCube(int quadIndex) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public Pnt3D calculateNewCenter(int quadIndex) {
    	
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();
        float quaterWidth = halfWidth / 2;
        if (quadIndex % 2 >= 1) {
            x += quaterWidth;
        } else {
            x -= quaterWidth;
        }

        if (quadIndex % 4 >= 2) {
            y += quaterWidth;
        } else {
            y -= quaterWidth;
        }


        if (quadIndex >= 4) {
            z += quaterWidth;
        } else {
            z -= quaterWidth;
        }
        return new Pnt3D(x, y, z);
    }

	public double[] getColor() {
		// TODO Auto-generated method stub
		
		return this.color;
	}
}