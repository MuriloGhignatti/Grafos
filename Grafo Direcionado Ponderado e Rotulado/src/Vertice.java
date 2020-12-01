import java.util.Collection;
import java.util.HashMap;

public class Vertice<T> {

    private T info;
    private String rotulo;
    private Integer distanciaDaRaiz;
    private HashMap<T, Aresta<T>> arestas;

    public Vertice(T info, HashMap<T, Aresta<T>> arestas, String rotulo){
        this.info = info;
        this.arestas = arestas;
        this.rotulo = rotulo;
        this.distanciaDaRaiz = Integer.MAX_VALUE;
    }

    public Vertice(T info, HashMap<T, Aresta<T>> arestas){
        this(info, arestas, "");
    }

    public Vertice(T info){
        this(info, null, "");
    }

    public Vertice(T info, String rotulo){
        this(info, null, rotulo);
    }

    public Vertice(Vertice<T> toCopy){
        this(toCopy.info, toCopy.arestas, toCopy.rotulo);
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void addAresta(Aresta<T> aresta){
        if(arestas == null)
            arestas = new HashMap<>();
        arestas.put((T) aresta.getInfo(), aresta);
    }

    public void addAresta(T info, Aresta<T> aresta){
        if(arestas == null)
            arestas = new HashMap<>();
        arestas.put(info, aresta);
    }

    public void addAresta(Vertice<T> vertice){
        if(arestas == null)
            arestas = new HashMap<>();
        arestas.put(vertice.getInfo(), new Aresta<>(vertice));
    }

    public void removeAresta(Aresta<T> aresta){
        if(arestas == null)
            System.out.println("O vertice: " + this.info + " não possui adjacencias");
        else
            arestas.remove(aresta.getInfo());
    }

    public void removeAresta(Vertice<T> vertice){
        if(arestas == null)
            System.out.println("O vertice: " + this.info + " não possui adjacencias");
        else
            arestas.remove(vertice.getInfo());
    }

    public void removeAresta(T info){
        if(arestas == null)
            System.out.println("O vertice: " + this.info + " não possui adjacencias");
        else
            arestas.remove(info);
    }

    public double existeAdjacencia(T info){
        if(arestas != null) {
            Aresta<T> result = arestas.get(info);
            if(result != null)
                return result.getPeso();
        }
        return Integer.MIN_VALUE;
    }

    public double existeAdjacencia(Vertice<T> vertice){
        if(arestas != null) {
            Aresta<T> result = arestas.get(vertice.getInfo());
            if(result != null)
                return result.getPeso();
        }
        return Double.MIN_VALUE;
    }

    public Collection<Aresta<T>> getArestas(){
        return this.arestas.values();
    }

    public void imprimeAdjacencias(){
        if(arestas == null)
            System.out.println("O Vertice: " + this.info + " não possui adjacencias");
        else
            for(Aresta adjacencia: arestas.values()){
                System.out.println(this.info + "->" + adjacencia.getInfo());
            }
    }

    public boolean arestaIsEmpty(){
        return arestas == null || arestas.size() == 0;
    }
}
