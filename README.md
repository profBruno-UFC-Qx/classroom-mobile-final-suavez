# 1. Objetivo Geral:
Desenvolver um sistema de fila virtual para postos de saúde que permita o gerenciamento digital do fluxo de atendimento, possibilitando que os pacientes acompanhem sua posição na fila em tempo real, sem a necessidade de longos períodos de espera presencial.

# 2. Público-Alvo:
Pacientes: indivíduos que necessitam de atendimento e desejam evitar longos períodos de espera presencial.
Funcionários dos postos de saúde: profissionais responsáveis pela organização e condução dos atendimentos que utilizarão o sistema para gerenciar as filas e acompanhar as presenças dos pacientes.

# 3. Impacto Esperado:
O sistema pretende reduzir o tempo de espera dos pacientes nos postos de saúde, permitindo que aguardem pelo atendimento de forma remota e cheguem ao posto somente perto do horário de serem atendidos.  Além disso, espera-se melhorar a organização interna dos postos de saúde por meio do controle digital da fila, permitindo o acompanhamento em tempo real dos pacientes e a redução de faltas sem aviso prévio. Isso proporciona um ganho tanto na eficiência operacional dos funcionários quanto na qualidade do serviço prestado à população.

# 4. Requisitos Funcionais:
- [RF01] Login Simplificado: O aplicativo deve permitir a autenticação do paciente utilizando exclusivamente o número do CPF.

- [RF02] Cancelamento Seguro: O paciente pode cancelar seu agendamento ou lugar na fila a qualquer momento.

- [RF03] Realocação de Posição: O paciente deve ter a opção de ceder sua vez e ser realocado na fila, caso perceba que não chegará a tempo no posto.

- [RF04] Notificações Push: O aplicativo deve enviar alertas de sistema para o celular do paciente informando o status da fila.

- [RF05] Exibição de Tempo Estimado: A interface deve mostrar uma estimativa de tempo restante até o atendimento.

- [RF06] Check-in de Presença: O paciente deve confirmar sua presença pelo aplicativo e a validação é feita por meio de geolocalização (RNF01).

# 5. Requisitos Não-Funcionais:
- [RNF01] Geolocalização: O aplicativo deve utilizar o GPS do dispositivo móvel para validar a presença do paciente no posto de saúde (RF06).

# 6. Regras de Negócio:
- [RN01] Validação de CPF: O sistema deve validar a integridade do CPF (dígito verificador) no momento do login para evitar entradas inválidas.

- [RN02] Autenticação Dupla para Cancelamento: Para efetivar o cancelamento, o aplicativo deve exigir a inserção do CNS (Cartão Nacional de Saúde) associado ao CPF, garantindo que terceiros não cancelem o atendimento indevidamente.

- [RN03] Limite de Realocação: O sistema permitirá apenas 1 (uma) realocação por agendamento. Ao ser utilizada, a função será bloqueada para aquele atendimento.

- [RN04] Regra de Posição: A realocação joga o paciente para a última posição atual da sua categoria, e a ação deve ser validada utilizando o CNS.

- [RN05] Gatilhos de Notificação: O disparo deve ocorrer em momentos-chave, como: "Sua vez está próxima (faltam 3 pessoas)" e "Você foi chamado para o consultório 2".

- [RN06] Raio de Confirmação: A confirmação definitiva de presença só poderá ser feita (habilitando o botão no app) se o paciente estiver dentro de um raio geográfico pré-definido (ex: 150 metros) das coordenadas do posto de saúde.

- [RN07] Cálculo Dinâmico: A previsão não deve ser um valor fixo. Ela deve ser calculada multiplicando a posição atual do paciente pelo tempo médio de duração dos últimos atendimentos daquela categoria específica.
