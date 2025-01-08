package org.example;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        /**
         * Simular um importador de CSV para criar um script SQL
         * Banco de dado Postgres , cuja a tabela produtos tem a seguinte estrutura
         * produtos (
         *  id_produto SERIAL UNIQUE PRIMARY KEY,
         *  nome VARCHAR(50) NOT NULL,
         *  descricao VARCHAR(100) NOT NULL,
         *  preco NUMERIC NOT NULL
         *  );
         * */

        // lendo o arquivo csv e gerendo um array de string com os dados para import
        List<List<String>> registros = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/example/book.csv"))){
            String line;
            // verificando linha por linha do arquivo
            while((line = br.readLine()) != null ){
                String[] novosRegistro = line.split(",");
                registros.add(Arrays.asList(novosRegistro));
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
        StringBuilder sqlScrip = new StringBuilder();
        // vamos criar essa variavel para acompanhar as linhas que iram percorrer o laco
        int linha = 1;
        // percorrendo a lista
        for (List<String> l : registros){
            String nome = l.get(0).trim();
            String descricao = l.get(1).trim();
            String preco = l.get(2).trim();

            // verifica se tem algum campo nulo para geracao do script SQL
            if(!nome.isEmpty() && !descricao.isEmpty() && !preco.isEmpty() && preco.matches("-?\\d+(\\.\\d+)?") ) {
                sqlScrip.append("INSERT INTO produtos (nome, descricao, preco ) VALUES('").append(nome).append("', ").
                                                                                            append("'").append(descricao).append("',").
                                                                                            append(preco).append(");").
                                                                                            append("\n");
            }else {
                System.out.println("Campo incorreto na linha : " + linha);
                System.out.println("Campo  : " + l);
            }
            linha++;
        }

        // escrevendo o arquivo do script
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/org/example/scriptescrito.txt"))){
        bw.write(String.valueOf(sqlScrip));

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }









    }
}