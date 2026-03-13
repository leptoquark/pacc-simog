# Module-by-Module Analysis

## Module Table

| Module | Purpose | Key Packages | Inbound Flows | Outbound Flows | Observations |
| --- | --- | --- | --- | --- | --- |
| `SimogParent` | Aggregates build and shared dependency policy | root POM only | Maven build | All child modules | Parent fixes Java level at legacy baseline |
| `SimogXmlBeans` | Schema-derived support classes | schema-related packages | Used by SOAP/XML handling | XML serialization/validation | Generated/support role |
| `SimogCommon` | Core business logic, validation, JDBC access | `it.avlp.simog.db`, `garamanager`, `validatore`, `util` | Called by web, WS, batch | DB operations, internal domain orchestration | Central dependency hub |
| `SimogWSCommon` | Shared WS action logic and integration helpers | `it.avlp.simog.common.actions`, `common.sql`, external stub packages | Called by WS WARs | DB, WS session table, external services | Core interoperability hub |
| `SimogAVCPLogin` | Authentication integration and login abstraction | `it.avlp.simog.login.*`, generated login proxies | Called by `SrvAutentica` and WS login paths | External login/IAM services | Contains legacy and newer auth branches |
| `SimogFlusso` | Workflow controller support | `it.avlp.simog.flusso` | Called by validation/mass-loader flows | Internal workflow selection | Narrow but important control role |
| `SimogMassLoader` | Batch import of procurement XML flows | `it.avlp.simog.massload` | CLI invocation | DB, filesystem, XML validation, feedback output | Operational batch subsystem |
| `SimogWeb` | Main browser-facing application | `it.avlp.simog.servlet`, `auth`, `actions`, JSPs | HTTP browser requests | `SimogCommon`, login, DB, JSP rendering | Main end-user entry point |
| `SimogWSPDD` | Main SOAP exposure for procurement operations | `it.avlp.simog.ws.endpoint` | SOAP requests | `SimogWSCommon`, `SimogCommon`, DB | High-contract integration surface |
| `SimogWSAGG` | SOAP exposure for aggregator/initiative flows | `it.avlp.simog.ws.endpoint` | SOAP requests | initiative logic, DB | Specialized interoperable surface |
| `SimogTEDCommon` | TED persistence and service logic | `it.anticorruzione.ted.*` | Called by `SimogWSTED` | JPA persistence, XML/TED processing | More structured than legacy core |
| `SimogWSTED` | SOAP exposure for TED publication and delta flows | `it.avlp.simog.ws.endpoint`, `it.anticorruzione.ted.actions` | SOAP requests | TED DB service, XML validation, publication flows | Separate integration boundary |
| `SimogXmlBeansPDD` | Additional schema-derived artifact built with Ant | XMLBeans-related | Build-time/support usage | XMLBeans classes | Not included in parent modules |

## Detailed Analysis

## `SimogParent`

Responsibilities:

- defines the Maven reactor
- centralizes module inclusion
- fixes compiler/source level expectations

Evidence:

- root `pom.xml`

Observations:

- Java level indicates legacy runtime constraints
- the build is module-structured, but architecture remains tightly coupled in code

## `SimogCommon`

Responsibilities:

- central business rules
- DB access and SQL generation
- validation and workflow-state checks
- utility and configuration support
- domain-oriented managers for tenders, lots, publications, documents, and administrative structures

Key classes:

- `it.avlp.simog.db.AccessiDB`
- `it.avlp.simog.garamanager.GaraManager`
- `it.avlp.simog.garamanager.lotto.LottoManager`
- `it.avlp.simog.validatore.WorkFlowController`
- `it.avlp.simog.util.SimogProperties`

Dependencies:

- used by almost every higher-level module
- depends on JDBC, generated DB classes, utility code, XML-related helpers

Technical observation:

This is the effective core of the application and the primary reverse-engineering target.

## `SimogWSCommon`

Responsibilities:

- shared implementation of SOAP actions
- WS connection handling
- WS session/ticket management
- encapsulation of common SOAP-side business orchestration
- hosting generated proxies for several external services

Key classes:

- `ConnectionWSManager`
- `WSSessionManager`
- `LoginActionManager`
- `InserisciGaraActionManager`

Observations:

- it bridges external contract semantics and internal manager-based logic
- it centralizes technical session handling for service consumers

## `SimogAVCPLogin`

Responsibilities:

- abstract login mode selection
- connect to remote login services
- parse or relay authentication outcomes

Key classes:

- `LoginManager`
- `LocalLogin`
- `RemoteLogin`
- `IAALogin`

Observation:

- the coexistence of old and new login proxy paths indicates architectural sediment from authentication evolution

## `SimogFlusso`

Responsibilities:

- control flow selection by flow type
- workflow abstraction for selected operational scenarios

Key class:

- `it.avlp.simog.flusso.WorkFlowController`

Observation:

- although small, it indicates workflow logic is recognized as a first-class concern

## `SimogMassLoader`

Responsibilities:

- command-line ingestion of XML payloads
- directory-based file processing
- schema validation
- feedback/result production

Key class:

- `it.avlp.simog.massload.Main`

Observation:

- batch integration is operationally significant and not just an internal utility

## `SimogWeb`

Responsibilities:

- end-user navigation and data-entry UI
- session-managed business operations
- servlet dispatch to business managers
- JSP rendering

Key classes:

- `ServletBase`
- `SrvAutentica`
- `SrvInserisciGara`
- many functional servlets mapped in `web.xml`

Observation:

- the module is broad and contains both infrastructure and business-facing controllers, reflecting a legacy web-application style

## `SimogWSPDD`

Responsibilities:

- expose procurement-oriented SOAP services
- manage login/session for service consumers
- implement create/update/consult flows for procurement records

Key class:

- `it.avlp.simog.ws.endpoint.SimogWSPDD`

Observation:

- service API is broad and operation-heavy, suggesting it supports substantial machine-to-machine interoperability

## `SimogWSAGG`

Responsibilities:

- expose aggregator-facing operations, especially initiative communication

Key class:

- `it.avlp.simog.ws.endpoint.SimogWSAGG`

Observation:

- narrower scope than `SimogWSPDD`, but integration-specific logic is significant

## `SimogTEDCommon`

Responsibilities:

- manage TED-specific entities, repositories, services, validators, and XML support

Key classes:

- `TEDDbService`
- entity classes listed in `persistence.xml`

Observation:

- compared with the legacy core, this subsystem shows a more explicit persistence model through JPA entities and repositories

## `SimogWSTED`

Responsibilities:

- expose TED-oriented SOAP operations for delta management, publication, verification, and cancellation

Key class:

- `it.avlp.simog.ws.endpoint.SimogWSTED`

Observation:

- this module forms a relatively clear bounded integration area around TED processes
