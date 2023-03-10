# Performance Evaluation of a single core

In this project, we will study the effect on the processor performance of the memory hierarchy when accessing large amounts of data.

## Index

- [Descrição do problema e explicação dos algoritmos](#descrição-do-problema-e-explicação-dos-algoritmos)
- [Métricas de Desempenho](craftdocs://open?blockId=778286D2-432B-4E9A-87D7-398F598906FC&spaceId=7bd061cb-19dc-2671-33a3-185afbdd05f9)
- [Resultados e Análise](craftdocs://open?blockId=0F91E7E7-FAD4-4E09-B6E9-3B42887F1743&spaceId=7bd061cb-19dc-2671-33a3-185afbdd05f9)
- [Conclusão](craftdocs://open?blockId=F24A6FE1-2C53-4331-8391-5F65F313D16A&spaceId=7bd061cb-19dc-2671-33a3-185afbdd05f9)

## Descrição do problema e explicação dos algoritmos

Sendo este o primeiro trabalho proposto no âmbito da cadeira de Computação Paralela e Distribuída, tínhamos como objetivo comparar e analisar o desempenho de um cpu em diferentes situações apresentadas. Como é do nosso conhecimento, esse desempenho depende de diversos fatores tais como a gestão de memória num determinado programa, a utilização de diferentes algoritmos, a linguagem de programação utilizada e até das próprias características físicas do processador.

Como tal, iremos apresentar neste relatório algumas abordagens em relação à multiplicação de matrizes e identificar as principais diferenças entre cada uma delas. Para a realização destes testes utilizamos a API PAPI assim como C++ e Java como linguagens de programação.

#### Multiplicação de matrizes pelo método algébrico

O primeiro exercício deste trabalho envolvia a multiplicação de matrizes pelo método mais comumente utilizado, ou seja, o produto das matrizes A = (aij) (m x p) e B = (bij) (p x n) seria a matriz C = (cij) (m x n), em que cada elemento cij é calculado com a soma dos produtos dos elementos da linha i de A com os elementos da coluna j de B.

![Image.png](https://lh4.googleusercontent.com/_GnNICo7t-ZLB7jtIjdulsGbhZN4iuX-tdVnz0bWIvkHX4LkLkc6h8E-yeQfqSNfAlyCJNpqIqt7kvGFOsqlFSFESoSan6RR1hoLg7ePZ2IXLk1z0rxYZEYfxNgCFvDJboyWjkbJ_x5Fh21ttjI7mXE)

#### Multiplicação por linhas

Se no primeiro exercício multiplicamos os elementos da linha i de A pelos elementos da coluna j de B, nesta segunda alínea iremos multiplicar os elementos da linha i de A pelos elementos da linha i de B, verificando se existe alguma diferença de desempenho por parte do cpu.

#### Multiplicação em bloco

Agora, neste terceiro exercício, temos como objetivo dividir as nossas matrizes A e B em várias submatrizes e de seguida calcular a matriz resultado. Para isso, utilizaremos o primeiro método, ou seja, o método algébrico. Iremos, portanto, analisar se o facto de dividir a matriz em partes mais pequenas contribui para diferenças no número de operações realizadas pelo processador.

## Métricas de Desempenho

Tal como nos foi proposto, para medir o desempenho do processador no cálculo de multiplicação de matrizes utilizamos duas linguagens de programação diferentes: C++ e Java. Com isto, conseguimos medir as diferenças no tempo que cada programa demorou a correr.

Para além disso utilizamos o PAPI (Performance API) de forma a coletar valores úteis como o número de cache misses nos níveis L1 e L2 da cache.

Na avaliação de resultados tivemos também em conta a  dimensão das matrizes pelo que foi um dos aspetos propostos para este trabalho.

## Resultados e Análise

1. #### Multiplicação de matrizes pelo método algébrico

**C++ Performance:**

| Size | Time (s) | Level 1 DCM | Level 2 DCM |
| ---- | -------- | ----------- | ----------- |
| 600  | 0,182    | 244776051   | 37737956    |
| 1000 | 0,977    | 1228428924  | 214386260   |
| 1400 | 3,057    | 3508798196  | 530845856   |
| 1800 | 16,808   | 9092784330  | 3150759350  |
| 2200 | 37,375   | 17631135986 | 18512819143 |
| 2600 | 67,284   | 30907455725 | 46183298297 |
| 3000 | 113,543  | 50296860285 | 94674304564 |

![C++ Basic Multiplication DCM.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/A7804C16-BE9C-49CA-A0A7-84DC6B866417_2/tGqmwyCLiBs9np4AYthlmC15hyjnEkXKEjxZPiKQUmwz/C%20Basic%20Multiplication%20DCM.png)

Através da análise dos dados da tabela, é possível concluir que o tamanho da matriz e o tempo de execução são diretamente proporcionais. Em relação aos *data cache misses* podemos seguir a mesma conclusão, sabendo que quanto maior a matriz mais memória é acessada.

**Java Performance:**

| Size | Time (s) |
| ---- | -------- |
| 600  | 0,443    |
| 1000 | 1,533    |
| 1400 | 4,974    |
| 1800 | 18,959   |
| 2200 | 39,835   |
| 2600 | 70,101   |
| 3000 | 114,679  |

Tal como na prévia análise, sabemos que com o o aumento do tamanho da matriz é possível verificar um acréscimo de tempo na sua execução.

2. #### Multiplicação em Linha

**C++ Performance:**

| Size | Time (s) | Level 1 DCM | Level 2 DCM |
| ---- | -------- | ----------- | ----------- |
| 600  | 0,091    | 27102899    | 56652910    |
| 1000 | 0,43     | 125786944   | 261062955   |
| 1400 | 1,514    | 346297957   | 700121534   |
| 1800 | 3,183    | 745277442   | 1425674947  |
| 2200 | 6,161    | 2072120207  | 2515419153  |
| 2600 | 10,27    | 4411931302  | 4113837413  |
| 3000 | 15,753   | 6779037601  | 6332366591  |

![C++ Line Multiplication DCM.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/657B05EA-AF10-4DD2-9DC7-0BC03DD084C6_2/xXxcR24tqzWouwTsyvefv8XovghsqT6giH5qPsSWqgkz/C%20Line%20Multiplication%20DCM.png)

| Size  | Time (s) | Level 1 DCM  | Level 2 DCM  |
| ----- | -------- | ------------ | ------------ |
| 4096  | 40,279   | 17547752512  | 15707309470  |
| 6144  | 135,786  | 59147235476  | 52734959866  |
| 8192  | 322,72   | 140073542249 | 125068329036 |
| 10240 | 628,837  | 273793089062 | 250892242727 |

Em comparação com a multiplicação através do método algébrico, há uma melhoria nas métricas de desempenho, devido ao algoritmo tirar vantagem dos valores já alocados em cache. A utilização de um diferente algoritmo permite a obtenção de melhores tempos e de menores *data cache misses*.

**Java Performance:**

| Size | Time (s) |
| ---- | -------- |
| 600  | 0,362    |
| 1000 | 0,581    |
| 1400 | 1,824    |
| 1800 | 4,43     |
| 2200 | 10,978   |
| 2600 | 18,211   |
| 3000 | 28,094   |

Em Java, apesar de o tempo de execução ser menor com a utilização de outro algoritmo, ainda existe um acréscimo ao de tempo na sua execução em comparação com C++.

**C++ vs Java:**

![C++ Time Performance.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/C3FB3615-6489-4D57-B9F3-DCEA4F3767F8_2/IvnX3xYdU7xBE1uFWqOm2mnlxlefm6V3em9VMlWDCT4z/C%20Time%20Performance.png)

![Java Time Performance.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/BF573BB4-781F-45A4-8385-294F212DBAD4_2/2qvxUuULp8jGKI6Vx2b5C4kGQkMGgfXgTccHuuSYox8z/Java%20Time%20Performance.png)

A partir dos gráficos acima e dos resultados obtidos, que não há uma diferença significativa no tempo de execução dos algoritmos, independentemente da utilização de C++ ou de Java, sendo o mais evidente, o tempo de execução em matrizes de dimensões superiores na multiplicação em linha.

3. #### Multiplicação por Blocos

**C++ Performance:**

**Block Size = 128**

| Size  | Time (s) | Level 1 DCM | Level 2 DCM  |
| ----- | -------- | ----------- | ------------ |
| 4096  | 38,866   | 9816072352  | 33532269676  |
| 6144  | 131,067  | 9131318958  | 112528948104 |
| 8192  | 281,226  | 8765175603  | 262099357363 |
| 10240 | 618,003  | 33119770223 | 508501463014 |

**Block Size = 256**

| Size  | Time (s) | Level 1 DCM | Level 2 DCM  |
| ----- | -------- | ----------- | ------------ |
| 4096  | 34,87    | 30810947444 | 23616455632  |
| 6144  | 118,091  | 29605837694 | 76446923679  |
| 8192  | 402,54   | 78525145239 | 160980671603 |
| 10240 | 564,167  | 73089269641 | 352351742155 |

**Block Size = 512**

| Size  | Time (s) | Level 1 DCM  | Level 2 DCM  |
| ----- | -------- | ------------ | ------------ |
| 4096  | 40,795   | 7022062687   | 19395802889  |
| 6144  | 107,541  | 153312269817 | 66233153737  |
| 8192  | 341,662  | 142601487187 | 138084006642 |
| 10240 | 514,313  | 136893056661 | 307158357141 |

![C++ Algorithm Time Comparison.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/EA782CA0-0109-4B4F-98BD-4387563CC8FB_2/vvqoaDp9BCgSMRc0uD1TWrDtrdENS74eAX7UsyputRwz/C%20Algorithm%20Time%20Comparison.png)

![C++ Level 1 DCM.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/9FDED5D8-03BF-4751-88AA-2E6717BF9963_2/AoycmRKZmOPuUGd6WD8D91v99jkyYIpRznuGvIdQwNQz/C%20Level%201%20DCM.png)

![C++ Level 2 DCM.png](https://res.craft.do/user/full/7bd061cb-19dc-2671-33a3-185afbdd05f9/9CBE34C8-CDF3-4D2E-A169-BDDB837B1E11_2/D4MJYd9O3YEupOuBLx8GHfvOFTlCLZQEelZHNyE2Y2sz/C%20Level%202%20DCM.png)

## Conclusão

