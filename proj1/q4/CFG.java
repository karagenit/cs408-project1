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
	// ...
    }
	
	public void deleteNode(int p, MethodNode m, ClassNode c) {
		// ...
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
