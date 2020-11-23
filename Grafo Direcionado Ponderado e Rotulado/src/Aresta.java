public class Aresta<T> {

    private Vertice<T> vertice;
    private int peso;
    private String rotulo;


    public Aresta(Vertice<T> vertice, int peso, String rotulo){
        this.vertice = vertice;
        this.peso = peso;
        this.rotulo = rotulo;
    }

    public Aresta(Vertice<T> vertice, int peso){
        this(vertice, peso, null);
    }

    public Aresta(Vertice<T> vertice){
        this(vertice, Integer.MAX_VALUE, null);
    }

    public Aresta(Aresta<T> toCopy){
        this(toCopy.vertice, toCopy.peso, toCopy.rotulo);
    }

    public Vertice<T> getVertice() {
        return vertice;
    }

    public int getPeso() {
        return peso;
    }

    public String getRotulo() {
        return rotulo;
    }

    public T getInfo() {
        return this.vertice.getInfo();
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void setVertice(Vertice<T> vertice) {
        this.vertice = vertice;
    }
}
