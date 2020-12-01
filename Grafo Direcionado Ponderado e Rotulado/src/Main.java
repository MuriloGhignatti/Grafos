import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>(String.class, true, true);

        Vertice<String> joao = new Vertice<>("Joao", "teste");
        Vertice<String> rafael = new Vertice<>("Rafael", "teste2");

        grafo.addVertice("Murilo");
        grafo.addVertice(rafael);
        grafo.addVertice(joao);

        grafo.criaAdjacencia(joao, rafael, 2);
        grafo.criaAdjacencia("Murilo", joao, 3);
        grafo.criaAdjacencia(rafael, "Murilo", 4);

        Dijkstra<String> busca = new Dijkstra<>(grafo);

//
//        grafo.imprimeAdjacencias("Murilo");
//
//        int[][] result = grafo.gerarMatrizAdjacencias();
//
//        grafo.gerarArquivoPajek("result.txt");

//        grafo.carregarArquivoPajek("grafo.net");
//        grafo.imprimeAdjacencias(5);

//        grafo.gerarGrafoAleatorio("result.txt.net", 3, false);

        System.out.println("Numero de Vertices: " + grafo.getQuantidadeDeVertices());
        System.out.println("É Conexo? " + grafo.VeriticaConexo());
//        for(int[] i: result){
//            for(int j: i){
//                System.out.print(j + ",\t\t");
//            }
//            System.out.println(" ");
//        }
    }

    public static void extrator(String arquivo1, String arquivo2){
        FileReader readerVertice = null;
        FileReader readerAresta = null;
        BufferedReader brVertice = null;
        BufferedReader brAresta = null;
        FileWriter fw = null;

        try{
            readerVertice = new FileReader(arquivo1);
            readerAresta = new FileReader(arquivo2);
            brVertice = new BufferedReader(readerVertice);
            brAresta = new BufferedReader(readerAresta);
            File grafo = new File("grafo.net");
            fw = new FileWriter(grafo);
            String linha;
            ArrayList<String> aeroportos = new ArrayList<String>();
            ArrayList<String> aeroportosID = new ArrayList<String>();
            int i = 0;
            while ((linha = brVertice.readLine()) != null) {
                String[] colunas = linha.split(",");
                aeroportos.add(colunas[1]);
                aeroportosID.add(colunas[0]);
            }
            fw.write("*Vertices " + (aeroportos.size() - 1));
            for (int g = 1; g < aeroportos.size(); g++) {
                fw.write("\n" + aeroportosID.get(g) + " \"" + aeroportos.get(g) + "\" ");
            }
            ArrayList<String> aeroportoOrigemID = new ArrayList<String>();
            ArrayList<String> aeroportoDestinoID = new ArrayList<String>();
            ArrayList<String> empresaAereaID = new ArrayList<String>();
            while ((linha = brAresta.readLine()) != null) {
                String[] colunas = linha.split(",");
                aeroportoOrigemID.add(colunas[3]);
                aeroportoDestinoID.add(colunas[5]);
                empresaAereaID.add(colunas[1]);
            }
            fw.write("\n" + "*Arcs");
            for(int j=1; j < aeroportoOrigemID.size(); j++) {
                if (!(aeroportoOrigemID.get(j).equals("null")) && !((aeroportoDestinoID.get(j)).equals("null")) && !((empresaAereaID.get(j)).equals("null"))) {
                    fw.write("\n" + aeroportoOrigemID.get(j) + " " + aeroportoDestinoID.get(j) + " " + empresaAereaID.get(j));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(brVertice != null)
                    brVertice.close();
                if(brAresta != null)
                    brVertice.close();
                if(readerVertice != null)
                    readerVertice.close();
                if(readerAresta != null)
                    readerAresta.close();
                if(fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
