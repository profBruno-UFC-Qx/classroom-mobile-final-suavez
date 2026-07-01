# 1. Objetivo Geral

Desenvolver uma plataforma de gamificação de estudos voltada a fomentar constância e competitividade saudável por meio de grupos de estudo, métricas de produtividade e registros visuais de atividades.

# 2. Público-Alvo

Estudantes Autônomos: Indivíduos que buscam motivação extra e ferramentas para rastrear seu progresso diário.

Grupos de Estudo e Coletivos Acadêmicos: Pessoas que desejam compartilhar rotinas, comparar desempenhos e manter um ambiente de responsabilidade mútua (accountability).

# 3. Impacto Esperado

O sistema visa aumentar a retenção dos usuários em seus cronogramas de estudo por meio do gatilho psicológico da "streak" (ofensiva) e da validação social. Espera-se que a funcionalidade de grupos e rankings transforme o estudo em uma experiência coletiva, aumentando o senso de realização pessoal através do registro visual (fotos de resumos, livros ou telas).

# 4. Requisitos Funcionais

- [RF01] Gerenciamento de Perfil: O usuário deve ser capaz de criar uma conta, definir um username (@) e visualizar suas estatísticas gerais (tempo total, quantidade de atividades e streak).

- [RF02] Registro de Atividade: O sistema deve permitir o upload de uma foto, título, descrição e seleção de categorias para cada sessão de estudo realizada.

- [RF03] Cronometragem de Estudo: O aplicativo deve registrar a data de início e a duração total (tempo) de cada atividade.

- [RF04] Formação de Grupos: Usuários podem criar ou entrar em grupos, onde terão acesso a um feed de atividades exclusivo e um ranking de membros.

- [RF05] Sistema de Categorização: O usuário deve poder criar categorias personalizadas para organizar seus tópicos de estudo.

- [RF06] Ofensiva (Streak): O sistema deve contabilizar e exibir visualmente a sequência de dias consecutivos em que o usuário registrou ao menos uma atividade válida.

# 5. Requisitos Não-Funcionais

- [RNF01] Persistência de Dados: O sistema deve garantir que o tempo de estudo seja contabilizado corretamente mesmo se o aplicativo for fechado em segundo plano durante uma sessão.

- [RNF02] Performance do Feed: O ranking e as atividades dos grupos devem ser atualizados em tempo real ou com latência mínima para manter a competitividade.

# 6. Regras de Negócio

- [RN01] Manutenção da Streak: A "streak" (ofensiva) é reiniciada para zero caso o usuário não publique nenhuma atividade em um intervalo superior a 24 horas desde a última publicação (ou conforme a regra de dia civil).

- [RN02] Vínculo Grupo-Categoria: Ao criar um novo grupo de estudos, o sistema deve gerar automaticamente uma categoria correspondente no perfil do criador para facilitar a organização.

- [RN03] Cálculo do Ranking: O ranking dentro dos grupos será definido pelo tempo total de estudo acumulado pelos membros dentro de um período específico (ex: semanal ou mensal).

- [RN04] Validação de Atividade: Uma atividade só será considerada válida para a streak e para o ranking se contiver, no mínimo, um título e o registro do tempo decorrido.

- [RN05] Unicidade de Username: Não poderá haver dois usuários com o mesmo username (@) na plataforma.

# 7. Estado Atual da Implementação

O projeto conta com um app Android (client) e um backend (server) já funcionais e integrados.

## 7.1 Stack Tecnológica

**Client** (`client/ProjectStudy`): Kotlin, Jetpack Compose, Navigation3, Hilt (DI), Room (persistência local), Ktor Client (HTTP), Coil (imagens).

**Server** (`server`): FastAPI, SQLAlchemy, SQLite, autenticação JWT (PyJWT + passlib/bcrypt), uvicorn. Roda localmente (sem deploy em nuvem no momento).

## 7.2 Arquitetura e Sincronização

O app funciona com Room como fonte da verdade local e sincroniza com o backend via `RemoteSyncService`:

- **Push:** atividades pendentes criadas offline são enviadas ao servidor (`POST /sync/activity`).
- **Pull:** `GET /sync/pull` traz grupos, atividades e ranking atualizados, que são gravados no Room e propagados à UI via `Flow`.
- Login e pull-to-refresh (Feed/Perfil) disparam esse fluxo de sincronização.

Upload de imagens de sessão: URI de conteúdo local → salva localmente (`LocalMediaStorage`) → enviada para `POST /media/activity-image` → servidor retorna URL pública servida em `/uploads` → atividade sincronizada com essa URL.

## 7.3 Funcionalidades Implementadas

- Cadastro e login de usuário com JWT (`/auth/register`, `/auth/login`, `/auth/me`).
- Perfil com estatísticas (tempo total, quantidade de atividades, streak) calculadas a partir das atividades locais.
- Registro de sessão de estudo manual (título, descrição, categoria, foto, tempo).
- Grupos: criação, listagem, entrada por código de convite (`/group`, `/group/{id}`, `/group/join`), com autorização por membership.
- Feed de atividades do grupo e ranking de membros.
- Sincronização bidirecional (push/pull) e pull-to-refresh no Feed e no Perfil.
- Persistência local das imagens de atividade (sobrevive a reinício do app).

## 7.4 Como Rodar Localmente

**Servidor:**
```bash
cd server
cp .env.example .env   # ajuste SECRET_KEY
uv sync
uv run uvicorn main:app --reload --host 0.0.0.0
```

**Client:** abra `client/ProjectStudy` no Android Studio. Ajuste a URL base em `app/src/main/java/com/example/projectstudy/di/NetworkModule.kt` para o IP local da máquina rodando o servidor (`hostname -I` / `ip addr`), já que o backend não está publicado em nuvem.
