# Full System Architecture

## Architectural Style

### Evidence-Based Characterization

SIMOG is a modular monolith at repository level with multiple deployable web-service and web-application components. Internally it combines:

- presentation-driven JSP/Servlet web architecture
- procedural action dispatch inside servlets
- manager-centric business logic
- direct SQL/JDBC persistence for the core domain
- dedicated SOAP interoperability layers
- a side subsystem using JPA for TED-related persistence

This is a hybrid layered/procedural architecture rather than a strict clean layered architecture.

Evidence:

- `SimogWeb/WebContent/WEB-INF/web.xml`
- `SimogWeb/src/it/avlp/simog/servlet/ServletBase.java`
- `SimogCommon/src/it/avlp/simog/db/AccessiDB.java`
- `SimogWSCommon/src/it/avlp/simog/common/actions/*`
- `SimogTEDCommon/src/main/java/META-INF/persistence.xml`

### Inferred Architectural Consequences

Inferred from class responsibilities, SIMOG likely evolved incrementally around procurement workflows rather than from a single stable architectural blueprint. Large manager classes and broad utility/configuration hubs indicate accretive growth and business-rule centralization in shared code.

## Major Layers

### 1. Presentation and Web Layer

- JSP pages under `SimogWeb/WebContent`
- servlets in `it.avlp.simog.servlet` and functional subpackages
- filters for locale and request/session logging enrichment
- tag libraries under `WEB-INF/tlds`

Responsibilities:

- receive user input
- coordinate session and request validation
- call action and manager classes
- forward to JSPs or return message/error payloads

### 2. Controller and Action Layer

- functional servlets such as `SrvInserisciGara`
- action classes and helper managers under packages like `it.avlp.simog.actions`
- SOAP action managers in `it.avlp.simog.common.actions`

Responsibilities:

- interpret request operation codes
- invoke validations and business managers
- assemble response DTOs/XML structures
- manage transaction begin/commit/rollback explicitly

### 3. Business/Validation Layer

- manager classes under `it.avlp.simog.garamanager`, `lotto`, `rubricamanager`, etc.
- validator classes under `it.avlp.simog.validatore`
- workflow controllers in `SimogFlusso` and `SimogCommon`

Responsibilities:

- domain rule enforcement
- workflow gating
- procedural status checks
- orchestration of DB updates and downstream calls

### 4. Persistence Layer

- `AccessiDB` superclass and subclasses
- generated DB support classes in `it.avlp.simog.db.generated`
- WS DB helpers such as `ConnectionWSManager` and `WSSessionManager`
- JPA repositories and entities in `SimogTEDCommon`

Responsibilities:

- execute SQL queries and updates
- map result sets into business/domain objects
- enforce certain state checks close to persistence
- manage technical session/ticket state for SOAP flows

### 5. Integration Layer

- SOAP endpoints in `SimogWSPDD`, `SimogWSAGG`, `SimogWSTED`
- SOAP client proxies/stubs in `SimogAVCPLogin` and `SimogWSCommon`
- XML schema and WSDL artifacts

Responsibilities:

- expose public or partner-facing service contracts
- adapt between internal managers and SOAP payloads
- call external registries and identity systems
- validate XML-based contracts

## Module Decomposition

| Module | Packaging | Architectural Role |
| --- | --- | --- |
| `SimogParent` | `pom` | Aggregator and dependency management |
| `SimogXmlBeans` | `jar` or support artifact | XMLBeans-generated schema support |
| `SimogCommon` | `jar` | Core business logic, JDBC access, validators, utilities |
| `SimogWSCommon` | `jar` or web-support module | Shared SOAP action managers, integration helpers, WS DB/session support |
| `SimogAVCPLogin` | `jar` | Authentication integration and WSDL-derived proxies |
| `SimogFlusso` | `jar` | Workflow/state controller support |
| `SimogMassLoader` | `jar` | Batch XML ingestion and feedback generation |
| `SimogWeb` | `war` | Main JSP/Servlet web application |
| `SimogWSPDD` | `war` | SOAP services for PDD/loader/OSS flows |
| `SimogWSAGG` | `war` | SOAP services including aggregator-oriented initiative flow |
| `SimogTEDCommon` | `jar` | TED persistence, service, validation, and XML support |
| `SimogWSTED` | `war` | TED SOAP exposure |

## Main Runtime Components

### Web Runtime

- browser
- servlet container / JBoss-like application server
- `LocaleFilter`
- `AuthenticationFilter`
- individual business servlets
- `ServletBase` transaction and connection support
- JSP rendering layer

### SOAP Runtime

- JAX-WS endpoint classes in dedicated WARs
- `ConnectionWSManager` for DB connectivity
- `LoginActionManager`, `InserisciGaraActionManager`, and other WS action managers
- `WSSessionManager` for WS session lifecycle in database

### Batch Runtime

- command-line `Main` in `SimogMassLoader`
- XML validation and processing pipeline
- directory-based handoff (`IN`, `OUT`, `WRK`, `BKP`)

### TED Runtime

- SOAP endpoint `SimogWSTED`
- service layer `TEDDbService`
- JPA persistence unit `ted`
- XML/XSD validation and notice handling classes

## Coupling Patterns

### Strong Coupling Hubs

- `SimogProperties` centralizes many technical and business configuration switches
- `AccessiDB` acts as a base class for many managers and absorbs both persistence and business checks
- workflow logic is spread across validators, controllers, and DB access methods
- SOAP layers depend directly on shared action managers and DB session helpers

### Observed Consequences

- business rules and persistence are not cleanly isolated
- servlet/action code often knows transaction details
- cross-module dependency direction tends to point toward `SimogCommon` and `SimogWSCommon`
- generated artifacts and hand-written code coexist in the same logical flows

## Orchestration Patterns

### Web Orchestration

Typical orchestration pattern:

1. filter processes request
2. servlet checks session/service availability
3. servlet obtains DB connection via `ServletBase`
4. servlet dispatches by parameter such as `toDo`
5. validators and manager classes execute business logic
6. servlet commits or rolls back manually
7. request is forwarded to JSP or error/message page

### SOAP Orchestration

Typical orchestration pattern:

1. endpoint receives XML/JAXB parameters
2. endpoint validates mandatory inputs
3. endpoint obtains DB connection
4. action manager validates ticket/session
5. manager executes domain logic
6. endpoint builds response bean and technical result
7. connection is committed/rolled back and closed

## Transaction Boundaries

Transactions are mostly application-managed rather than declaratively managed.

Evidence:

- `ServletBase.commit()` and `ServletBase.rollback()`
- `ConnectionWSManager.commit()` and `rollback()`
- servlets and endpoints explicitly handling commit/rollback around operations

Inference:

Transaction scopes usually correspond to a single servlet action or SOAP operation, but some complex workflows may span multiple internal DB operations and integration calls in a single request.

## Error Handling Patterns

- explicit `try/catch/finally` blocks in servlets and endpoints
- message catalogs through `Messaggi`
- typed exceptions such as `SimogException`, `ActionException`, `SimogWSException`, `TEDErrorException`
- user-facing forwarding to error or validation pages in web flows
- SOAP technical results encoded in response payloads

## Security Mechanisms

Evidence-based mechanisms:

- login through `SrvAutentica` and `LoginManager`
- session-based web security checks in `ServletBase`
- profile and user object storage in HTTP session
- request enrichment/log correlation in `AuthenticationFilter`
- WS ticket/session validation through `WSSessionManager` and action managers

Inference:

Authorization is partly role/profile based and partly embedded in business logic checks rather than being centrally enforced by a security framework.

## Session and Authentication Model

### Web Session Model

- HTTP session stores authenticated user and related context
- `ServletBase.checkSession()` validates presence of session/user state
- locale is stored in session by `LocaleFilter`

### SOAP Session Model

- WS login methods create tickets
- database table `WS_SESSIONS` appears to store ticket/session status
- action managers validate ticket state before protected operations

## Configuration Model

Configuration is centralized but highly distributed in content:

- external property file loaded by `SimogProperties`
- container descriptors (`web.xml`, `jboss-web.xml`, `jboss-classloading.xml`)
- module version property files
- JPA `persistence.xml` for TED

The application depends heavily on deployment-time configuration not stored entirely in source control.

## Logging Model

- Log4j is used widely
- `PropertyConfigurator` is invoked from bootstrap/base classes
- MDC is used to enrich logs with session/user context in `AuthenticationFilter`
- logger names include common system loggers such as `SIMOG_LOGGER` and `MASSLOADER_LOGGER`

## Deployment Model

Direct evidence suggests multiple WAR deployments on a JBoss-like server using a shared database and shared external configuration. SOAP services are separated into dedicated WARs, while the main web UI is deployed independently.

Likely runtime components:

- `SimogWeb.war`
- `SimogWSPDD.war`
- `SimogWSAGG.war`
- `SimogWSTED.war`
- shared JAR modules on application classpath
- one or more databases accessed through `jdbc/SIMOG`
