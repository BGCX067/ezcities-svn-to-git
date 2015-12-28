package simulation.app.celluar;

/**
 * Created by IntelliJ IDEA.
 * User: jetik
 * Date: 07-Mar-2011
 * Time: 12:17:10
 * To change this template use File | Settings | File Templates.
 */
public interface Visitor {
    /**
     * Visits one node
     * @param node
     */
    public void visit(QuadTreeNode node);
}
