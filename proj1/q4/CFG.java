import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

public class CFG {
    Set<Node> nodes = new HashSet<Node>();
    Map<Node, Set<Node>> edges = new HashMap<Node, Set<Node>>();

    static class Node {
        int position;
        MethodNode method;
        ClassNode clazz;

        Node(int p, MethodNode m, ClassNode c) {
            position = p; method = m; clazz = c;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Node)) return false;
            Node n = (Node)o;
            return (position == n.position) &&
            method.equals(n.method) && clazz.equals(n.clazz);
        }

        public int hashCode() {
            return position + method.hashCode() + clazz.hashCode();
        }

        public String toString() {
	        return clazz.name + "." +
	        method.name + method.signature + ": " + position;
        }
    }

    /*
     * Inserts a new node.
     * If the node doesn't already exist in the set of nodes,
     * insert the node into the set of nodes and associate
     * an empty set of nodes as the value of the new node
     * in the map of edges.
     */
    public void addNode(int p, MethodNode m, ClassNode c) {
	    Node n = new Node(p, m, c);
        Set<Node> connections = new HashSet<Node>();

        if (!nodes.contains(n)) {
            nodes.add(n);
            edges.put(n, connections);
        }
    }

    public void addEdge(int p1, MethodNode m1, ClassNode c1,
			int p2, MethodNode m2, ClassNode c2) {
	    Node n1 = new Node(p1, m1, c1);
        Node n2 = new Node(p2, m2, c2);

        // Ensure these nodes actually exist before we create the edges.
        addNode(p1, m1, c1);
        addNode(p2, m2, c2);

        // TODO: check if edge already exists?

        // Add an edge in the edge map for n1 -> n2
        // edges.get(n1) should definitely succeed.
        edges.get(n1).add(n2);

        // TODO: should we also add n2 -> n1? These are just unidirectional right?
    }
	
	public void deleteNode(int p, MethodNode m, ClassNode c) {
        Node n = new Node(p, m, c);
	    if (nodes.contains(n)) {
            nodes.remove(n);
            edges.remove(n);
            edges.forEach((k, v) -> {
                v.remove(n);
            });
        }
    }
	
    public void deleteEdge(int p1, MethodNode m1, ClassNode c1,
						int p2, MethodNode m2, ClassNode c2) {
		// ...
    }
	

    public boolean isReachable(int p1, MethodNode m1, ClassNode c1,
			       int p2, MethodNode m2, ClassNode c2) {
	// ...
    }
}
