# Performance Evaluation of a single core

In this project, we will study the effect on the processor performance of the memory hierarchy when accessing large amounts of data.

## Index

- [Descrição do problema e explicação dos algoritmos](#descrição-do-problema-e-explicação-dos-algoritmos)
- [Métricas de Desempenho](#métricas-de-desempenho)
- [Resultados e Análise](#resultados-e-análise)
- [Conclusão](#conclusão)

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

<table align=center>
  <td>
    <table> 
        <tr>
          <td>Size</td>
          <td>Time (s)</td>
          <td>Level 1 DCM</td>
          <td>Level 2 DCM</td>
        </tr>
        <tr>
          <td>600</td>
          <td>0,182</td>
          <td>244776051</td>
          <td>37737956</td>
        </tr>
        <tr>
          <td>1000</td>
          <td>0,977</td>
          <td>1228428924</td>
          <td>214386260</td>
        </tr>
        <tr>
          <td>1400</td>
          <td>3,057</td>
          <td>3508798196</td>
          <td>530845856</td>
        </tr>
        <tr>
          <td>1800</td>
          <td>16,808</td>
          <td>9092784330</td>
          <td>3150759350</td>
        </tr>
        <tr>
          <td>2200</td>
          <td>37,375</td>
          <td>17631135986</td>
          <td>18512819143</td>
        </tr>
        <tr>
          <td>2600</td>
          <td>67,284</td>
          <td>30907455725</td>
          <td>46183298297</td>
        </tr>
        <tr>
          <td>3000</td>
          <td>113,543</td>
          <td>50296860285</td>
          <td>94674304564</td>
        </tr>
      </table>
  </td>
  <td>
    <img style="width:350px" src="../Results and Analysis/C++ Basic Multiplication DCM.png"/>
    <p align=center>Figura 1: C++ Basic Multiplication DCM</p>
  </td>
</table>

Através da análise dos dados da tabela, é possível concluir que o tamanho da matriz e o tempo de execução são diretamente proporcionais. Em relação aos *data cache misses* podemos seguir a mesma conclusão, sabendo que quanto maior a matriz mais memória é acessada.

**Java Performance:**

<table align=center>
  <td>
    <table>
      <tr>
          <td>Size</td>
          <td>Time (s)</td>
      </tr>
      <tr>
          <td>600</td>
          <td>0,443</td>
      </tr>
      <tr>
          <td>1000</td>
          <td>1,533</td>
      </tr>
      <tr>
          <td>1400</td>
          <td>4,974</td>
      </tr>
    </table>
  </td>
  <td>
    <table>
      <tr>
          <td>1800</td>
          <td>18,959</td>
      </tr>
      <tr>
          <td>2200</td>
          <td>39,835</td>
      </tr>
      <tr>
          <td>2600</td>
          <td>70,101</td>
      </tr>
      <tr>
          <td>3000</td>
          <td>114,679</td>
      </tr>
    </table>
  </td>
</table>

Tal como na prévia análise, sabemos que com o o aumento do tamanho da matriz é possível verificar um acréscimo de tempo na sua execução.

2. #### Multiplicação em Linha

**C++ Performance:**

<table align=center>
  <td>
    <table>
      <tr>
          <td>Size</td>
          <td>Time (s)</td>
          <td>Level 1 DCM</td>
          <td>Level 2 DCM</td>
      </tr>
      <tr>
          <td>600</td>
          <td>0,091</td>
          <td>27102899</td>
          <td>56652910</td>
      </tr>
      <tr>
          <td>1000</td>
          <td>0,43</td>
          <td>125786944</td>
          <td>261062955</td>
      </tr>
      <tr>
          <td>1400</td>
          <td>1,514</td>
          <td>346297957</td>
          <td>700121534</td>
      </tr>
      <tr>
          <td>1800</td>
          <td>3,183</td>
          <td>745277442</td>
          <td>1425674947</td>
      </tr>
      <tr>
          <td>2200</td>
          <td>6,161</td>
          <td>2072120207</td>
          <td>2515419153</td>
      </tr>
      <tr>
          <td>2600</td>
          <td>10,27</td>
          <td>4411931302</td>
          <td>4113837413</td>
      </tr>
      <tr>
          <td>3000</td>
          <td>15,753</td>
          <td>6779037601</td>
          <td>6332366591</td>
      </tr>
    </table>
  </td>
  <td>
    <img style="width:350px" src="../Results and Analysis/C++ Line Multiplication DCM.png"/>
    <p align=center>Figura 2: C++ Line Multiplication DCM</p>
  </td>
</table>

<table align=center>
    <tr>
        <td>Size</td>
        <td>Time (s)</td>
        <td>Level 1 DCM</td>
        <td>Level 2 DCM</td>
    </tr>
    <tr>
        <td>4096</td>
        <td>40,279</td>
        <td>17547752512</td>
        <td>15707309470</td>
    </tr>
    <tr>
        <td>6144</td>
        <td>135,786</td>
        <td>59147235476</td>
        <td>52734959866</td>
    </tr>
    <tr>
        <td>8192</td>
        <td>322,72</td>
        <td>140073542249</td>
        <td>125068329036</td>
    </tr>
    <tr>
        <td>10240</td>
        <td>628,837</td>
        <td>273793089062</td>
        <td>250892242727</td>
    </tr>
</table>

Em comparação com a multiplicação através do método algébrico, há uma melhoria nas métricas de desempenho, devido ao algoritmo tirar vantagem dos valores já alocados em cache. A utilização de um diferente algoritmo permite a obtenção de melhores tempos e de menores *data cache misses*.

**Java Performance:**

<table align=center>
    <tr>
        <td>Size</td>
        <td>Time (s)</td>
    </tr>
    <tr>
        <td>600</td>
        <td>0,362</td>
    </tr>
    <tr>
        <td>1000</td>
        <td>0,581</td>
    </tr>
    <tr>
        <td>1400</td>
        <td>1,824</td>
    </tr>
    <tr>
        <td>1800</td>
        <td>4,43</td>
    </tr>
    <tr>
        <td>2200</td>
        <td>10,978</td>
    </tr>
    <tr>
        <td>2600</td>
        <td>18,211</td>
    </tr>
    <tr>
        <td>3000</td>
        <td>28,094</td>
    </tr>
</table>

Em Java, apesar de o tempo de execução ser menor com a utilização de outro algoritmo, ainda existe um acréscimo ao de tempo na sua execução em comparação com C++.

**C++ vs Java:**

<table align=center>
  <td>
    <img style="width:350px" src="../Results and Analysis/C++ Time Performance.png"/>
    <p align=center>Figura 2: C++ Time Performance</p>
  </td>
  <td>
    <img style="width:350px" src="../Results and Analysis/Java Time Performance.png"/>
    <p align=center>Figura 3: Java Time Performance</p>
  </td>
</table>

A partir dos gráficos acima e dos resultados obtidos, que não há uma diferença significativa no tempo de execução dos algoritmos, independentemente da utilização de C++ ou de Java, sendo o mais evidente, o tempo de execução em matrizes de dimensões superiores na multiplicação em linha.

3. #### Multiplicação por Blocos

**C++ Performance:**

**Block Size = 128**

<table align=center>
  <tr>
      <td>Size</td>
      <td>Time (s)</td>
      <td>Level 1 DCM</td>
      <td>Level 2 DCM</td>
  </tr>
  <tr>
      <td>4096</td>
      <td>38,866</td>
      <td>9816072352</td>
      <td>33532269676</td>
  </tr>
  <tr>
      <td>6144</td>
      <td>131,067</td>
      <td>9131318958</td>
      <td>112528948104</td>
  </tr>
  <tr>
      <td>8192</td>
      <td>281,226</td>
      <td>8765175603</td>
      <td>262099357363</td>
  </tr>
  <tr>
      <td>10240</td>
      <td>618,003</td>
      <td>33119770223</td>
      <td>508501463014</td>
  </tr>
</table>

**Block Size = 256**

<table align=center>
  <tr>
      <td>Size</td>
      <td>Time (s)</td>
      <td>Level 1 DCM</td>
      <td>Level 2 DCM</td>
  </tr>
  <tr>
      <td>4096</td>
      <td>34,87</td>
      <td>30810947444</td>
      <td>23616455632</td>
  </tr>
  <tr>
      <td>6144</td>
      <td>118,091</td>
      <td>29605837694</td>
      <td>76446923679</td>
  </tr>
  <tr>
      <td>8192</td>
      <td>402,54</td>
      <td>78525145239</td>
      <td>160980671603</td>
  </tr>
  <tr>
      <td>10240</td>
      <td>564,167</td>
      <td>73089269641</td>
      <td>352351742155</td>
  </tr>
</table>

**Block Size = 512**

<table align=center>
  <tr>
      <td>Size</td>
      <td>Time (s)</td>
      <td>Level 1 DCM</td>
      <td>Level 2 DCM</td>
  </tr>
  <tr>
      <td>4096</td>
      <td>40,795</td>
      <td>7022062687</td>
      <td>19395802889</td>
  </tr>
  <tr>
      <td>6144</td>
      <td>107,541</td>
      <td>153312269817</td>
      <td>66233153737</td>
  </tr>
  <tr>
      <td>8192</td>
      <td>341,662</td>
      <td>142601487187</td>
      <td>138084006642</td>
  </tr>
  <tr>
      <td>10240</td>
      <td>514,313</td>
      <td>136893056661</td>
      <td>307158357141</td>
  </tr>
</table>

<div align=center>
  <img style="width:350px;" src="../Results and Analysis/C++ Algorithm Time Comparison.png"/>
  <p align=center>Figura 4: C++ Algorithm Time Comparison</p>
</div>

<table align=center>
  <td>
    <img style="width:350px" src="../Results and Analysis/C++ Level 1 DCM.png"/>
    <p align=center>Figura 5: C++ Level 1 DCM</p>
  </td>
  <td>
    <img style="width:350px" src="../Results and Analysis/C++ Level 2 DCM.png"/>
    <p align=center>Figura 5: C++ Level 2 DCM</p>
  </td>
</table>


## Conclusão

