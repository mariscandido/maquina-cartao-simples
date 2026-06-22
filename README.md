# maquina-cartao-simples

Simulador de máquina de cartão em Java, com operações básicas de crédito e débito, cálculo de taxas e geração de comprovantes. Projeto didático inspirado em adquirentes como Cielo, Rede e Stone, com instruções claras para execução em qualquer ambiente.

## Funcionalidades

- **Processamento de transações**: Suporte a transações de crédito (taxa de 5%) e débito (taxa de 2%)
- **Cancelamento de transações**: Possibilidade de cancelar transações, zerando o valor líquido
- **Listagem de transações**: Visualização de todas as transações realizadas
- **Geração de comprovantes**: Emissão de comprovantes detalhados no console
- **Tratamento de exceções**: Validação de valores e tipos de transação
- **Arquitetura extensível**: Estrutura preparada para futuras integrações (API REST, banco de dados)

## Estrutura do Repositório

```
maquina-cartao-simples/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── itau/
│   │               └── maquinacartao/
│   │                   ├── App.java
│   │                   ├── model/
│   │                   │   └── Transacao.java
│   │                   ├── service/
│   │                   │   └── MaquinaCartaoService.java
│   │                   └── exception/
│   │                       └── TransacaoException.java
│   └── test/
│       └── java/
│           └── com/
│               └── itau/
│                   └── maquinacartao/
│                       └── MaquinaCartaoServiceTest.java
├── docs/
│   └── Documentacao.doc
├── pom.xml
├── README.md
├── .gitignore
└── LICENSE
```

## Instalação e Execução

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

### Passos de Instalação

1. Clone o repositório:
```bash
git clone <repository-url>
cd maquina-cartao-simples
```

2. Compile o projeto com Maven:
```bash
mvn clean compile
```

3. Execute os testes:
```bash
mvn test
```

4. Execute a aplicação:
```bash
mvn exec:java -Dexec.mainClass="com.itau.maquinacartao.App"
```

Ou compile e execute o JAR:
```bash
mvn package
java -jar target/maquina-cartao-simples-1.0.0.jar
```

## Exemplos de Uso

### Processar uma transação de crédito

```java
MaquinaCartaoService maquina = new MaquinaCartaoService();
Transacao transacao = maquina.processarTransacao("CREDITO", 100.0);
System.out.println(transacao);
```

**Saída esperada:**
```
Transação A1B2C3D4 [CREDITO] - Valor: R$100.0 | Taxa: R$5.0 | Líquido: R$95.0 | Status: Concluída | Data: 2026-06-21T20:00:00
```

### Processar uma transação de débito

```java
Transacao transacao = maquina.processarTransacao("DEBITO", 200.0);
System.out.println(transacao);
```

**Saída esperada:**
```
Transação E5F6G7H8 [DEBITO] - Valor: R$200.0 | Taxa: R$4.0 | Líquido: R$196.0 | Status: Concluída | Data: 2026-06-21T20:00:01
```

### Cancelar uma transação

```java
maquina.cancelarTransacao(transacao.getId());
System.out.println(transacao);
```

**Saída esperada:**
```
Transação A1B2C3D4 [CREDITO] - Valor: R$100.0 | Taxa: R$5.0 | Líquido: R$0.0 | Status: Cancelada | Data: 2026-06-21T20:00:00
```

### Listar todas as transações

```java
List<Transacao> transacoes = maquina.listarTransacoes();
for (Transacao t : transacoes) {
    System.out.println(t);
}
```

### Gerar comprovante

```java
maquina.gerarComprovante(transacao.getId());
```

**Saída esperada:**
```
========================================
        COMPROVANTE DE TRANSAÇÃO       
========================================
Transação A1B2C3D4 [CREDITO] - Valor: R$100.0 | Taxa: R$5.0 | Líquido: R$95.0 | Status: Concluída | Data: 2026-06-21T20:00:00
========================================
```

## Testes Unitários

O projeto inclui testes unitários abrangentes seguindo a estratégia TAAC:

- Teste de criação de transação válida (crédito e débito)
- Teste de cálculo correto das taxas
- Teste de cancelamento de transação
- Teste de exceções para valores inválidos
- Teste de exceções para tipo inválido
- Teste de listagem de transações
- Teste de geração de comprovante

Para executar os testes:
```bash
mvn test
```

## Regras de Negócio

### Tipos de Transação

- **CRÉDITO**: Taxa de 5% sobre o valor bruto
- **DÉBITO**: Taxa de 2% sobre o valor bruto

### Estrutura da Transação

Cada transação contém:
- ID único (gerado automaticamente)
- Tipo (CREDITO ou DEBITO)
- Valor bruto
- Taxa aplicada
- Valor líquido (valor bruto - taxa)
- Data e hora da transação
- Status (Concluída ou Cancelada)

### Exceções

- **Valor inválido**: Transações com valor menor ou igual a zero são rejeitadas
- **Tipo inválido**: Apenas CREDITO e DEBITO são aceitos

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## Autor

**Mariana Silveira**

## Futuras Evoluções

- Integração com API REST
- Persistência em banco de dados
- Suporte a múltiplos tipos de cartão
- Integração com gateways de pagamento reais
- Interface gráfica para operações
