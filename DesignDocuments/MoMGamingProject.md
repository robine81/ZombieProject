
## MoM 30/12

Se till att sätta MVP till en enkel lösning som sen kan expanderas eftersom spel blir snabbt stort och komplext
Front-end (minimalistisk för att stötta testing under utveckling) del av MVP
Ska vi göra en hostad front-end, eller CLI -> Tomas vad har du för tankar?
Tar vi på oss för mycket med front-end? Räcker med CLI?
Typ av spel - textäventyr, traditionella Road-like, simulerade gambling/kort-spel -> funkar både med grafiskt frontend och CLI

Referens simulerade gambling/kort-spel:
https://www.youtube.com/watch?v=-nOcgQNha-0

Referens textäventyr (Roadwarden):
https://www.youtube.com/watch?v=t94rAP60c50

Lättare med simulerade gambling/kort-spel och traditionella Ro-like (mekaniskt fokuserad) än textäventyr
Textäventyr i grunden med mekaniskt (Roadwarden-typ)
Tärningsspel är liten skala, kräver inte så mycket innehåll. Var får vi in Springboot? Balatro-hållet?
Roadwarden MVP -> nod-nätverk och träd. Ett generiskt träd som kan byggas på enkelt
Nackdel Roadwarden -> microservices, hur kan vi visa det?
Ska vi ha admin-roll?
Var hostar vi? Om inte hosting, behövs inte authentication
Git-repon publik så att alla kan ladda ner och spela lokalt
Om inte hostar -> Hur gör vi med MYSQL databas? Docker container? (Kan inte utgå från att alla har MYSQL eller Docker tillgång) Använda en database.sql fil (in-memory databas). En mer utvecklad version på våra datorer för en mer sofistikerad demo.
(CLI, eller web version) använda Java 3PP library för att konvertera bild till ASCII

### Vad tycker var och en av oss är viktigt att showcase:a? Ingå i MVP:
- En enkel “happy path”
- Microservices
- Spring Boot
- En service -> JWT, authentication, user & admin
- En service -> game engine

### Java-delar att visa:
- Domänmodell + clean architecture: entity vs domain vs DTO, mapper, services.
- JUnit5 (enhetstesting)
- API-design: versionering, pagination, felkoder (Problem Details).
- Security: Spring Security + JWT, method security, roles.
- Persistence: relations, constraints, optimistic locking.
- CI/CD: GitHub Actions(Automatisera att bygga en artifact vid varje push) + Docker Compose.

#### **Följande är mina brainstorm anteckningar från min chat med AI som jag hade innan mötet och som vi sa att jag skulle dela med mig. Ska bara tas som inspirationstips**

#### MVP
- Core rules + game state + save/load + highscore.
- Front end

#### V1
- Rankings per dag/vecka, statistik per spelare.
- Fler nivåer/svårighetsgrad
- “Achievements”.

#### V2 (nice)
- Matchmaking/multiplayer async (inte realtime).
- Adminpanel (enkla endpoints).
- Replays / audit log.

#### Tekniskt: bygg “Game Engine” som egen modul/serviceklass så ni kan lägga till nya regler/spelmodi utan att röra controllers.

- Auth, JWT, player/admin roles
- Persistence (JPA): Player, Game, Move, Score.
- API (REST): start game, get state, make move, end game.
- Observability: logging, exception handling, metric

#### Sätt att dela upp i teamet
- Person A: Game engine + rules + tests.
- Person B: Data model/JPA + repository tests.
- Person C: API layer (controllers/DTO/validation) + Swagger + error handling.
- Person D: Security + CI/CD + Docker + docs.

#### Minimal beslutstråd för er brainstorming (snabb att köra i grupp)
1.	Ska vi ha UI eller bara API? (A/B)
2.	Är spelet turbaserat? (ja = bra)
3.	Kan en match beskrivas som: start -> N moves -> end? (måste)
4.	Vilken “signaturfeature” vill vi visa? (security / testcontainers / clean arch / CI)
5.	Vad är första demo-scenariot vi kan visa på 60 sek?

#### Micro-services

1) API Gateway / BFF (Spring Boot)
- Exponerar ett enda API utåt (till UI/Postman).
- Validerar request, hanterar auth (JWT), routing.
- Pratar med andra tjänster via REST (senare kan ni byta till async).

2) Game Service (Spring Boot)
- All spel-logik: start game, make move, game state, rules.
- Äger Game, Move, GameState i sin databas.

3) Score/Leaderboard Service (Spring Boot)
- Tar emot “game finished”-händelser, räknar poäng, leaderboards, statistik.
- Äger Score, LeaderboardEntry i sin databas.

Det räcker för att visa microservice-tänk: separata deployables, separata databaser, tydliga contracts.

#### Viktiga designval för att det ska “kännas som microservices”
- Varje service har egen databas.
- Ingen “delad entity” mellan services. Bara DTO/event-kontrakt.
- BFF äger inte spelregler, bara orchestration.
- En enkel “happy path” räcker.

## MoM 2/1
- Tomas -> Marknadsanalys at Cryptomarknaden med AI
- Text-äventyr
- Zombie-tema
- Olika vapen (sling-shot, sabel, gevär, fångnät, hand-klovar)
- Namnförslag: For a fistful of brains, The good, the bad and the Undead
- 

## MoM 7/1
- bestämma spelregler
- möte för use cases / seq diagram
- separat möte för att diskutera arkitektur
- separat möte för att skapa Project board (Kanban)
- skapa design document 
- CCC: controller, character(inte viktigt), camera (inte viktigt)
- vilka verb kan spelaren göra (skjuta, öppna dörr, gå, hoppa)
- begränsa verb, för att beskriva liknande meknaniker och/eller skapa generiska mekaniker som 


## MoM 8/1
- Flow chart Intro scene
- Nice to have: User Engine (MVP API Gateway)







