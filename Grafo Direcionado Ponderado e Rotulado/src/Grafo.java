import java.io.*;
import java.util.*;

public class Grafo<T> {

    private HashMap<T, Vertice<T>> vertices;
    private boolean direcionado;
    private boolean ponderado;
    private Class<T> classType;

    private enum Resultado {
        Vertice1NaoPresente,
        Vertice2NaoPresente,
        VerticesPresentes,
    }

    public Grafo(Class<T> classType, boolean direcionado, boolean ponderado, HashMap<T, Vertice<T>> vertices) {
        this.vertices = vertices;
        this.direcionado = direcionado;
        this.classType = classType;
        this.ponderado = ponderado;
    }

    public Grafo(HashMap<T, Vertice<T>> toCopy, Class<T> classType) {
        this(classType, false, false, toCopy);
    }

    public Grafo(Grafo<T> toCopy) {
        this(toCopy.classType, toCopy.direcionado, toCopy.ponderado, toCopy.vertices);
    }

    public Grafo(Class<T> classType, boolean direcionado, boolean ponderado) {
        this(classType, direcionado, ponderado, new HashMap<>());
    }

    public Grafo(Class<T> classType) {
        this(classType, false, false, new HashMap<>());
    }

    private Resultado verticesExistem(T vertice1, T vertice2) {
        if (!existeVertice(vertice1)) {
            return Resultado.Vertice1NaoPresente;
        } else if (!existeVertice(vertice2)) {
            return Resultado.Vertice2NaoPresente;
        }
        return Resultado.VerticesPresentes;
    }

    public void addVertice(T info) {
        vertices.put(info, new Vertice<T>(info));
    }

    public void addVertice(Vertice<T> vertice) {
        vertices.put(vertice.getInfo(), vertice);
    }

    public void addVertice(T info, Vertice<T> vertice) {
        vertices.put(info, vertice);
    }

    public void removeVertice(T info) {
        vertices.remove(info);
    }

    public void removeVertice(Vertice<T> vertice) {
        vertices.remove(vertice.getInfo());
    }

    public void removeVertice(T info, Vertice<T> vertice) {
        vertices.remove(info, vertice);
    }

    public boolean existeVertice(T info) {
        return vertices.containsKey(info);
    }

    public boolean existeVertice(Vertice<T> vertice) {
        return vertices.containsKey(vertice.getInfo());
    }

    public boolean existeVertice(T info, Vertice<T> vertice) {
        return vertices.containsKey(info) && vertices.containsValue(vertice);
    }

    public void imprimeAdjacencias(T info) {
        vertices.get(info).imprimeAdjacencias();
    }

    public void imprimeAdjacencias(Vertice<T> vertice) {
        vertices.get(vertice.getInfo()).imprimeAdjacencias();
    }

    public int adjacentes(T info, Collection<Aresta<T>> listaAdjacentes) {
        listaAdjacentes = vertices.get(info).getArestas();
        return listaAdjacentes.size();
    }

    public int adjacentes(Vertice<T> vertice, Collection<Aresta<T>> listaAdjacentes) {
        listaAdjacentes = vertices.get(vertice.getInfo()).getArestas();
        return listaAdjacentes.size();
    }

    public void setInfo(T verticeInfo, T newInfo) {
        Vertice<T> vertice = vertices.remove(verticeInfo);
        vertice.setInfo(newInfo);
        vertices.put(newInfo, vertice);
    }

    public void setInfo(Vertice<T> vertice, T newInfo) {
        Vertice<T> holder = vertices.remove(vertice.getInfo());
        holder.setInfo(newInfo);
        vertices.put(newInfo, holder);
    }

    public double[][] gerarMatrizAdjacencias() {
        ArrayList<Vertice<T>> values = new ArrayList<>(vertices.values());
        double[][] matriz = new double[vertices.size()][vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                matriz[i][j] = values.get(i).existeAdjacencia(values.get(j));
            }
        }
        return matriz;
    }

    public void criaAdjacencia(T vertice1, T vertice2, int p, String rotulo) {
        switch (verticesExistem(vertice1, vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
                break;
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
                break;
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<T>(vertices.get(vertice2), p, rotulo));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<T>(vertices.get(vertice2), p, rotulo));
                    vertices.get(vertice2).addAresta(new Aresta<T>(vertices.get(vertice1), p, rotulo));
                }
                break;
        }
    }

    public void criaAdjacencia(T vertice1, T vertice2, int p) {
        switch (verticesExistem(vertice1, vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2), p));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2), p));
                    vertices.get(vertice2).addAresta(new Aresta<>(vertices.get(vertice1), p));
                }
        }
    }

    public void criaAdjacencia(T vertice1, T vertice2) {
        switch (verticesExistem(vertice1, vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2)));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2)));
                    vertices.get(vertice2).addAresta(new Aresta<>(vertices.get(vertice1)));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, Vertice<T> vertice2, int p, String rotulo) {
        switch (verticesExistem(vertice1.getInfo(), vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p, rotulo));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p, rotulo));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1.getInfo()), p, rotulo));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, Vertice<T> vertice2, int p) {
        switch (verticesExistem(vertice1.getInfo(), vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1.getInfo()), p));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, Vertice<T> vertice2) {
        switch (verticesExistem(vertice1.getInfo(), vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo())));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2.getInfo())));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1.getInfo())));
                }
        }
    }

    public void criaAdjacencia(T vertice1, Vertice<T> vertice2, int p, String rotulo) {
        switch (verticesExistem(vertice1, vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p, rotulo));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p, rotulo));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1), p, rotulo));
                }
        }
    }

    public void criaAdjacencia(T vertice1, Vertice<T> vertice2, int p) {
        switch (verticesExistem(vertice1, vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo()), p));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1), p));
                }
        }
    }

    public void criaAdjacencia(T vertice1, Vertice<T> vertice2) {
        switch (verticesExistem(vertice1, vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo())));
                else {
                    vertices.get(vertice1).addAresta(new Aresta<>(vertices.get(vertice2.getInfo())));
                    vertices.get(vertice2.getInfo()).addAresta(new Aresta<>(vertices.get(vertice1)));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, T vertice2, int p, String rotulo) {
        switch (verticesExistem(vertice1.getInfo(), vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2), p, rotulo));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2), p, rotulo));
                    vertices.get(vertice2).addAresta(new Aresta<>(vertices.get(vertice1.getInfo()), p, rotulo));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, T vertice2, int p) {
        switch (verticesExistem(vertice1.getInfo(), vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2), p));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2), p));
                    vertices.get(vertice2).addAresta(new Aresta<>(vertices.get(vertice1.getInfo()), p));
                }
        }
    }

    public void criaAdjacencia(Vertice<T> vertice1, T vertice2) {
        switch (verticesExistem(vertice1.getInfo(), vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel criar adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel criar adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2)));
                else {
                    vertices.get(vertice1.getInfo()).addAresta(new Aresta<>(vertices.get(vertice2)));
                    vertices.get(vertice2).addAresta(new Aresta<>(vertices.get(vertice1)));
                }
        }
    }

    public void removeAdjacencia(Vertice<T> vertice1, Vertice<T> vertice2) {
        switch (verticesExistem(vertice1.getInfo(), vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel remover adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel remover adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).removeAresta(vertices.get(vertice2.getInfo()));
                else {
                    vertices.get(vertice1.getInfo()).removeAresta(vertices.get(vertice2.getInfo()));
                    vertices.get(vertice2.getInfo()).removeAresta(vertices.get(vertice2.getInfo()));
                }
        }
    }

    public void removeAdjacencia(Vertice<T> vertice1, T vertice2) {
        switch (verticesExistem(vertice1.getInfo(), vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel remover adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel remover adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1.getInfo()).removeAresta(vertice2);
                else {
                    vertices.get(vertice1.getInfo()).removeAresta(vertice2);
                    vertices.get(vertice2).removeAresta(vertice1);
                }
        }
    }

    public void removeAdjacencia(T vertice1, T vertice2) {
        switch (verticesExistem(vertice1, vertice2)) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel remover adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel remover adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).removeAresta(vertice2);
                else {
                    vertices.get(vertice1).removeAresta(vertice2);
                    vertices.get(vertice2).removeAresta(vertice1);
                }
        }
    }

    public void removeAdjacencia(T vertice1, Vertice<T> vertice2) {
        switch (verticesExistem(vertice1, vertice2.getInfo())) {
            case Vertice1NaoPresente:
                System.err.println("Vertice: " + vertice1 + " não encontrado no grafo, impossivel remover adjacencia");
            case Vertice2NaoPresente:
                System.err.println("Vertice: " + vertice2 + " não encontrado no grafo, impossivel remover adjacencia");
            case VerticesPresentes:
                if (direcionado)
                    vertices.get(vertice1).removeAresta(vertices.get(vertice2.getInfo()));
                else {
                    vertices.get(vertice1).removeAresta(vertices.get(vertice2.getInfo()));
                    vertices.get(vertice2.getInfo()).removeAresta(vertice1);
                }
        }
    }

    public List<Vertice<T>> getVertices(){
        return new ArrayList<>(this.vertices.values());
    }

    public double CentralidadeIntermediacao(Vertice<T> vertice){
        int contador = 0;
        Collection<Vertice<T>> verticesCol = vertices.values();
        Dijkstra<T> dijkstra = new Dijkstra<>(this);
        for(Vertice<T> v: verticesCol){
            for(Vertice<T> v1: verticesCol){
                if(dijkstra.execute(v, v1 ,vertice)){
                    contador++;
                }
            }
        }
        int div = (verticesCol.size() - 1) * (verticesCol.size() - 2);
        if(direcionado)
            return contador/div;
        return 2 * contador/div;
    }

    public double centralidadePosicionamento(Vertice<T> vertice){

        double semiResult = 0.0;
        Collection<Vertice<T>> verticesCol = vertices.values();
        Dijkstra<T> dijkstra = new Dijkstra<>(this);
        for(Vertice<T> v: verticesCol){
            semiResult += dijkstra.execute(vertice, v);
        }
        if(ponderado)
            return 1/semiResult;
        return (verticesCol.size() - 1)/semiResult;
    }

    public void gerarArquivoPajek(String path) {
        System.out.println("Gerando arquivo Pajek");
        File file = new File(path.endsWith(".net") ? path : path + ".net");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("*Vertices " + vertices.size());
            bufferedWriter.newLine();
            for (Vertice<T> v : vertices.values()) {
                if (v.getRotulo().compareTo("") != 0)
                    bufferedWriter.write(v.getInfo() + "\"" + v.getRotulo() + "\"");
                else
                    bufferedWriter.write(v.getInfo().toString());
                bufferedWriter.newLine();
            }

            if (direcionado)
                bufferedWriter.write("*Arcs");
            else
                bufferedWriter.write("*Edges");
            bufferedWriter.newLine();
            for (Vertice<T> v : vertices.values()) {
                if (!v.arestaIsEmpty())
                    for (Aresta<T> a : v.getArestas()) {
                        bufferedWriter.write(v.getInfo() + " " + a.getInfo() + " " + a.getPeso());
                        bufferedWriter.newLine();
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void carregarArquivoPajek(String path) {
        System.out.println("Carregando arquivo Pajek");
        File file = new File(path.endsWith(".net") ? path : path + ".net");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            int estado = 0; //0 - Iniciando; 1 - Vertices; 2 - Edges ou Arcs
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("*Vertices"))
                    estado = 1;
                if (estado == 1) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("*Edges") || line.contains("*Arcs"))
                            break;
                        if (line.contains("\"")) {
                            if (vertices.get((T) line.substring(0, line.indexOf("\""))) != null) {
                                String test = line.substring(0, line.indexOf("\""));
                                vertices.get(line.substring(0, line.indexOf("\""))).setRotulo(line.substring(line.indexOf("\"")));
                            } else {
                                vertices.put((T) cast(line.substring(0, line.indexOf("\"") - 1)), new Vertice<T>((T) line.substring(0, line.indexOf("\"")), line.substring(line.indexOf("\"")).replace("\"", "")));
                            }
                        } else {
                            if (vertices.get((T) line) == null)
                                vertices.put((T) cast(line), new Vertice<>((T) line));
                        }
                    }
                    estado = 2;
                }
                if (estado == 2) {
                    if (line.contains("*Edges")) {
                        this.direcionado = false;
                        line = bufferedReader.readLine();
                    } else if (line.contains("*Arcs")) {
                        this.direcionado = true;
                        line = bufferedReader.readLine();
                    }
                    String[] parameters = line.split(" ");
                    if (vertices.get((T) cast(parameters[0])) == null || vertices.get((T) cast(parameters[1])) == null)
                        continue;
                    if (parameters.length == 3)
                        criaAdjacencia((T) cast(parameters[0]), (T) cast(parameters[1]), Integer.valueOf(parameters[2]));
                    else
                        criaAdjacencia((T) cast(parameters[0]), (T) cast(parameters[1]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarVerticesAqruivoPajek(String path) {
        System.out.println("Carregando arquivo Pajek");
        File file = new File(path.endsWith(".net") ? path : path + ".net");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            int estado = 0; //0 - Iniciando; 1 - Vertices; 2 - Edges ou Arcs
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("*Vertices")){
                    estado = 1;
                    line = bufferedReader.readLine();
                }
                if (estado == 1) {
                    if (line.contains("*Edges") || line.contains("*Arcs"))
                        break;
                    if (line.contains("\"")) {
                        if (vertices.get((T) line.substring(0, line.indexOf("\""))) != null) {
                            String test = line.substring(0, line.indexOf("\""));
                            vertices.get(line.substring(0, line.indexOf("\""))).setRotulo(line.substring(line.indexOf("\"")));
                        } else {
                            vertices.put((T) cast(line.substring(0, line.indexOf("\""))), new Vertice<T>((T) line.substring(0, line.indexOf("\"")), line.substring(line.indexOf("\"")).replace("\"", "")));
                        }
                    } else {
                        if (vertices.get((T) line) == null)
                            vertices.put((T) cast(line), new Vertice<>((T) line));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarGrafoAleatorio(String path, int arestas, boolean conexo) {
        carregarVerticesAqruivoPajek(path);
        Collection<Vertice<T>> verticesLista = vertices.values();
        int countArestas = 0;
        while (countArestas < arestas) {
            if (conexo) {
                for (Vertice<T> vertice : verticesLista) {
                    for (Vertice<T> vertice1 : verticesLista) {
                        if (vertice.existeAdjacencia(vertice1) == Integer.MIN_VALUE) {
                            if (vertice1 != vertice) {
                                vertice.addAresta(vertice1);
                                countArestas++;
                            }
                        }
                    }
                }
            } else
                for (Vertice<T> vertice : verticesLista) {
                    for (Vertice<T> vertice1 : verticesLista) {
                        vertice.addAresta(vertice1);
                        countArestas++;
                        if ((new Random().nextInt(2 - 1) + 1) == 2)
                            continue;
                    }
                }
        }
    }

    public int getQuantidadeDeVertices() {
        return vertices.size();
    }

    public boolean VerificaCiclo() {
        if (direcionado)
            return DCiclico();
        else
            return NDCiclico();
    }

    public boolean VeriticaConexo() {
        return DConexo();
    }

    private boolean DConexo() {
        ArrayList<Vertice<T>> visitado = new ArrayList<>();
        for (Vertice<T> vertice : vertices.values())
            if (DConexoUTil(vertice, visitado))
                return true;
        return false;
    }

    private boolean DConexoUTil(Vertice<T> vertice, ArrayList<Vertice<T>> visitado) {

        visitado.add(vertice);
        for (Aresta<T> aresta : vertice.getArestas()) {
            if (!visitado.contains(aresta.getVertice()))
                if (DConexoUTil(aresta.getVertice(), visitado))
                    return true;
        }
        return false;
    }

    private boolean DCiclico() {
        ArrayList<Vertice<T>> visitado = new ArrayList<>();
        ArrayList<Vertice<T>> recStack = new ArrayList<>();

        for (Vertice<T> vertice : vertices.values())
            if (DCiclicoUtil(vertice, visitado, recStack))
                return true;
        return false;
    }

    private boolean DCiclicoUtil(Vertice<T> vertice, ArrayList<Vertice<T>> visitado, ArrayList<Vertice<T>> recStack) {
        if (recStack.contains(vertice))
            return true;
        if (visitado.contains(vertice))
            return false;

        visitado.add(vertice);
        recStack.add(vertice);
        if (!vertice.arestaIsEmpty()) {
            for (Aresta<T> aresta : vertice.getArestas())
                if (DCiclicoUtil(aresta.getVertice(), visitado, recStack))
                    return true;
        }
        if (recStack.contains(vertice))
            recStack.remove(vertice);
        return false;
    }

    private boolean NDCiclico() {
        ArrayList<Vertice<T>> visitado = new ArrayList<>();
        for (Vertice<T> vertice : vertices.values()) {
            if (!visitado.contains(vertice))
                if (NDCiclicoUtil(vertice, visitado, null))
                    return true;
        }
        return false;
    }

    private boolean NDCiclicoUtil(Vertice<T> vertice, ArrayList<Vertice<T>> visitado, Vertice<T> pai) {
        visitado.add(vertice);
        Aresta<T> currentAresta;
        Iterator<Aresta<T>> it = vertice.getArestas().iterator();
        while (it.hasNext()) {
            currentAresta = it.next();

            if (!visitado.contains(currentAresta.getVertice())) {
                if (NDCiclicoUtil(currentAresta.getVertice(), visitado, vertice))
                    return true;
            } else if (currentAresta.getVertice() != pai)
                return true;
        }
        return false;
    }

    private Object cast(String value) {
        if (Integer.class.equals(classType)) {
            return Integer.valueOf(value);
        } else if (Double.class.equals(classType)) {
            return Double.valueOf(value);
        } else if (Float.class.equals(classType)) {
            return Float.valueOf(value);
        } else if (Boolean.class.equals(classType)) {
            return Boolean.valueOf(value);
        } else if (String.class.equals(classType)) {
            return value;
        } else
            return null;
    }
}
