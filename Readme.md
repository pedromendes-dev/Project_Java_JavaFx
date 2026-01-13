## Project Faculdade Contas a Pagar e Receber em Java && JavaFX

###  Descrição do projeto:
> Este projeto é um sistema simples de Contas a Pagar e Receber desenvolvido em Java utilizando JavaFX para a interface gráfica. O sistema permite aos usuários gerenciar suas contas, registrando despesas e receitas, além de visualizar relatórios financeiros básicos.

### Arquitetura do projeto:
- O projeto segue uma arquitetura MVC (Model-View-Controller) para separar a lógica de negócios, a interface do usuário e o controle de fluxo.
- Model: Classes que representam as entidades do sistema, como Conta, Usuario, etc.
- View: Interfaces gráficas desenvolvidas com JavaFX para interação com o usuário.
- Controller: Classes que gerenciam a lógica de negócios e a comunicação entre o Model e a View.

### Ferramentas usadas no projeto:
- Java 11
- JavaFX 11
- Maven para gerenciamento de dependências


target
- target é a pasta de saída do build do Maven. É onde o Maven coloca todos os artefatos gerados pelo processo de build — classes compiladas, recursos processados e o(s) JAR/WAR/ZIP resultante(s).
  É uma pasta gerada automaticamente (artefatos “build”) — não é código-fonte e não deve entrar no controle de versão.
  O que você normalmente encontra dentro de target
  classes/ — classes Java compiladas (.class) e recursos copiados (ex.: arquivos de src/main/resources).
  test-classes/ — classes compiladas dos testes.
  generated-sources/ — código gerado por plugins (se houver).
  maven-status/ — metadados do build.
  *.jar ou *-SNAPSHOT.jar — o artefato empacotado quando você roda mvn package.
  surefire-reports/ — relatórios de testes unitários gerados pelo plugin Surefire.
  site/, dependency/ ou outras pastas geradas por plugins (dependendo do build).
  arquivos temporários e logs do build.


### - Pontos principais do projeto:
JavaFX
- JavaFX é uma biblioteca gráfica para construir interfaces de usuário em Java. Ela oferece uma ampla gama de componentes gráficos, como botões, tabelas e formulários, facilitando o desenvolvimento de aplicações desktop ricas em recursos.

Front‑end gráfico do JavaFX,
- No JavaFX, Label é um componente gráfico usado para mostrar texto na tela (como uma etiqueta ou título).
- Front‑end desktop (JavaFX) → feito com classes Java como Label, Button, Stage, Scene, exibido em janelas de aplicativos no computador.


---

### Principais classes do JavaFX usadas no projeto:

Stage
- O Stage é a janela principal do aplicativo JavaFX onde todos os elementos gráficos são exibidos.

Label
- O Label é um componente gráfico usado para mostrar texto na tela (como uma etiqueta ou título).

Button
- O Button é um componente gráfico que representa um botão clicável na interface do usuário.

VBox
- O VBox é um contêiner que organiza seus elementos filhos em uma única coluna vertical.
- É uma classe do JavaFX que representa um layout vertical.
Ela organiza os elementos um em baixo do outro, como uma pilha.

----

setStyle
 
- Estou usando o método `.setStyle` para aplicar estilos CSS diretamente aos componentes JavaFX. Isso permite personalizar a aparência dos elementos da interface do usuário, como cores, fontes e tamanhos, de forma rápida e fácil.
- O estilo é passado em forma de String porque o método setStyle espera um texto que contenha regras CSS.


setTitle
- O método `setTitle` é usado para definir o título da janela principal (Stage) do aplicativo JavaFX. Esse título é exibido na barra de título da janela quando o aplicativo está em execução.

setScene
- O método `setScene` é usado para definir a cena (Scene) que será exibida na janela principal (Stage) do aplicativo JavaFX. A cena contém todos os elementos gráficos que serão renderizados na janela.

show
- O método `show` é usado para exibir a janela principal (Stage) do aplicativo JavaFX na tela. Quando chamado, ele torna a janela visível para o usuário.


----
- O que é DAO
- DAO = Data Access Object.
- É um padrão de projeto cuja responsabilidade principal é encapsular todo o código que acessa a fonte de dados (SQL/JDBC, arquivos, NoSQL etc.).
- O DAO isola a lógica de persistência do restante da aplicação, facilitando manutenção, testes e troca de tecnologia (por exemplo, trocar JDBC por JPA ou outro banco).
Responsabilidades de um DAO
Conectar ao banco (via utilitária/connection pool) — normalmente usa uma classe utilitária (ex.: DBUtil) ou um DataSource.
Executar queries/updates (INSERT, SELECT, UPDATE, DELETE).
Transformar ResultSet em objetos do domínio (ex.: ResultSet -> Cliente).
Lançar/expor exceções apropriadas para camadas superiores (ou encapsular em exceptions customizadas).
Não deve conter: regras de negócio, validações complexas, UI, transações de negócio (essas ficam em Service).

### Como rodar o projeto

- Requisitos:
  - Java 17+
  - Maven 3.6+

- Para compilar e executar a aplicação (JavaFX):

```powershell
mvn -f "C:\Users\Pedro\OneDrive\Área de Trabalho\Faculdade_project\contasPagarReceber\pom.xml" -DskipTests package
mvn -f "C:\Users\Pedro\OneDrive\Área de Trabalho\Faculdade_project\contasPagarReceber\pom.xml" javafx:run
```

- Arquivo de configuração do banco de dados: `src/main/resources/application.properties`.
  - Se estiver usando MySQL (Workbench), atualize `db.host`, `db.port`, `db.name`, `db.user` e `db.password`.
  - Para testes automatizados o projeto usa H2 em memória (arquivo `src/test/resources/application.properties`).

### Testes automatizados
- Os testes usam JUnit 5 e H2 em memória. Para rodar todos os testes:

```powershell
mvn -f "C:\Users\Pedro\OneDrive\Área de Trabalho\Faculdade_project\contasPagarReceber\pom.xml" -U test
```

- Testes incluídos:
  - `DBUtilTest` — valida conexão com o banco (usa H2 durante testes).
  - `ClienteDAOTestIT` — cria schema e testa CRUD de `Cliente`.
  - `ContaDAOTestIT` — testa CRUD de `Conta`.
  - `MovimentacaoDAOTestIT` — testa insert/list de movimentação.

### Notas sobre encoding e acentuação
- Alguns sistemas e ambientes (IDE ou ferramenta de build) podem salvar arquivos com encoding diferente (por exemplo, CP1252). Para evitar problemas com acentuação:
  - Configure seu editor/IDE para salvar arquivos em UTF-8.
  - O `pom.xml` do projeto foi configurado para usar `UTF-8` ao copiar recursos (`maven-resources-plugin`).
  - Em FXML eu substituí textos acentuados por entidades numéricas (ex.: `Lan&#231;ar`) e adicionei escapes Unicode em strings Java para prevenir problemas.

### Scripts de banco
- `src/main/resources/db/schema_mysql.sql` — script para criar schema/tabelas (clientes, contas, movimentacao). Pode ser usado no MySQL Workbench ou adaptado para outro SGBD.

### Próximos passos sugeridos
- Adicionar validações e máscaras de entrada (CPF/CNPJ, campos monetários).
- Implementar camadas de serviço com transações para operações que envolvem múltiplos DAOs.
- Adicionar testes de UI (TestFX) se desejar automação de interface.
