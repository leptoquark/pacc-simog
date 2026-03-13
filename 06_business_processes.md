# Business Process Mapping

## Scope

The workflows below are included only where code evidence is strong enough to support them. In several cases, the full business semantics remain partly inferred from Italian naming, servlet mappings, SQL table names, and service operation names.

## Workflow 1: Tender / Procedure Registration

### Business Objective

Create and manage a procurement procedure (`gara`) and its associated lifecycle information.

### Actors

- authenticated web operator
- interoperating external system through SOAP

### Triggering Event

- browser request to servlet paths such as `/inizializzaGara` or `/InserisciGara`
- SOAP invocation such as `SimogWSPDD.inserisciGara(...)`

### Sequence of Steps

1. user or external system submits tender data
2. session or service ticket is validated
3. business layer validates mandatory fields and workflow rules
4. `GaraManager` and related helpers persist the procedure
5. downstream consistency checks and derived updates are executed
6. response is returned as JSP navigation outcome or SOAP result payload

### Core Classes / Modules

- `SimogWeb`
- `SimogWSPDD`
- `SimogCommon`
- `SrvInserisciGara`
- `InserisciGaraActionManager`
- `GaraManager`
- `AccessiDB`

### Key Data Manipulated

- gara identifiers
- procurement metadata
- publication-related information
- delegations and administrative references

### Validations / Checks

- session validity
- workflow/state constraints
- business-field validations
- service ticket and collaboration checks in SOAP flows

### Outputs Produced

- created or updated gara record
- user-facing message or SOAP response
- updated workflow status

### External Dependencies

- database
- authentication/session infrastructure

### Possible Exceptions

- validation failures
- DB errors
- invalid session/ticket
- business rule violations

## Workflow 2: Lot Management

### Business Objective

Create, update, perfect, and consult lots (`lotto`) associated with a procurement procedure.

### Triggering Event

- web requests such as `/InserisciLotto`, `/gestisciLotto`
- SOAP methods such as `modificaLotto`, `perfezionaLotto`

### Core Steps

1. identify parent gara and target lot
2. verify user context or service ticket
3. validate lot-level data and dependencies
4. persist changes through lot-related managers
5. update derived states, perfection data, and related records

### Core Classes

- `LottoManager`
- `GaraManager`
- `SimogWSPDD`
- workflow and validator classes

### Key Data

- lot identifiers / CIG-related information
- award/perfection details
- publication and status indicators

### Important Observation

Lot lifecycle management appears central to the application and is intertwined with state rules embedded in `AccessiDB` and validators.

## Workflow 3: CIG and Related Administrative Queries

### Business Objective

Support lookup, management, or enrichment of CIG-related information and related administrative classifications.

### Evidence

- servlet mappings such as `/elencoCig`
- utility class `QueryCIG`
- exception `CigException`

### Inference

The codebase suggests that CIG handling is a significant business concern, but full semantics would require domain interviews to complement code evidence.

## Workflow 4: Publication / Award / Contract Update Flows

### Business Objective

Manage procurement publication and post-award contractual changes such as variants, suspensions, stipulations, conclusions, and related schede.

### Evidence

- JSP structure under `scheda*`
- state helper methods in `AccessiDB`
- managers such as `PubblicazioneBandoManager` and `PubblicazioneAggiudicazioneManager`

### Sequence of Steps

1. user navigates to a phase-specific scheda
2. workflow controller determines allowed operation/state
3. relevant manager validates data and updates DB
4. system reflects new procedural phase and publication status

### Key Data

- publication data
- award information
- contract execution updates
- suspension/variant/completion-related attributes

## Workflow 5: Initiative Communication for Aggregators

### Business Objective

Communicate or synchronize initiative data with an external or partner-facing aggregator flow.

### Triggering Event

- SOAP call to `SimogWSAGG.comunicaIniziativa(...)`

### Sequence of Steps

1. validate incoming initiative payload
2. obtain DB connection via `ConnectionWSManager`
3. use `IniziativaManager` and validators to insert/update initiative data
4. manage related territories/categories
5. commit transaction and return technical result

### Core Classes

- `SimogWSAGG`
- `IniziativaManager`
- `IniziativaValidator`

## Workflow 6: Authentication and Profile Selection

### Business Objective

Authenticate user and establish the working profile/session context.

### Triggering Event

- `/checkAuthentication`

### Sequence of Steps

1. receive credentials or SAML/IAM-related payload
2. invoke `LoginManager`
3. execute local, remote, or IAA login path
4. parse authentication result
5. construct `Utente` and profile data
6. populate session and continue navigation

### Core Classes

- `SrvAutentica`
- `LoginManager`
- `RemoteLogin`
- `XmlManager`

### External Dependencies

- remote login web services
- profile/registry-related lookups

## Workflow 7: WS Session / Ticket Lifecycle

### Business Objective

Secure and track machine-to-machine interaction sessions for SOAP consumers.

### Triggering Event

- SOAP login and protected operations

### Sequence of Steps

1. client authenticates through SOAP login
2. system writes or updates WS session record
3. ticket is returned to caller
4. protected operations validate ticket against `WS_SESSIONS`
5. session state is updated after each operation
6. session can be invalidated or closed

### Core Classes

- `LoginActionManager`
- `WSSessionManager`
- `SimogWSPDD`

## Workflow 8: TED Delta / Publication Flow

### Business Objective

Manage TED-specific delta extraction, notice generation, verification, and publication transmission.

### Triggering Event

- SOAP methods exposed by `SimogWSTED`

### Sequence of Steps

1. request identifies gara or lotto delta/publication action
2. TED validators and XML schema checks run
3. TED service layer queries or updates JPA-backed entities
4. notice data is assembled and stored/transmitted
5. response communicates status or payload

### Core Classes

- `SimogWSTED`
- `TEDDbService`
- `it.anticorruzione.ted.notice.*`
- TED JPA entities

## Workflow 9: Mass Loader XML Ingestion

### Business Objective

Import large sets of procurement data from XML files in operational batch mode.

### Triggering Event

- command-line execution of `Main`

### Sequence of Steps

1. verify runtime configuration and directories
2. pick up XML input files
3. validate payloads against schemas
4. process business import logic
5. write feedback files
6. move/archive processed files

### Core Classes

- `Main`
- workflow support in `SimogFlusso`

## Business Process Observations

- Procurement lifecycle management is implemented across web, SOAP, and batch channels rather than in a single interaction surface.
- Workflow is implicit in controller, validator, and persistence logic instead of managed by a dedicated BPM engine.
- State-driven scheda navigation appears to be one of the key hidden architectures of the system.
