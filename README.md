# 🕹️ CSGO Mobile App  
Aplicativo desenvolvido para a disciplina de **Desenvolvimento Mobile**, utilizando **Kotlin + XML**, consumindo dados de uma **API externa de CSGO**.

---

## ▶️ Demonstração do Aplicativo
![Demonstração do App](./assets/AppCSGO.gif)

---

## 🚀 Funcionalidades Principais

### 🔽 Barra de Navegação Inferior (Bottom Navbar)
A aplicação possui uma **Navbar com 6 opções**:
- **Home**
- **Skins**
- **Highlights**
- **Crates**
- **Stickers**
- **Agents**

### 🏠 Home & Telas Internas
A página inicial (Home) apresenta:
- Cards e conteúdos variados do CS
- Acesso rápido para seções principais
- Listagens consumidas diretamente da API
- Layout adaptado via XML

### 🔎 Área de Pesquisa
- Permite buscar skins, agentes, caixas, etc.
- Resultados carregados dinamicamente

### 🎁 OPEN CRATE (Loot Box Animation)
- Animação similar à abertura de loot boxes do CS:GO.  
- Exibe um item aleatório ao final (skin, sticker, crate, etc.).  
- Construída utilizando XML + lógica Kotlin.

---

## 🛠️ Tecnologias Utilizadas

### ✔️ Linguagem & Interface
- Kotlin  
- XML Layouts  
- ViewBinding  

### ✔️ Arquitetura
- MVVM  

### ✔️ Outros
- Navigation Component  
- RecyclerView  
- Vector Drawables  

---

## 🧩 Estrutura do Projeto 

```bash
app/
├── data/
│ ├── model/
│ ├── repository/
│ └── api/
│
├── ui/
│ ├── home/
│ ├── skins/
│ ├── highlights/
│ ├── crates/
│ ├── stickers/
│ └── agents/

```

---

## ▶️ Como Rodar o Projeto

### **1. Pré-requisitos**

Certifique-se de que possui:

- **Android Studio** (versão atual recomendada)  
- **JDK 17+**  
- **Gradle** configurado automaticamente pelo Android Studio  
- **Emulador Android** ou **dispositivo físico**

---

### **2. Clonando o Repositório**

```sh
git clone https://github.com/Patrick-1810/app-csgo.git
cd app-csgo

```

---

### **3. Abrindo no Android Studio**

- Abra o Android Studio

- Vá em File > Open

- Selecione a pasta do projeto

- Aguarde o Gradle sincronizar

--- 

### **4. Configuração da API**

- Vá até o arquivo:

```sh
app/src/main/java/.../api/RetrofitClient

```
- Insira a URL da API de CSGO/CS2 utilizada no projeto.
- A URL é: https://raw.githubusercontent.com/ByMykel/CSGO-API/main/public/api/en/

---

### **5. Rodando o Aplicativo**
- Escolha um emulador ou conecte o celular via USB

- Clique em Run ▶

- Aguarde o build completar

---

### **6. 👥 Autores**
- [Leonardo Cogo](https://github.com/leonardocogo)
- [Nicolle Poltosi](https://github.com/NicolleMP)
- [Patrick Prestes](https://github.com/Patrick-1810)
