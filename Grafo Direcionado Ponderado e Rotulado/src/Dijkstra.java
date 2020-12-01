import java.util.*;

public class Dijkstra<T>{

    private List<Vertice<T>> vertices;
    private HashMap<Vertice<T>, List<Aresta<T>>> arestas;
    private Set<Vertice<T>> visitados;
    private Set<Vertice<T>> nVisitados;
    private HashMap<Vertice<T>, Vertice<T>> antecedentes;
    private HashMap<Vertice<T>, Double> distancia;
    private double pesoResultado;

    public Dijkstra(Grafo<T> grafo){
        this.vertices = grafo.getVertices();
        this.arestas = new HashMap<>();
        this.visitados = new HashSet<>();
        this.nVisitados = new HashSet<>();
        this.antecedentes = new HashMap<>();
        this.distancia = new HashMap<>();
        this.pesoResultado = -1;
    }

    public void resetVars(Grafo<T> grafo){
        this.vertices = grafo.getVertices();
        this.arestas = new HashMap<>();
        this.visitados = new HashSet<>();
        this.nVisitados = new HashSet<>();
        this.antecedentes = new HashMap<>();
        this.distancia = new HashMap<>();
        this.pesoResultado = -1;
        System.gc();
    }

    public void resetVars(){
        this.arestas = new HashMap<>();
        this.visitados = new HashSet<>();
        this.nVisitados = new HashSet<>();
        this.antecedentes = new HashMap<>();
        this.distancia = new HashMap<>();
        this.pesoResultado = -1;
        System.gc();
    }

    public boolean execute(Vertice<T> inicial, Vertice<T> destino, Vertice<T> homemDoMeio){
        boolean result = privateExecute(inicial, destino).contains(homemDoMeio);
        resetVars();
        return result;
    }

    public double execute(Vertice<T> inicial, Vertice<T> destino){
        privateExecute(inicial, destino);
        double result = pesoResultado;
        resetVars();
        return result;
    }

    private LinkedList<Vertice<T>> privateExecute(Vertice<T> inicial, Vertice<T> destino){
        for(Vertice<T> vertice: vertices){
            if(arestas.get(vertice) == null)
                arestas.put(vertice, new ArrayList<>(vertice.getArestas()));
        }
        distancia.put(inicial, 0.0);
        nVisitados.add(inicial);
        Vertice<T> verticeAtual;
        while(nVisitados.size() != 0){
            verticeAtual = getMenor(nVisitados);
            visitados.add(verticeAtual);
            nVisitados.remove(verticeAtual);
            getDistanciasMinimas(verticeAtual);
        }
        LinkedList<Vertice<T>> caminho = new LinkedList<>();
        if(antecedentes.get(destino) == null)
            return null;
        verticeAtual = destino;
        caminho.add(verticeAtual);
        while(antecedentes.get(verticeAtual) != null){
            verticeAtual = antecedentes.get(verticeAtual);
            caminho.add(verticeAtual);
        }
        Collections.reverse(caminho);
        return caminho;
    }

    private Vertice<T> getMenor(Set<Vertice<T>> nVisitados){
        Vertice<T> menor = null;
        for(Vertice<T> vertice: nVisitados){
            if(menor == null)
                menor = vertice;
            else
                if(getMenorDistancia(vertice) < getMenorDistancia(menor))
                    menor = vertice;
        }
        return menor;
    }

    private double getMenorDistancia(Vertice<T> destino){
        Double distanciaAtual = distancia.get(destino);
        return distanciaAtual == null ? Double.MAX_VALUE : distanciaAtual;
    }

    private void getDistanciasMinimas(Vertice<T> vertice){
        Vertice<T> verticeAtual;
        for(Aresta<T> aresta: arestas.get(vertice)){
            verticeAtual = aresta.getVertice();
            if(getMenorDistancia(verticeAtual) > getMenorDistancia(vertice) + getDistancia(vertice, verticeAtual)){
                distancia.put(verticeAtual, getMenorDistancia(vertice) + getDistancia(vertice, verticeAtual));
                antecedentes.put(verticeAtual, vertice);
                pesoResultado += aresta.getPeso();
                nVisitados.add(verticeAtual);
            }
        }
    }

    private double getDistancia(Vertice<T> inicial, Vertice<T> destino){
        for(Aresta<T> aresta: inicial.getArestas()){
            if(aresta.getVertice().equals(destino))
                return aresta.getPeso();
        }
        throw new RuntimeException("Vertice de destino não encontrado");
    }
}
