# Request / Response Lifecycle

## Overview

SIMOG supports at least four distinct runtime lifecycles:

- web UI lifecycle
- SOAP service lifecycle
- authentication lifecycle
- batch lifecycle

Each lifecycle is described below using code evidence.

## Web UI Lifecycle

## Variant A: Standard Browser Request

### 1. Entry Point

Request enters the `SimogWeb` WAR through servlet mappings defined in `SimogWeb/WebContent/WEB-INF/web.xml`.

Examples:

- `/inizializzaGara`
- `/InserisciGara`
- `/InserisciLotto`
- `/gestisciLotto`
- `/bandoGara`

### 2. Filtering / Interception

Filters declared in `web.xml` run first:

- `LocaleFilter`
- `AuthenticationFilter`

Observed effects:

- UTF-8 encoding is enforced
- locale is selected and stored in session
- MDC logging context is enriched with session/user information

### 3. Controller Dispatch

The mapped servlet receives the request, usually extending `ServletBase`.

Evidence:

- `ServletBase`
- `SrvInserisciGara`

Typical controller responsibilities:

- check session and service availability
- retrieve or create DB connection
- decode request parameters
- branch by operation code

### 4. Validation

Validation occurs in one or more of the following places:

- servlet-level parameter checks
- validator classes
- manager precondition checks
- workflow/state checks inside `AccessiDB` or `WorkFlowController`

Inference:

Validation is distributed rather than centralized.

### 5. Business Logic Invocation

Controller calls action/manager classes such as `GaraLottoAction`, `GaraManager`, or related specialized managers.

Example evidence:

- `SrvInserisciGara` delegates to business classes depending on `toDo`

### 6. Persistence / DB Access

Managers use JDBC through `AccessiDB` inheritance and direct SQL execution.

Typical pattern:

- servlet gets connection
- manager executes SQL
- servlet decides commit or rollback

### 7. Response Composition

If successful, servlet:

- sets request/session attributes
- forwards to JSP
- or sends a message/validation result via helper methods in `ServletBase`

If unsuccessful:

- catches exception
- rolls back transaction
- forwards to error page or writes technical error

### 8. View Rendering

JSP renders next page or result view using request/session state populated by controller and business layers.

## Variant B: Authentication Flow

### Entry Point

`/checkAuthentication` mapped to `it.avlp.simog.auth.manager.SrvAutentica`

### Lifecycle

1. request contains credentials or SAML-related data
2. `SrvAutentica` invokes `LoginManager`
3. `LoginManager` selects local, remote, or IAA login path
4. login XML/result is parsed through `XmlManager`
5. `Utente` and profile-related data are built and enriched
6. HTTP session is populated
7. user is redirected/forwarded to authorized next page

Evidence:

- `SrvAutentica`
- `LoginManager`
- `RemoteLogin`

## SOAP Service Lifecycle

## Variant A: PDD/Procurement Service Operation

### 1. Entry Point

Request enters a SOAP WAR through endpoint mapping, for example:

- `/services/SimogWSPDD`
- `/services/LoaderAppaltoWS`
- `/services/SimogWSOSS`

### 2. Endpoint Method Invocation

JAX-WS endpoint class receives the SOAP payload.

Example:

- `SimogWSPDD.inserisciGara(...)`
- `SimogWSPDD.login(...)`

### 3. Parameter and Session Validation

Endpoint validates mandatory inputs and delegates ticket/session checks to shared action managers and `WSSessionManager`.

Pattern:

- login methods create or update session/ticket state
- business operations verify valid ticket before proceeding

### 4. Business Execution

Shared action manager performs:

- XML/JAXB unmarshalling or object adaptation
- collaboration or credential checks
- domain manager invocation
- DB writes/reads

Evidence:

- `LoginActionManager`
- `InserisciGaraActionManager`

### 5. Transaction Handling

`ConnectionWSManager` manages DB transaction boundaries explicitly.

### 6. SOAP Response Generation

Endpoint builds response bean with:

- business output data
- technical result code/message
- possible validation or error information

## Variant B: TED SOAP Flow

### Entry Point

`/services/SimogWSTED`

### Runtime Pattern

1. endpoint receives TED operation request
2. XML/XSD validation and TED-specific checks run
3. service layer or TED action classes manipulate deltas, notices, or publication status
4. JPA-backed persistence and supporting managers are invoked
5. endpoint returns a TED-specific technical/business response

Evidence:

- `SimogWSTED`
- `TEDDbService`

## Batch Lifecycle

### Entry Point

Command-line execution of `it.avlp.simog.massload.Main`

### Runtime Pattern

1. read configuration and runtime parameters
2. verify required directories (`IN`, `OUT`, `WRK`, `BKP`)
3. identify input files
4. validate XML against expected schemas
5. invoke processing/business logic
6. generate feedback/output artifacts
7. archive or move processed files
8. return exit code

Evidence:

- `SimogMassLoader/src/it/avlp/simog/massload/Main.java`

## Administrative / Utility Flows

Visible code also suggests ad hoc utility or support flows:

- `QueryCIG`
- `BuildListTypes`
- `DatabaseNavigatorSQLServer`

These are not central user-facing lifecycles, but they are operationally relevant for support and data handling.

## Cross-Cutting Lifecycle Observations

- connection and transaction management are manual and repeated
- validation is spread across controllers, validators, and DB helpers
- state management is partly session-based and partly DB-driven
- many flows depend on external configuration loaded at runtime
- SOAP and web lifecycles reuse the same core business/persistence modules
