# Projeto de automação de testes da API Votes do The CatApi
&nbsp;
>Este projeto de automação em RestAssured tem como objetivo realizar cenários de testes nos três verbos HTTP (GET, POST, DELETE) para a API disponibilizada no endpoint: [https://api.thecatapi.com/v1/votes](https://api.thecatapi.com/v1/votes).

## Estrutura do Projeto

>- **com.catapi.votes:** Contém a classe `VotesTest` que possui os cenários de testes automatizados.

## ⚒️ Recursos utilizados

>Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:
### Tecnologias
- [Java 11](https://www.java.com/)
- [Maven 4.0.0](https://maven.apache.org/ "Maven")
- [RestAssured](https://rest-assured.io/)
- [JUnit](https://junit.org/)

## Configuração do Ambiente

1. Clone este repositório:

```bash
git clone https://github.com/PSVLOPES/automatiza_catapi.git
```

2. Abra o IntelliJ ou sua IDE preferida e importe o projeto.
   

3. Execute os testes:

   - Navegue até `src/test/java/com/catapi/votes` e execute a classe `VotesTest`.

## Execução dos Testes

Os testes contidos na classe `VotesTest` realizam as seguintes ações:

1. **Verbo GET:** Realiza uma requisição GET para o endpoint e valida o status code (200) e o(s) votos.

2. **Verbo POST:** Envia uma requisição POST com um corpo JSON e valida o status code (201) e o registro de um voto.

3. **Verbo DELETE:** Envia uma requisição DELETE para um recurso específico e valida o status code (200) e se o registro do voto foi apagado.

4. **JSON Schema e HTTP Code:** Realiza uma requisição GET para o endpoint e valida o JSON Schema utilizando arquivos externos que estão em (`src/resources/schemas/*.json`).


## Autor

- Pablo Lopes (Sensedia)

