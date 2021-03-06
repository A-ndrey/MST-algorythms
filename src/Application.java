import algorithms.*;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import graph_elements.Edge;
import graph_elements.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Application extends JFrame {

    private Algorithm algorithm;

    Application(Algorithm algorithm){
        super(algorithm.getClass().getSimpleName());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.algorithm = algorithm;
    }

    public void start(Graph<Vertex, Edge> graph){
        algorithm.start(graph);
        Layout<Vertex, Edge> layout = new ISOMLayout<>(graph);
        layout.setSize(new Dimension(600,600));
        BasicVisualizationServer<Vertex,Edge> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(650,650));
        setStyle(vv);

        add(vv);
        pack();
        setVisible(true);
    }

    private void setStyle(BasicVisualizationServer<Vertex, Edge> vv){
        RenderContext<Vertex, Edge> context = vv.getRenderContext();

        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.setBackground(Color.WHITE);
        context.setEdgeLabelTransformer(edge -> Integer.toString(edge.getWeight()));
        context.setEdgeDrawPaintTransformer(edge -> edge.isVisible() ? Color.BLACK : Color.LIGHT_GRAY);
        context.setEdgeShapeTransformer(new EdgeShape.Line<>());
        context.setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.LIGHT_GRAY){
            @Override
            public <E> Component getEdgeLabelRendererComponent(JComponent vv, Object value, Font font, boolean isSelected, E edge) {
                super.getEdgeLabelRendererComponent(vv, value, font, isSelected, edge);
                setForeground(((Edge)edge).isVisible() ? Color.BLACK : Color.LIGHT_GRAY);
                return this;
            }
        });
//        context.setVertexShapeTransformer(node -> new Ellipse2D.Double(-12, -12, 24, 24));
        context.setVertexShapeTransformer(node -> new Rectangle2D.Double(-30, -12, 60, 24));
        context.setVertexLabelTransformer(node -> node.getName());
        context.setVertexFillPaintTransformer(node -> Color.WHITE);
    }


}
