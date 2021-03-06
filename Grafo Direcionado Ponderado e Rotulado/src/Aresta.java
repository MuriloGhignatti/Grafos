public class Aresta<T> {

    private Vertice<T> vertice;
    private double peso;


    public Aresta(Vertice<T> vertice, double peso, String rotulo){
        this.vertice = vertice;
        this.peso = peso;
    }

    public Aresta(Vertice<T> vertice, double peso){
        this(vertice, peso, null);
    }

    public Aresta(Vertice<T> vertice){
        this(vertice, Integer.MAX_VALUE, null);
    }

    public Aresta(Aresta<T> toCopy){
        this(toCopy.vertice, toCopy.peso);
    }

    public Vertice<T> getVertice() {
        return vertice;
    }

    public double getPeso() {
        return peso;
    }

    public T getInfo() {
        return this.vertice.getInfo();
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setVertice(Vertice<T> vertice) {
        this.vertice = vertice;
    }
}
