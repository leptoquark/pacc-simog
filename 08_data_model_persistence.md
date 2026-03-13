# Data Model and Persistence Analysis

## Persistence Approach

SIMOG uses two persistence styles:

- direct JDBC and manual SQL for the core application
- JPA with `RESOURCE_LOCAL` transactions for the TED subsystem

## Core JDBC Model

### Primary Access Pattern

The dominant persistence pattern is manager classes extending `AccessiDB`, using manual SQL construction and direct connection handling.

Evidence:

- `SimogCommon/src/it/avlp/simog/db/AccessiDB.java`
- manager classes in `it.avlp.simog.garamanager.*`

### Characteristics

- SQL and business rules are interwoven
- transaction demarcation is mostly performed by callers
- result mapping appears partly manual and partly assisted by generated DB classes
- DB checks often encode workflow-state logic

### Representative Artifacts

- `AccessiDB`
- `GaraManager`
- `LottoManager`
- `DocumentoManager`
- generated classes in `it.avlp.simog.db.generated`

## WS Persistence Support

SOAP layers use dedicated DB helpers for connection and session handling.

Key classes:

- `ConnectionWSManager`
- `WSSessionManager`

Responsibilities:

- open connections from JNDI datasource or `DriverManager`
- control autocommit, commit, rollback
- persist WS session/ticket state in `WS_SESSIONS`

## TED Persistence Model

### Persistence Unit

`SimogTEDCommon/src/main/java/META-INF/persistence.xml`

Key properties:

- persistence unit name: `ted`
- transaction type: `RESOURCE_LOCAL`
- JNDI datasource: `java:/jdbc/SIMOG`

### Declared Entities

- `TEDNoDocExt`
- `TEDNotice`
- `TEDStatus`
- `TEDSubmit`
- `TEDTypeNotice`
- `Gara`
- `Lotto`
- `Pubblicazioni`
- `TedDelta`
- `WsSession`

### Observations

- TED reuses some business concepts (`Gara`, `Lotto`, `Pubblicazioni`) in a more explicit persistence model
- this subsystem is architecturally cleaner than the legacy JDBC core

## Main Persistent Tables / Concepts

Exact schema DDL is not present, but table references and entity names strongly suggest persistence around:

- `GARA`
- `LOTTO`
- publication-related tables
- `WS_SESSIONS`
- initiative-related tables
- TED-specific notice/status/delta tables

This reconstruction is based on SQL references and entity naming.

## Relationships and Data Structures

Strongly supported:

- `GARA` to `LOTTO` is parent-child
- TED deltas reference procurement entities
- WS sessions are stored separately from business entities

Inferred:

- publication entities link to gara and/or lotto
- administrative lookup tables exist for classifications such as CPV, ISTAT, NUTS
- contracting authority and profile-related reference data is stored in dedicated tables

## Read / Write Patterns

### Read Patterns

- ad hoc query execution within manager classes
- lookup servlets for classification data
- SOAP consultation operations returning procurement information
- TED service queries through repositories

### Write Patterns

- servlet and SOAP actions execute coordinated inserts/updates in a single request transaction
- mass-loader performs batch-oriented writes
- TED flows write deltas, notices, status transitions, and submission records

## Transaction Handling

### Legacy Core

- explicit commit/rollback in servlets and WS endpoints
- no visible declarative transaction framework

### TED

- JPA with resource-local transaction semantics
- exact transaction demarcation requires deeper per-class inspection, but the persistence model is still application-driven

## Caching Patterns

Only limited caching evidence is visible.

Evidence:

- `ServletBase` initializes `HSqlManager` in memory

Inference:

Caching is likely tactical and local, not a platform-wide cache strategy.

## Persistence Risks and Constraints

- strong coupling between SQL and domain logic
- difficult unit testing because persistence is not abstracted cleanly
- transaction safety depends on disciplined caller behavior
- schema understanding is partly buried in generated classes and SQL strings
- DB portability is low
- operational errors may surface late because some validation sits near persistence

## Reverse-Engineering Priority

For full data-model reconstruction, the highest-value follow-up targets are:

- `AccessiDB`
- `GaraManager`
- `LottoManager`
- generated classes under `it.avlp.simog.db.generated`
- TED entities and repositories
