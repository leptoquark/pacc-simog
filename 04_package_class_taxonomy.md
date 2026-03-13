# Package and Class Taxonomy

## Classification by Architectural Concern

## Presentation Layer

Primary evidence:

- `SimogWeb/WebContent/*.jsp`
- `SimogWeb/WebContent/scheda*/*.jsp`
- `SimogWeb/WebContent/theme/*`
- `SimogWeb/WebContent/script/*`

Role:

- render server-side UI
- expose forms for procurement lifecycle data entry and consultation
- provide navigation across gara, lotto, schede, rubriche, and support functions

Representative artifacts:

- `index.jsp`
- gara and lot JSPs
- publication- and state-specific scheda JSPs

## Web Layer

Packages:

- `it.avlp.simog.servlet`
- `it.avlp.simog.auth`
- `it.avlp.simog.actions`

Representative classes:

- `ServletBase`
- `LocaleFilter`
- `AuthenticationFilter`
- `SrvAutentica`
- `SrvInserisciGara`

Role:

- request interception
- HTTP session handling
- transaction scaffolding
- dispatch to business logic

## Controller / Servlet Layer

This concern is mostly implemented through specialized servlets rather than an MVC framework controller abstraction.

Representative classes:

- `it.avlp.simog.garamanager.app.SrvInserisciGara`
- servlet classes mapped in `SimogWeb/WebContent/WEB-INF/web.xml`
- authentication servlet `it.avlp.simog.auth.manager.SrvAutentica`

Observed pattern:

- many controllers extend `ServletBase`
- operation branching often depends on request parameters such as `toDo`

## Business / Service Layer

Packages:

- `it.avlp.simog.garamanager`
- `it.avlp.simog.garamanager.lotto`
- `it.avlp.simog.rubricamanager`
- `it.avlp.simog.gestioneannullamentomanager`
- `it.avlp.simog.gestionecancellazionemanager`
- `it.anticorruzione.ted.service`

Representative classes:

- `GaraManager`
- `LottoManager`
- `DocumentoManager`
- `IniziativaManager`
- `TEDDbService`

Role:

- implement procurement logic
- coordinate validations, persistence, and downstream side effects

## Validation Layer

Packages:

- `it.avlp.simog.validatore`
- `it.anticorruzione.ted.validator`

Representative classes:

- `WorkFlowController` in `SimogCommon`
- validator classes referenced by SOAP and TED flows

Role:

- enforce field, workflow, and consistency checks
- determine allowed navigation/state transitions

## Data Access Layer

Packages:

- `it.avlp.simog.db`
- `it.avlp.simog.db.generated`
- `it.avlp.simog.common.sql`
- `it.avlp.simog.ws.commons.sql`
- `it.anticorruzione.ted.db`

Representative classes:

- `AccessiDB`
- `ConnectionWSManager`
- `WSSessionManager`
- repository implementations in `SimogTEDCommon`

Role:

- execute SQL/JDBC logic for the legacy core
- manage persistence for service-side session state
- expose JPA repository pattern in TED subsystem

## Integration Layer

Packages:

- `it.avlp.simog.ws.endpoint`
- `it.avlp.simog.common.actions`
- `it.avcp.simog.auth`
- `it.avcp.anagrafe.AnagrafeWS`
- `it.mef.serviziCUP`
- `it.avcp.avcpass`
- `ausa.servizi.avcp.it`
- `it.anticorruzione.ted.rest`

Representative classes:

- `SimogWSPDD`
- `SimogWSAGG`
- `SimogWSTED`
- `LoginActionManager`
- generated external service proxies

Role:

- expose external-facing SOAP contracts
- consume remote SOAP services
- adapt XML payloads and validation contracts

## Utility / Shared Layer

Packages:

- `it.avlp.simog.util`
- `it.avlp.simog.common`
- bundled third-party utility sources such as `com.generationjava.*`

Representative classes:

- `SimogProperties`
- utility classes supporting XML, formatting, configuration, and shared flags

Role:

- support cross-cutting behavior
- centralize runtime flags and environment properties

## Domain Model

Evidence is distributed across beans, managers, XSDs, generated DB classes, and JPA entities.

Packages:

- `it.avlp.simog.beans`
- `it.avlp.simog.db.generated`
- `it.anticorruzione.ted.db.entity`

Representative domain concepts:

- gara
- lotto
- pubblicazione
- iniziativa
- sessione WS
- TED notice/status/submit/delta

## Batch / Background Logic

Packages:

- `it.avlp.simog.massload`

Representative classes:

- `Main`
- `QueryCIG`
- `BuildListTypes`

Role:

- file-based ingestion and operational utilities

## Configuration / Bootstrap

Representative files and classes:

- root `pom.xml`
- module POMs
- `web.xml` files
- `jboss-web.xml`
- `jboss-classloading.xml`
- `persistence.xml`
- `SimogProperties`
- `ServletBase`

Role:

- define runtime container wiring
- load external configuration
- initialize logging, datasource access, and shared resources

## Security / Authentication

Packages:

- `it.avlp.simog.auth`
- `it.avlp.simog.login`
- `it.avcp.simog.auth`
- `it.avcp.sicurezza.*`
- `it.avcp.iam.service.*`

Representative classes:

- `SrvAutentica`
- `LoginManager`
- `RemoteLogin`
- `AuthenticationFilter`
- `WSSessionManager`

Role:

- user authentication
- session establishment
- service ticket validation
- request-context logging enrichment

## Reporting / Export / Document Generation

Visible evidence is indirect rather than organized as a dedicated reporting module.

Representative artifacts:

- publication managers
- document managers
- TED notice generation classes under `it.anticorruzione.ted.notice`
- mass-loader feedback generation

Inference:

Reporting/export behavior exists, but much of it is embedded in functional modules rather than isolated in a distinct reporting layer.

## Exception Management

Representative classes:

- `SimogException`
- `ActionException`
- `CigException`
- `LottoNotFoundException`
- `SimogWSException`
- `SimogWsXmlException`
- `TEDErrorException`

Role:

- encode technical and business error conditions
- support differentiated handling in web, SOAP, and TED flows

## Cross-Cutting Quality Observations

- Naming is partly consistent around procurement domains (`Gara`, `Lotto`, `Pubblicazione`) but mixed across Italian business naming and technical packages.
- Packaging is functional but not cleanly layered; UI, business logic, and persistence often interpenetrate.
- Generated code and hand-written code are mixed in the same module boundaries.
- Several manager classes are god-class candidates because they combine DB access, business rules, and orchestration.
