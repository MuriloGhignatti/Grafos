public class Main {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        Vertice<String> joao = new Vertice<>("Joao");
        Vertice<String> rafael = new Vertice<>("Rafael");

        grafo.addVertice("Murilo");
        grafo.addVertice("Arthur");
        grafo.addVertice(rafael);
        grafo.addVertice(joao);

        grafo.criaAdjacencia("Murilo", "Arthur", 1);
        grafo.criaAdjacencia(joao, rafael, 2);
        grafo.criaAdjacencia("Murilo", joao, 3);
        grafo.criaAdjacencia("Murilo", rafael, 4);
        grafo.criaAdjacencia(rafael, "Murilo", 5);

        grafo.imprimeAdjacencias("Murilo");

        int[][] result = grafo.gerarMatrizAdjacencias();

        for(int[] i: result){
            for(int j: i){
                System.out.print(j + ",\t");
            }
            System.out.println(" ");
        }
    }
}
