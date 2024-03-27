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

## Descrições das Consultas (Queries)

### Consulta 1: Produtores com Intervalos Mínimos e Máximos

- **Propósito:** Obter o produtor com o maior intervalo entre dois prêmios consecutivos, assim como o produtor que recebeu dois prêmios mais rapidamente.
- **Consulta SQL:** Consulte a consulta SQL rotulada como "producer-controller" no código-fonte.
- **Endpoint:** `/api/producer/min-max`

## Resumo da Consulta SQL

```
WITH movie_producer_titles AS (
    SELECT
        m.release_year AS release_year,
        p.name AS producer,
        mv.title AS title
    FROM
        movie_producer mp
        JOIN producer p ON mp.id_producer = p.id_producer
        JOIN movie m ON mp.id_movie = m.id_movie
        JOIN movie mv ON mv.id_movie = mp.id_movie
    WHERE
        m.flg_winner = true
),
movie_win_intervals AS (
    SELECT
        producer,
        release_year AS previous_win_year,
        LEAD(release_year) OVER (PARTITION BY producer ORDER BY release_year) AS following_win_year,
        LAG(title) OVER (ORDER BY release_year) AS previous_movie,
        title AS following_movie
    FROM
        movie_producer_titles
)
SELECT
    producer,
    MIN(following_win_year - previous_win_year) AS yearInterval,
    previous_win_year,
    following_win_year
FROM
    movie_win_intervals
WHERE
    following_win_year IS NOT NULL
GROUP BY
    producer,
    previous_win_year,
    following_win_year
HAVING
    yearInterval = (
        SELECT MIN(following_win_year - previous_win_year)
        FROM movie_win_intervals
        WHERE following_win_year IS NOT NULL
    )
ORDER BY
    yearInterval ASC;
```

### Common Table Expressions (CTEs)

#### `movie_producer_titles`

Esta CTE seleciona os anos de lançamento dos filmes, seus produtores e títulos correspondentes. Ela realiza uma junção entre as tabelas `movie_producer`, `producer`, `movie` e `movie` (novamente) para obter os dados necessários. O filtro `m.flg_winner = true` é aplicado para garantir que apenas os filmes vencedores sejam considerados.

#### `movie_win_intervals`

Esta CTE calcula os anos de lançamento dos filmes vencedores e seus intervalos entre prêmios consecutivos. Utiliza a CTE anterior (`movie_producer_titles`) e funções analíticas como `LEAD` e `LAG` para determinar os anos de lançamento dos prêmios anterior e seguinte, respectivamente, para cada produtor. Isso é feito particionando os dados por produtor e ordenando por ano de lançamento.

### Consulta Principal

A consulta principal utiliza a CTE `movie_win_intervals` para calcular o intervalo mínimo entre dois prêmios consecutivos para cada produtor. Seleciona o produtor, o intervalo mínimo (`yearInterval`), o ano do prêmio anterior e o ano do prêmio seguinte. Os resultados são agrupados por produtor, ano do prêmio anterior e ano do prêmio seguinte. Há uma cláusula `HAVING` que filtra os resultados para incluir apenas os registros em que o `yearInterval` é igual ao menor intervalo encontrado na consulta. Finalmente, os resultados são ordenados pelo `yearInterval` em ordem ascendente.

Essencialmente, essa consulta retorna os produtores com o menor intervalo entre dois prêmios consecutivos. Se houver empates, todos os produtores com o menor intervalo serão incluídos no resultado.


### Consulta 2: Produtores com os Maiores Intervalos entre Prêmios Consecutivos

- **Propósito:** Obter os produtores com os maiores intervalos entre dois prêmios consecutivos, incluindo empates.
- **Consulta SQL:** Consulte a consulta SQL rotulada como "producer-controller" no código-fonte.
- **Endpoint:** `/api/producer/max-interval`

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE). Sinta-se à vontade para modificar e distribuir o código para uso pessoal ou comercial.
 ```