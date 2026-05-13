1. Objetivo Geral

Desenvolver uma plataforma de gamificação de estudos voltada a fomentar constância e competitividade saudável por meio de grupos de estudo, métricas de produtividade e registros visuais de atividades.

2. Público-Alvo

Estudantes Autônomos: Indivíduos que buscam motivação extra e ferramentas para rastrear seu progresso diário.

Grupos de Estudo e Coletivos Acadêmicos: Pessoas que desejam compartilhar rotinas, comparar desempenhos e manter um ambiente de responsabilidade mútua (accountability).

3. Impacto Esperado

O sistema visa aumentar a retenção dos usuários em seus cronogramas de estudo por meio do gatilho psicológico da "streak" (ofensiva) e da validação social. Espera-se que a funcionalidade de grupos e rankings transforme o estudo em uma experiência coletiva, aumentando o senso de realização pessoal através do registro visual (fotos de resumos, livros ou telas).

4. Requisitos Funcionais

- [RF01] Gerenciamento de Perfil: O usuário deve ser capaz de criar uma conta, definir um username (@) e visualizar suas estatísticas gerais (tempo total, quantidade de atividades e streak).

- [RF02] Registro de Atividade: O sistema deve permitir o upload de uma foto, título, descrição e seleção de categorias para cada sessão de estudo realizada.

- [RF03] Cronometragem de Estudo: O aplicativo deve registrar a data de início e a duração total (tempo) de cada atividade.

- [RF04] Formação de Grupos: Usuários podem criar ou entrar em grupos, onde terão acesso a um feed de atividades exclusivo e um ranking de membros.

- [RF05] Sistema de Categorização: O usuário deve poder criar categorias personalizadas para organizar seus tópicos de estudo.

- [RF06] Ofensiva (Streak): O sistema deve contabilizar e exibir visualmente a sequência de dias consecutivos em que o usuário registrou ao menos uma atividade válida.

5. Requisitos Não-Funcionais

- [RNF01] Persistência de Dados: O sistema deve garantir que o tempo de estudo seja contabilizado corretamente mesmo se o aplicativo for fechado em segundo plano durante uma sessão.

- [RNF02] Performance do Feed: O ranking e as atividades dos grupos devem ser atualizados em tempo real ou com latência mínima para manter a competitividade.

6. Regras de Negócio

- [RN01] Manutenção da Streak: A "streak" (ofensiva) é reiniciada para zero caso o usuário não publique nenhuma atividade em um intervalo superior a 24 horas desde a última publicação (ou conforme a regra de dia civil).

- [RN02] Vínculo Grupo-Categoria: Ao criar um novo grupo de estudos, o sistema deve gerar automaticamente uma categoria correspondente no perfil do criador para facilitar a organização.

- [RN03] Cálculo do Ranking: O ranking dentro dos grupos será definido pelo tempo total de estudo acumulado pelos membros dentro de um período específico (ex: semanal ou mensal).

- [RN04] Validação de Atividade: Uma atividade só será considerada válida para a streak e para o ranking se contiver, no mínimo, um título e o registro do tempo decorrido.

- [RN05] Unicidade de Username: Não poderá haver dois usuários com o mesmo username (@) na plataforma.
