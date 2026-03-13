# Deep-Dive Architecture Document

## Purpose

This document provides a focused architectural deep dive on six areas:

- runtime flows
- module dependencies
- integration design
- legacy architectural constraints
- maintainability risks
- modernization opportunities

It is intended as a more analytical companion to the broader architecture material in `02_system_architecture.md`.

## 1. Architectural Core

### 1.1 High-Level Characterization

SIMOG is a legacy multi-module enterprise system built as a Maven reactor with several deployable WARs and a shared core of JAR modules. At runtime, it behaves like a modular monolith with multiple entry channels:

- browser-driven web UI in `SimogWeb`
- SOAP service exposure in `SimogWSPDD`, `SimogWSAGG`, and `SimogWSTED`
- command-line batch processing in `SimogMassLoader`
- outbound integrations to authentication and public-sector registry services

The repository structure suggests modular decomposition, but the source code shows strong concentration of business and persistence logic in a small number of shared modules and classes.

Primary evidence:

- `pom.xml`
- `SimogWeb/WebContent/WEB-INF/web.xml`
- `SimogWSPDD/WebContent/WEB-INF/web.xml`
- `SimogWSAGG/WebContent/WEB-INF/web.xml`
- `SimogWSTED/WebContent/WEB-INF/web.xml`
- `SimogCommon/src/it/avlp/simog/db/AccessiDB.java`

### 1.2 Architectural Shape

The architecture is best described as a hybrid of:

- layered web application
- procedural service orchestration
- DB-centric business implementation
- SOAP interoperability platform

It is not a framework-centered enterprise architecture with strong dependency inversion. Instead, most important flows converge on:

- `ServletBase` for web-request runtime scaffolding
- `AccessiDB` and large manager classes for persistence and business behavior
- `ConnectionWSManager` and WS action managers for SOAP runtime
- `SimogProperties` for cross-cutting environment configuration

## 2. Runtime Flows

## 2.1 Web Runtime Flow

### 2.1.1 Main Path

The main browser runtime is declared in `SimogWeb/WebContent/WEB-INF/web.xml`. Requests enter through servlet mappings such as:

- `/checkAuthentication`
- `/inizializzaGara`
- `/InserisciGara`
- `/InserisciLotto`
- `/gestisciLotto`
- `/elencoCig`
- `/bandoGara`

Before business handling, two filters are applied:

- `it.avlp.simog.servlet.LocaleFilter`
- `it.avlp.simog.servlet.AuthenticationFilter`

`LocaleFilter` enforces UTF-8 and manages session locale state. `AuthenticationFilter` currently enriches MDC log context with session and user identifiers. The class also contains commented IAM-related logic, which indicates that this filter was once intended to carry stronger security responsibilities.

### 2.1.2 Controller Execution Model

Most functional servlets rely on `ServletBase`, which centralizes:

- logger initialization
- configuration loading through `SimogProperties`
- JDBC driver and datasource access
- connection/session tracking
- service/session validity checks
- commit and rollback handling
- JSP forwarding and error/validation response helpers

This makes `ServletBase` a hidden runtime framework for the web application.

Evidence:

- `SimogWeb/src/it/avlp/simog/servlet/ServletBase.java`

### 2.1.3 Business Execution Path

A representative flow is `SrvInserisciGara`:

1. request reaches servlet mapping
2. servlet checks session and service availability
3. servlet obtains DB connection from `ServletBase`
4. servlet inspects request parameter such as `toDo`
5. servlet invokes action and manager logic
6. manager classes execute SQL and business checks
7. servlet commits or rolls back
8. request is forwarded to JSP or validation/error output

This pattern indicates that transaction boundaries are not hidden in service classes; they are visible in controllers.

### 2.1.4 Runtime Consequences

- web requests are stateful and session-centered
- navigation logic is server-side
- business validation is split across controller, validator, and persistence helper layers
- failure handling depends on explicit try/catch/finally logic rather than a uniform interceptor model

## 2.2 SOAP Runtime Flow

### 2.2.1 Entry Points

SOAP entry points are exposed by dedicated WARs:

- `SimogWSPDD`
- `SimogWSAGG`
- `SimogWSTED`

Representative mappings:

- `/services/SimogWSPDD`
- `/services/LoaderAppaltoWS`
- `/services/SimogWSOSS`
- `/services/LoaderAppaltoAVCPASS`
- `/services/SimogWSAGG`
- `/services/SimogWSTED`

### 2.2.2 Generic SOAP Execution Pattern

For the procurement-oriented services, the main pattern is:

1. endpoint receives typed SOAP request
2. endpoint validates required parameters
3. endpoint obtains DB connection via `ConnectionWSManager`
4. shared WS action manager validates ticket/session
5. shared action manager invokes core business managers
6. endpoint assembles response bean and technical result
7. connection is committed/rolled back and closed

Important evidence:

- `SimogWSPDD/src/it/avlp/simog/ws/endpoint/SimogWSPDD.java`
- `SimogWSCommon/src/it/avlp/simog/common/actions/LoginActionManager.java`
- `SimogWSCommon/src/it/avlp/simog/common/actions/InserisciGaraActionManager.java`
- `SimogWSCommon/src/it/avlp/simog/common/sql/ConnectionWSManager.java`
- `SimogWSCommon/src/it/avlp/simog/ws/commons/sql/WSSessionManager.java`

### 2.2.3 Session and Ticket Design

SOAP consumers do not appear to rely solely on transport-level security. Instead, the code implements a dedicated application session model:

- login operation creates a ticket
- ticket/session state is stored in `WS_SESSIONS`
- statuses such as `S`, `I`, `P`, `E` are managed in DB
- protected operations validate session/ticket before allowing business execution

This is a clear architectural subsystem in its own right.

### 2.2.4 Specialized SOAP Flows

`SimogWSAGG` handles initiative communication. Its `comunicaIniziativa` path shows a specialized service facade pattern: validate, connect, invoke `IniziativaManager`, handle related territory/category records, and commit.

`SimogWSTED` handles a more bounded set of TED operations:

- delta extraction
- cancellation of delta requests
- consultation
- publication
- verification
- correction and award notice submission

Compared with the generic procurement SOAP flows, TED appears more subsystem-oriented and less sprawling.

## 2.3 Authentication Runtime Flow

### 2.3.1 Web Authentication

The main web authentication entry point is:

- `/checkAuthentication` mapped to `it.avlp.simog.auth.manager.SrvAutentica`

The flow is:

1. request arrives with credentials or SAML-related data
2. `SrvAutentica` calls `LoginManager`
3. `LoginManager` chooses local, remote, or IAA login implementation
4. returned XML or authentication payload is parsed
5. application user object and profile context are created
6. session is populated
7. navigation continues into protected application flows

### 2.3.2 Legacy Auth Sediment

`RemoteLogin` contains code paths for more than one remote authentication implementation. Comments suggest old login remains active and newer IAM-oriented work was not fully completed or retained. This is an architectural sign of migration history embedded in the codebase.

## 2.4 Batch Runtime Flow

The batch runtime is centered on `SimogMassLoader`.

Main flow:

1. command-line startup in `Main`
2. validation of directories such as `IN`, `OUT`, `WRK`, `BKP`
3. load/validate configuration
4. discover input XML files
5. validate against schemas
6. run business processing
7. produce feedback files and archive/move processed data

This channel is important because it bypasses the browser and SOAP entry points while still reusing core business logic.

## 2.5 Hidden Runtime Architecture

The most important hidden runtime pattern is that workflow and state control are not externalized to a separate engine. Instead, they are scattered across:

- `it.avlp.simog.validatore.WorkFlowController`
- `it.avlp.simog.flusso.WorkFlowController`
- `AccessiDB`
- domain managers such as `GaraManager` and `LottoManager`
- JSP/scheda-specific navigation logic

This makes runtime behavior highly code-centric and difficult to understand from deployment descriptors alone.

## 3. Module Dependencies

## 3.1 Dependency Structure

The practical dependency graph is not a simple layered stack. It is hub-and-spoke around a few modules.

### Core Dependency Hubs

- `SimogCommon`
- `SimogWSCommon`
- `SimogAVCPLogin`
- `SimogTEDCommon` for the TED branch

### Main Direction

- `SimogWeb` depends on `SimogCommon` and `SimogAVCPLogin`
- `SimogWSPDD` and `SimogWSAGG` depend on `SimogWSCommon`, which in turn depends heavily on `SimogCommon`
- `SimogMassLoader` depends on `SimogCommon`
- `SimogWSTED` depends on `SimogTEDCommon`, and likely on some shared SIMOG classes where needed

## 3.2 Module Roles

### `SimogCommon`

This is the real application core. It contains:

- DB access scaffolding
- business managers
- validators
- utilities
- shared domain-oriented classes

Its architectural importance is higher than its name alone suggests.

### `SimogWSCommon`

This module acts as the integration-control layer between external service contracts and internal procurement logic. It centralizes:

- WS ticket/session handling
- shared SOAP action orchestration
- common DB connection management for service calls
- many external service stubs

### `SimogAVCPLogin`

This is an infrastructural module for authentication interoperability. It is a separate concern logically, but it is tightly woven into the main runtime because session establishment is not isolated behind a thin interface.

### `SimogTEDCommon`

This is a relatively self-contained subsystem. It has its own persistence model, entity set, and TED-specific service layer. In dependency terms, it is the clearest candidate for bounded-context treatment.

## 3.3 Dependency Anti-Patterns

### Shared Core Overload

`SimogCommon` carries too many responsibilities:

- configuration
- SQL
- domain behavior
- workflow checks
- support utilities

This creates change amplification. A modification in the core may affect web flows, SOAP flows, and batch flows simultaneously.

### Infrastructure Leakage

Transaction handling, connection management, and sometimes configuration concerns are visible in upper layers such as servlets and endpoint classes. This indicates that infrastructure abstractions are incomplete.

### Generated-Code Adjacency

Generated SOAP/XML code and handwritten business logic coexist in close proximity, which increases cognitive load and complicates refactoring.

## 4. Integration Design

## 4.1 Integration Style

SIMOG is integration-heavy and XML-centric. The design relies on:

- SOAP inbound service exposure
- SOAP outbound service consumption
- XML/XSD validation
- DB integration as shared backbone
- file-based import/export for batch scenarios

## 4.2 Inbound Integration Design

The inbound design is organized by separate deployable SOAP WARs rather than one generic service gateway. This has some benefits:

- clearer endpoint ownership per service family
- independent deployment packaging
- visible separation between procurement operations and TED operations

But the internal orchestration remains coupled because all major inbound channels converge on shared business managers or shared DB access code.

## 4.3 Outbound Integration Design

Outbound integrations are implemented through generated client stubs and direct invocation from login or WS-related code. Families visible in the repository include:

- authentication services
- Anagrafe services
- CUP services
- AVCpass-related services
- AUSA-related services

The design pattern is adapter-like in practice but not consistently encapsulated. Some client packages are clearly generated; others are mixed with orchestration code.

## 4.4 Integration Contract Design

The repository contains substantial contract material:

- WSDLs in `SimogAVCPLogin/wsdl`
- WSDL snapshots in `SimogWSPDD/wsdl_from_browser`
- shared domain XSDs under root `XSD`
- TED XSDs under `SimogWSTED/src/XSD`

This suggests that contract-first or contract-bound development has been important operationally, even if the implementation style is strongly imperative.

## 4.5 Integration Session Design

The SOAP session/ticket model deserves special attention because it is a deliberate architectural feature, not an incidental detail.

It provides:

- logical session continuity for external clients
- DB-persisted state
- authorization gating for business operations
- operation outcome/status tracking

This design is serviceable for legacy SOAP ecosystems, but it also creates additional DB dependency and state-management complexity.

## 4.6 Integration Design Strengths

- broad interoperability surface
- explicit contract artifacts
- dedicated endpoint modules
- reusable WS action logic in `SimogWSCommon`

## 4.7 Integration Design Weaknesses

- multiple generations of SOAP technology coexist
- configuration is external and fragmented
- failure-handling patterns vary by integration
- generated artifacts are tightly coupled to implementation modules
- semantic understanding of some contracts depends on business history not visible in code

## 5. Legacy Architectural Constraints

## 5.1 Technology Baseline Constraints

The parent build indicates a Java 6-era baseline. Combined with JSP/Servlet UI, Axis/JAX-RPC artifacts, XMLBeans-era support, and JBoss descriptors, this places SIMOG in a constrained modernization posture.

Consequences:

- limited access to modern Java/platform features without broad upgrade effort
- likely dependency compatibility constraints
- elevated operational dependence on legacy container behavior

## 5.2 Persistence Constraints

The core domain is implemented through direct JDBC and SQL-heavy managers. Business behavior is tied to persistence in classes such as `AccessiDB` and `GaraManager`.

Consequences:

- hard to change schema-related behavior safely
- difficult to isolate and test business logic
- strong coupling to current table structure and query conventions

## 5.3 Workflow Constraints

Workflow/state rules are not modeled as an explicit domain state machine or workflow engine. They are encoded procedurally in validators and DB helpers.

Consequences:

- state semantics are hard to discover
- adding a new procedural phase is high-risk
- validation and state transition logic may drift across modules

## 5.4 Configuration Constraints

`SimogProperties` centralizes many unrelated concerns:

- DB parameters
- authentication endpoints
- file paths
- service toggles
- legal/regulatory behavior switches
- integration flags

Consequences:

- configuration drift can alter behavior significantly
- environment reconstruction is difficult
- operational knowledge is concentrated outside the repository

## 5.5 Operational Constraints

- WAR deployment into a JBoss-like container
- dependence on JNDI datasource provisioning
- shared DB as central dependency
- filesystem requirements for config and batch
- strong reliance on external services for full system function

## 6. Maintainability Risks

## 6.1 Primary Risk Areas

### Risk 1: `AccessiDB` as Architectural Bottleneck

`AccessiDB` is not just a DB helper. It accumulates:

- low-level persistence operations
- workflow checks
- business preconditions
- utility-like behavior used by many managers

This makes it both fragile and strategically important. It is the single clearest maintainability hotspot.

### Risk 2: Large Manager Classes

Classes such as `GaraManager` and `LottoManager` appear to function as god classes. They encode large slices of the procurement lifecycle with broad DB knowledge and low abstraction.

Impact:

- difficult code review
- hard to isolate change impact
- limited testability

### Risk 3: Runtime Logic Spread Across Layers

A single business change may require coordinated modification in:

- JSP navigation
- servlet dispatch logic
- validators
- managers
- SQL helpers
- SOAP action managers

This is a classic symptom of weakly enforced architectural boundaries.

### Risk 4: Authentication Migration Sediment

Multiple authentication branches and commented logic imply that security architecture evolved without full cleanup. This increases the chance of:

- dormant code paths
- ambiguous production behavior
- hard-to-audit security assumptions

### Risk 5: Externalized Configuration Complexity

The true runtime behavior depends on configuration values not fully stored in source control. Engineers can understand the code but still fail to reproduce the environment.

### Risk 6: Sparse Automated Tests

The repository shows minimal formal automated test coverage. This amplifies every other risk because behavior is difficult to lock down before refactoring.

## 6.2 Secondary Risk Areas

- generated code mixed with handwritten code
- database-backed WS session state as an additional failure mode
- mixed persistence styles between core and TED
- multilingual UI/messages without obvious centralized documentation

## 7. Modernization Opportunities

## 7.1 Architectural Modernization Strategy

The safest modernization approach is incremental and characterization-first. SIMOG contains high-value domain knowledge; replacing it wholesale would create major functional and institutional risk.

## 7.2 Short-Term Opportunities

### A. Stabilize Understanding

- produce dependency maps for `SimogCommon` and `SimogWSCommon`
- document top 20 critical runtime flows with class-level call chains
- externalize configuration catalog from `SimogProperties` into maintainable documentation

### B. Reduce Operational Ambiguity

- document active authentication path and retire dead branches after confirmation
- standardize logging correlation across web, SOAP, and batch
- add environment validation startup checks for missing critical configuration

### C. Protect Behavior

- create characterization tests around:
  - login
  - gara insertion
  - lot update/perfection
  - WS ticket lifecycle
  - TED publication
  - mass-loader processing

## 7.3 Medium-Term Opportunities

### A. Extract Application Services

Introduce explicit service facades between controllers/endpoints and the largest manager classes. This would reduce direct exposure of:

- transaction details
- SQL-oriented helper methods
- persistence-specific concerns

### B. Isolate Workflow Logic

Move state and phase transition rules into dedicated workflow services or rule objects. Even before technology replacement, this would improve readability and testability.

### C. Encapsulate Outbound Integrations

Place generated external-service clients behind stable adapter interfaces. This would reduce coupling to WSDL-generated types and simplify later replacement.

### D. Rationalize Configuration

Split configuration by concern:

- database
- authentication
- interoperability
- TED
- filesystem/batch
- feature toggles

## 7.4 Long-Term Opportunities

### A. Recompose the Core Domain

The strongest long-term architectural target is to separate:

- procurement core domain services
- interoperability adapters
- presentation concerns
- persistence implementations

This does not require immediate microservice decomposition. A well-structured modular monolith would already be a major improvement.

### B. Consolidate Security Model

Replace overlapping authentication branches with a single documented integration approach and explicit authorization model.

### C. Modernize Persistence Boundary

For the core domain, the goal should be better persistence isolation, not immediate ORM conversion everywhere. The current risk is in coupling, not in JDBC alone.

### D. Use TED as a Reference Subsystem

`SimogTEDCommon` is not modern by current standards, but it is more explicit in its persistence model. It can serve as a pattern source for introducing clearer repository/service boundaries into other parts of the system.

## 7.5 What Should Remain Stable

- external SOAP contracts unless versioned intentionally
- critical procurement identifiers and state semantics
- batch contract expectations if external partners rely on them
- legal/regulatory toggles until their business ownership is fully documented

## 8. Recommended Reverse-Engineering Priorities

If this deep dive is followed by engineering action, the highest-value next steps are:

1. map class-level dependencies around `AccessiDB`, `GaraManager`, and `LottoManager`
2. map the exact login/runtime security path in production-relevant code
3. reconstruct the `WS_SESSIONS` lifecycle and downstream service permissions
4. build a table-level data model from SQL strings in `SimogCommon`
5. extract one representative end-to-end flow for each channel:
   web, SOAP, TED, and batch

## 9. Final Architectural Assessment

SIMOG is a legacy, DB-centric, workflow-heavy enterprise system whose true architecture is encoded more in classes and runtime conventions than in clean interfaces or framework configuration. Its strongest qualities are functional breadth, operational interoperability, and the amount of procurement process knowledge embedded in the code. Its weakest architectural properties are concentration of responsibilities in shared core classes, manual transaction orchestration, implicit workflow logic, and environment dependence on external configuration and legacy integration assumptions.

From a modernization perspective, the correct approach is not replacement by fashion. The correct approach is controlled extraction of architectural seams: service boundaries, workflow boundaries, integration adapters, configuration domains, and test-backed behavior contracts.
