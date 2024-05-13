package core;

import structures.LineSchedule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A graphical user interface for displaying LineSchedules on a graph.
 */
public class GrafoGUI extends JFrame {

    public Grafo grafo;
    public List<LineSchedule> lineSchedules;
    public String sala;
    public String HoraFim;
    public String HoraInicio;
    public String Data;

    /**
     * Constructs a GrafoGUI with the given LineSchedules and room information.
     *
     * @param lineSchedules the list of LineSchedules to be displayed
     * @param sala          the room name
     * @param HoraInicio    the start time
     * @param HoraFim       the end time
     * @param Data          the date of the class
     */
    public GrafoGUI(List<LineSchedule> lineSchedules, String sala, String HoraInicio, String HoraFim, String Data) {
        super("Grafo GUI");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.lineSchedules = lineSchedules;
        this.HoraFim = HoraFim;
        this.sala = sala;
        this.Data = Data;
        this.HoraInicio = HoraInicio;

        add(new GrafoPanel());
    }

    /**
     * Inner class representing the panel for drawing LineSchedules.
     */
    public class GrafoPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Define the coordinates of the center for drawing the given LineSchedule
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int incrment = 1;
            // Draw the given LineSchedule at the center
            g.setColor(Color.GREEN);
            g.fillOval(centerX, centerY, 20, 20);
            g.drawString("Sua Sala", centerX + 25, centerY - 10);

            // Iterate over the list of LineSchedules
            for (LineSchedule lineSchedule : lineSchedules) {
                if (lineSchedule.getSala().equals(sala) && lineSchedule.getDataAula().equals(Data)) {
                    if (compararHoras(lineSchedule.getHoraInicio(), HoraFim)
                            || !compararHoras(lineSchedule.getHoraInicio(), HoraInicio)) {
                        drawLineSchedule(g, centerX, centerY, lineSchedule, Color.RED, incrment);
//                        System.out.println(lineSchedule);
                        incrment++;
                    }
                }
            }
        }

        /**
         * This method draws a LineSchedule on the specified Graphics object.
         *
         * @param g            The Graphics object to draw on.
         * @param x            The x-coordinate of the position to draw.
         * @param y            The y-coordinate of the position to draw.
         * @param lineSchedule The LineSchedule object to draw.
         * @param color        The Color to use for drawing.
         */
        private void drawLineSchedule(Graphics g, int x, int y, LineSchedule lineSchedule, Color color, int i) {
            g.setColor(color);

            // Calculate the radius of the circle based on the index 'i'
            int radius = 40 + i * 30; // Adjust this value as needed

            // Calculate the angle increment based on the number of circles 'i'
            double angleIncrement = 2 * Math.PI / i;

            // Draw each circle along the circumference
            for (int j = 0; j < i; j++) {
                // Calculate the angle for this circle
                double angle = j * angleIncrement;

                // Calculate the position of the circle on the circumference
                int circleX = x + (int) (radius * Math.cos(angle));
                int circleY = y + (int) (radius * Math.sin(angle));

                // Draw the circle
                g.fillOval(circleX - 10, circleY - 10, 20, 20); // Adjust the size as needed

                // Draw text
                g.setColor(Color.BLACK);
                g.drawString(lineSchedule.getSala() + " - " + lineSchedule.getUnidadeCurricular(), circleX - 10,
                        circleY - 10);

                // Draw line from center to circle
                g.drawLine(x, y, circleX, circleY);
                g.setColor(color);

            }
        }

        /**
         * Compares two time strings HH:mm.
         *
         * @param horaA the first time string
         * @param horaB the second time string
         * @return true if horaA is later than horaB, false otherwise
         */
        public static boolean compararHoras(String horaA, String horaB) {
            // Extract hours and minutes from each time
            String[] partesHoraA = horaA.split(":");
            String[] partesHoraB = horaB.split(":");

            // Convert parts to integers
            int horaAInt = Integer.parseInt(partesHoraA[0]);
            int minutoAInt = Integer.parseInt(partesHoraA[1]);
            int horaBInt = Integer.parseInt(partesHoraB[0]);
            int minutoBInt = Integer.parseInt(partesHoraB[1]);

            // Compare the hours
            if (horaAInt > horaBInt) {
                return true;
            } else if (horaAInt == horaBInt) {
                // If hours are equal, compare the minutes
                return minutoAInt > minutoBInt;
            } else {
                return false;
            }
        }
    }
}


/**
 * Represents a graph composed of vertices and edges.
 */
class Grafo {
    private List<Vertice> vertices;
    private List<Aresta> arestas;

    public Grafo() {
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    }

    public void adicionarVertice(Vertice v) {
        vertices.add(v);
    }

    public void adicionarAresta(Aresta a) {
        arestas.add(a);
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }
}

/**
 * Represents a vertex in a graph.
 */
class Vertice {

    private int x;
    private int y;
    private String nome;

    public Vertice(int x, int y, String nome) {
        this.x = x;
        this.y = y;
        this.nome = nome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNome() {
        return nome;
    }
}

/**
 * Represents an edge in a graph.
 */
class Aresta {
    private Vertice origem;
    private Vertice destino;

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }
}
