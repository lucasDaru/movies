# Aplicação Movie TexoIT

Este projeto é uma aplicação Spring Boot projetada para gerenciar dados de filmes. Ele utiliza Spring Boot juntamente com Spring Data JPA para persistência de dados e fornece endpoints RESTful para interação com os dados. O Swagger está integrado ao projeto para oferecer documentação da API e capacidades de teste de integração.

## Instruções de Configuração

Para executar a aplicação Movie TexoIT, siga estas etapas:

1. **Clonar o Repositório:**  
   Clone o repositório do projeto de onde ele está hospedado.

    ```bash
   git clone https://github.com/lucasDaru/movies.git
    ```

2. **Construir o Projeto:**  
   Navegue até o diretório do projeto e construa o projeto usando o Maven.

    ```bash
   cd movie
   mvn clean package
    ```

3. **Executar a Aplicação:**  
   Após a construção ser bem-sucedida, execute a aplicação usando o plugin Spring Boot Maven.

    ```bash
   mvn spring-boot:run
    ```

4. **Acessar o Swagger UI para Teste de Integração:**  
   Depois que a aplicação estiver em execução, você pode acessar o Swagger UI em seu navegador da web, navegando até a seguinte URL:

   ```bash 
   http://localhost:8080/swagger-ui/index.html
   ```

6. O Swagger UI fornece documentação interativa para os endpoints RESTful expostos pela aplicação, permitindo que você explore e teste as funcionalidades da API.


## Endpoints da API

- **Controlador de Filmes (Movie Controller):**
- Importar Arquivo CSV: `/api/movie/import/csv`

- **Controlador de Produtores (Producer Controller):**
- Obter Produtores com Intervalos Mínimos e Máximos: `/api/producer/min-max`

## Esquema CSV e Tabelas do Banco de Dados

A aplicação trabalha com dados de filmes fornecidos em formato CSV com as seguintes colunas:

   ```bash 
   year;title;studios;producers;winner
   ```

Os dados são mapeados para as seguintes tabelas do banco de dados:

- **Filme (Movie)**
- **Produtor (Producer)**
- **Estúdio (Studio)**
- **Filme_Produtor (Movie_Producer)**
- **Filme_Estúdio (Movie_Studio)**

# Detalhes do Método findProducerIntervalsMinMax

Este método é crucial para encontrar os intervalos de anos mínimos e máximos entre as vitórias dos produtores de filmes.

## Funcionamento do Método

1. **Obtenção dos Produtores Vencedores**: Primeiro, o método obtém todos os produtores de filmes vencedores utilizando o método `findAllByMovieWinner` do repositório `repository`.

2. **Iteração sobre os Produtores**: Em seguida, itera sobre cada produtor e seus respectivos filmes.

3. **Filtragem e Ordenação dos Filmes**: Para cada produtor, filtra os filmes que são vencedores e os ordena pelo ano de produção.

4. **Cálculo dos Intervalos de Anos**: Calcula os intervalos de anos entre as vitórias consecutivas de cada produtor.

5. **Manutenção dos Intervalos Mínimos e Máximos**: Mantém apenas os intervalos mínimos e máximos, armazenando-os em um mapa.

6. **Construção do Mapa de Saída**: Finalmente, constrói um mapa de saída onde associa a chave "min" aos intervalos mínimos e a chave "max" aos intervalos máximos e retorna esse mapa.

- **Propósito:** Obter os produtores com os maiores intervalos entre dois prêmios consecutivos, incluindo empates.
- **Endpoint:** `/api/producer/min-max`

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE). Sinta-se à vontade para modificar e distribuir o código para uso pessoal ou comercial.
 ```