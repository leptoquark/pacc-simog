# Diagram Specifications

## 1. System Context Diagram

### Purpose

Show SIMOG in relation to users, external systems, database, filesystem batch exchange, and TED-related interactions.

### Main Elements

- Browser users
- External SOAP consumers
- SIMOG Web (`SimogWeb`)
- SIMOG SOAP Services (`SimogWSPDD`, `SimogWSAGG`, `SimogWSTED`)
- Shared core (`SimogCommon`, `SimogWSCommon`)
- SIMOG database
- Authentication services
- Registry/CUP/AVCPASS/AUSA services
- Batch file exchange directories

### Mermaid

```mermaid
flowchart LR
    U[Browser Users] --> WEB[SimogWeb WAR]
    X[SOAP Consumers] --> WSPDD[SimogWSPDD / WSAGG / WSTED]
    WEB --> CORE[SimogCommon]
    WSPDD --> WSCORE[SimogWSCommon]
    WSCORE --> CORE
    CORE --> DB[(SIMOG DB)]
    WSPDD --> DB
    WEB --> AUTH[Auth Services]
    WSPDD --> AUTH
    WSCORE --> EXT[Anagrafe / CUP / AVCpass / AUSA]
    BATCH[SimogMassLoader] --> DB
    BATCH --> FS[(IN/OUT/WRK/BKP)]
    WSTED[SimogWSTED] --> TED[TED Services / Notice Logic]
    TED --> DB
```

## 2. Container / Component Diagram

### Purpose

Show internal deployable units and their main dependencies.

### Main Elements

- `SimogWeb`
- `SimogWSPDD`
- `SimogWSAGG`
- `SimogWSTED`
- `SimogCommon`
- `SimogWSCommon`
- `SimogAVCPLogin`
- `SimogTEDCommon`
- `SimogMassLoader`

### PlantUML

```plantuml
@startuml
package "SIMOG" {
  [SimogWeb] --> [SimogCommon]
  [SimogWeb] --> [SimogAVCPLogin]
  [SimogWSPDD] --> [SimogWSCommon]
  [SimogWSAGG] --> [SimogWSCommon]
  [SimogWSTED] --> [SimogTEDCommon]
  [SimogWSCommon] --> [SimogCommon]
  [SimogMassLoader] --> [SimogCommon]
  [SimogTEDCommon] --> [SIMOG DB]
  [SimogCommon] --> [SIMOG DB]
}
@enduml
```

## 3. Module Dependency Diagram

### Purpose

Highlight central dependency hubs and module direction.

### Key Message

- `SimogCommon` is the dominant core dependency
- `SimogWSCommon` is the SOAP-side orchestration hub
- `SimogTEDCommon` forms a semi-bounded TED subsystem

## 4. Request Lifecycle Sequence Diagram

### Web Request Variant

```mermaid
sequenceDiagram
    participant User
    participant Filter as Filters
    participant Servlet as ServletBase-derived Servlet
    participant Manager as Business Manager
    participant DB as SIMOG DB
    participant JSP

    User->>Filter: HTTP request
    Filter->>Servlet: forwarded request
    Servlet->>Servlet: checkSession/checkService
    Servlet->>Manager: invoke action/business logic
    Manager->>DB: SQL read/write
    DB-->>Manager: results
    Manager-->>Servlet: outcome
    Servlet->>Servlet: commit/rollback
    Servlet->>JSP: forward
    JSP-->>User: rendered response
```

### SOAP Request Variant

```mermaid
sequenceDiagram
    participant Client
    participant Endpoint as SOAP Endpoint
    participant Action as WS Action Manager
    participant Session as WSSessionManager
    participant Core as SimogCommon Manager
    participant DB as SIMOG DB

    Client->>Endpoint: SOAP request
    Endpoint->>Action: validate and dispatch
    Action->>Session: validate ticket/session
    Session->>DB: WS_SESSIONS check
    Action->>Core: business operation
    Core->>DB: SQL read/write
    DB-->>Endpoint: result
    Endpoint-->>Client: SOAP response
```

## 5. Business Workflow Sequence Diagrams

### Gara Registration

```mermaid
sequenceDiagram
    participant User
    participant Srv as SrvInserisciGara
    participant GM as GaraManager
    participant DB as SIMOG DB

    User->>Srv: submit gara data
    Srv->>Srv: validate session and request
    Srv->>GM: create/update gara
    GM->>DB: persist gara
    DB-->>GM: generated data/status
    GM-->>Srv: business result
    Srv-->>User: next page / message
```

### TED Publication

```mermaid
sequenceDiagram
    participant Client
    participant TEDWS as SimogWSTED
    participant Service as TEDDbService
    participant DB as SIMOG DB

    Client->>TEDWS: pubblicaGaraTED
    TEDWS->>TEDWS: validate request/XML
    TEDWS->>Service: publication logic
    Service->>DB: read/write TED entities
    DB-->>Service: persisted status
    Service-->>TEDWS: result
    TEDWS-->>Client: TED response
```

## 6. Integration Diagram

### Purpose

Show inbound and outbound integrations grouped by function:

- authentication
- registries
- CUP
- AVCpass
- AUSA
- TED
- database
- filesystem batch

## 7. Deployment Diagram

### Main Nodes

- browser client
- external SOAP clients
- JBoss-like application server hosting multiple WARs
- shared database server
- external authentication and registry endpoints
- batch execution host or scheduled OS job host
- shared filesystem paths for mass-loader

## 8. Domain Model Diagram

### Main Concepts

- Gara
- Lotto
- Pubblicazione
- Iniziativa
- Utente
- WSSession
- TEDNotice
- TedDelta

### Suggested Relationships

- `Gara` 1..* `Lotto`
- `Gara` 0..* `Pubblicazione`
- `Lotto` 0..* `Pubblicazione`
- `WSSession` authorizes service operations on `Gara` and `Lotto`
- `Gara` and `Lotto` feed `TedDelta`
- `TedDelta` contributes to `TEDNotice`
