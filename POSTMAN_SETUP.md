# 🚀 Configuração do Postman - Sistema Hospitalar

## 📋 Pré-requisitos

1. **Postman** instalado
2. **Sistema Hospitalar** rodando em `http://localhost:8080`
3. **Swagger UI** disponível em `http://localhost:8080/swagger-ui.html`

## 🔧 Configuração

### 1. Importar Coleção

1. Abra o Postman
2. Clique em **Import**
3. Selecione o arquivo `Sistema-Hospitalar.postman_collection.json`

### 2. Importar Ambiente

1. Clique em **Import** novamente
2. Selecione o arquivo `Sistema-Hospitalar.postman_environment.json`
3. Selecione o ambiente **"Sistema Hospitalar - Ambiente Local"**

## 🔐 Autenticação

### 1. Fazer Login

1. Vá para a pasta **"🔐 Autenticação"**
2. Execute **"Login"** com as credenciais:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
3. O token JWT será automaticamente salvo na variável `jwt_token`

### 2. Usuários Disponíveis

- **admin** / admin123 (ADMIN)
- **medico** / medico123 (MEDICO)
- **enfermeiro** / enfermeiro123 (ENFERMEIRO)
- **recepcionista** / recepcionista123 (RECEPCIONISTA)

## 📁 Estrutura da Coleção

### 🔐 Autenticação

- **Login**: Fazer login e obter token JWT
- **Registrar Usuário**: Criar novo usuário (apenas ADMIN)

### 👥 Pacientes

- **Listar Todos**: Visualizar todos os pacientes
- **Buscar por ID**: Buscar paciente específico
- **Criar Paciente**: Cadastrar novo paciente
- **Atualizar Paciente**: Modificar dados do paciente
- **Contar Pacientes Ativos**: Estatísticas

### 👨‍⚕️ Profissionais

- **Listar Todos**: Visualizar profissionais
- **Criar Profissional**: Cadastrar novo profissional

**⚠️ Importante**: Para criar um profissional, use o payload correto com todos os campos obrigatórios:

```json
{
  "nome": "Dr. Carlos Santos",
  "cpf": "98765432100",
  "dataNascimento": "1985-05-20",
  "telefone": "(11) 55555-5555",
  "email": "carlos@hospital.com",
  "endereco": "Av. Paulista, 1000",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01310-100",
  "tipoProfissional": "MEDICO",
  "especialidade": "Cardiologia",
  "registroProfissional": "CRM12345",
  "dataFormacao": "2010-12-15",
  "instituicaoFormacao": "Universidade de São Paulo",
  "observacoes": "Especialista em cardiologia intervencionista"
}
```

### 🏥 Consultas

- **Listar Todas**: Visualizar consultas
- **Criar Consulta**: Agendar nova consulta
- **Confirmar Consulta**: Confirmar agendamento

### 🛏️ Leitos

- **Listar Todos**: Visualizar leitos
- **Criar Leito**: Cadastrar novo leito
- **Internar Paciente**: Internar paciente em leito
- **Dar Alta**: Liberar leito

### 🔬 Exames

- **Listar Todos**: Visualizar exames
- **Criar Exame**: Solicitar novo exame
- **Realizar Exame**: Executar exame

### 📊 Relatórios

- **Dashboard**: Visão geral do sistema
- **Estatísticas Pacientes**: Dados dos pacientes
- **Ocupação Leitos**: Status dos leitos

## 🧪 Como Testar

### 1. Fluxo Básico

1. **Login** → Obter token
2. **Criar Paciente** → Cadastrar paciente
3. **Criar Profissional** → Cadastrar médico
4. **Criar Consulta** → Agendar consulta
5. **Criar Leito** → Cadastrar leito
6. **Internar Paciente** → Internar no leito

### 2. Testes de Permissão

- Teste com diferentes usuários (admin, medico, enfermeiro)
- Verifique que cada role tem acesso apenas aos endpoints permitidos

### 3. Testes de Erro

- Tente acessar endpoints sem token
- Tente acessar endpoints com role inadequada
- Teste dados inválidos

## 🔧 Variáveis de Ambiente

| Variável          | Descrição                      | Exemplo                   |
| ----------------- | ------------------------------ | ------------------------- |
| `base_url`        | URL base da API                | `http://localhost:8080`   |
| `jwt_token`       | Token JWT de autenticação      | `eyJhbGciOiJIUzI1NiJ9...` |
| `user_id`         | ID do usuário logado           | `1`                       |
| `user_name`       | Nome do usuário logado         | `Administrador`           |
| `paciente_id`     | ID do paciente para testes     | `1`                       |
| `profissional_id` | ID do profissional para testes | `1`                       |
| `leito_id`        | ID do leito para testes        | `1`                       |
| `consulta_id`     | ID da consulta para testes     | `1`                       |
| `exame_id`        | ID do exame para testes        | `1`                       |

## 🚨 Troubleshooting

### Erro 401 (Unauthorized)

- Verifique se fez login corretamente
- Confirme se o token JWT está sendo enviado
- Verifique se o token não expirou

### Erro 403 (Forbidden)

- Verifique se o usuário tem a role necessária
- Teste com usuário admin para confirmar permissões

### Erro 404 (Not Found)

- Verifique se o ID do recurso existe
- Confirme se a URL está correta

### Erro 400 (Bad Request)

- Verifique o formato dos dados enviados
- Confirme se todos os campos obrigatórios estão preenchidos

## 📚 Recursos Adicionais

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console`
- **API Docs**: `http://localhost:8080/api-docs`

## 🎯 Dicas

1. **Sempre faça login primeiro** antes de testar outros endpoints
2. **Use o ambiente correto** para manter as variáveis organizadas
3. **Teste com diferentes roles** para verificar as permissões
4. **Monitore os logs** do Spring Boot para debug
5. **Use o Swagger UI** para explorar a API visualmente
