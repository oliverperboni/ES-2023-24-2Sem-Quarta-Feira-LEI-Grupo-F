package core;

import javax.swing.*;

import structures.LineSchedule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GrafoGUI extends JFrame {
    private Grafo grafo;

    private List<LineSchedule> lineSchedules;
    private LineSchedule centerLineSchedule;

    public GrafoGUI(List<LineSchedule> lineSchedules, LineSchedule centerLineSchedule) {
        super("Grafo GUI");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.lineSchedules = lineSchedules;
        this.centerLineSchedule = centerLineSchedule;

        add(new GrafoPanel());
    }

    class GrafoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Define as coordenadas do centro para desenhar o LineSchedule dado
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Desenhar o LineSchedule dado no centro
            desenharLineSchedule(g, centerX, centerY, centerLineSchedule, Color.GREEN);

            // Iterar sobre a lista de LineSchedules
            for (LineSchedule lineSchedule : lineSchedules) {
                if(lineSchedule.getSala().equals(centerLineSchedule.getSala())){
                    if (!compararHoras(lineSchedule.getHoraInicio(), centerLineSchedule.getHoraFim()) || compararHoras(lineSchedule.getHoraInicio(), centerLineSchedule.getHoraInicio())) {
                        desenharLineSchedule(g, centerX, centerY, lineSchedule, Color.RED);
                    }
                }

                // Se houver conflito, pinte em vermelho
                // if (temConflito(lineSchedule, centerLineSchedule)) {
                //     desenharLineSchedule(g, centerX, centerY, lineSchedule, Color.RED);
                // }
            }
        }

        // Método para desenhar um LineSchedule em uma posição específica
        private void desenharLineSchedule(Graphics g, int x, int y, LineSchedule lineSchedule, Color color) {
            g.setColor(color);
            g.fillOval(x, y, 20, 20);
            g.drawString(lineSchedule.getCurso() + " - " + lineSchedule.getUnidadeCurricular(), x + 25, y - 10);
        }

        // Método para verificar se há conflito entre dois LineSchedules
        // private boolean temConflito(LineSchedule a, LineSchedule b) {
        //     // Verifica se as datas e horas dos LineSchedules são iguais
        //     return a.getDataHora().equals(b.getDataHora());
        // }

        public static boolean compararHoras(String horaA, String horaB) {
            // Extrai as horas e minutos de cada hora
            String[] partesHoraA = horaA.split(":");
            String[] partesHoraB = horaB.split(":");
    
            // Converte as partes para inteiros
            int horaAInt = Integer.parseInt(partesHoraA[0]);
            int minutoAInt = Integer.parseInt(partesHoraA[1]);
            int horaBInt = Integer.parseInt(partesHoraB[0]);
            int minutoBInt = Integer.parseInt(partesHoraB[1]);
    
            // Compara as horas
            if (horaAInt > horaBInt) {
                return true;
            } else if (horaAInt == horaBInt) {
                // Se as horas forem iguais, compara os minutos
                if (minutoAInt > minutoBInt) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // GrafoGUI gui = new GrafoGUI();
            // gui.setVisible(true);
        });
    }
}

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
