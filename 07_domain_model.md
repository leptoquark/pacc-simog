# Domain Model Reconstruction

## Domain Overview

SIMOG models a public procurement lifecycle domain centered on procedures (`gara`) and lots (`lotto`), enriched by publication, award, administrative, interoperability, and TED-publication concepts.

This reconstruction is derived from:

- manager class names
- servlet mappings
- SOAP operations
- DB helper methods and SQL references
- XSD/WSDL artifacts
- TED JPA entities

## Core Entities

## Gara

Meaning:

- procurement procedure or tender

Evidence:

- `GaraManager`
- `XSD/GaraWS.xsd`
- TED entity listing in `persistence.xml`

Likely responsibilities:

- hold the primary procurement header data
- aggregate one or more lots
- connect to publication and administrative metadata

## Lotto

Meaning:

- lot associated with a gara

Evidence:

- `LottoManager`
- `XSD/LottoWS.xsd`
- TED entity listing in `persistence.xml`

Likely relationship:

- many lots belong to one gara

## Pubblicazione / Publication

Meaning:

- publication records related to procurement procedure and/or award lifecycle

Evidence:

- `PubblicazioneBandoManager`
- `PubblicazioneAggiudicazioneManager`
- `Pubblicazioni` listed in TED persistence

## Iniziativa

Meaning:

- initiative communicated through aggregator-oriented service flow

Evidence:

- `IniziativaManager`
- `SimogWSAGG.comunicaIniziativa(...)`

## WS Session / Ticket

Meaning:

- technical session object for SOAP consumers

Evidence:

- `WSSessionManager`
- `WS_SESSIONS` table references
- `WsSession` TED entity listing

Likely attributes:

- ticket
- status
- collaboration or caller metadata
- timestamps / validity

## Utente / User Profile

Meaning:

- authenticated user and selected profile/session context

Evidence:

- `SrvAutentica`
- session checks in `ServletBase`

## Administrative Reference Data

Inferred entities:

- SA or contracting authority references
- territory classifications
- CPV / ISTAT / NUTS classifications
- rubrica / registry-like items

Evidence:

- servlet mappings `/ricercaCPV`, `/ricercaIstat`, `/ricercaNuts`
- `RubricaManager`

## TED Notice and Related Entities

Entities listed in `SimogTEDCommon` persistence:

- `TEDNotice`
- `TEDStatus`
- `TEDSubmit`
- `TEDTypeNotice`
- `TEDNoDocExt`
- `TedDelta`

Meaning:

- represent publication notice, status tracking, submission state, type taxonomy, document extension metadata, and delta-extraction state

## Relationships

## Strongly Supported Relationships

- one `Gara` has multiple `Lotto`
- `Gara` and `Lotto` participate in TED delta/publication flows
- `Gara` and/or `Lotto` are linked to publication records
- service session/ticket state is separate from business data but needed to authorize SOAP operations

## Inferred Relationships

- gara is associated with contracting authority / administrative subject
- lot is associated with CIG and possibly CUP-related metadata
- workflow state is associated with gara/lot/scheda combinations
- publications may reflect both pre-award and post-award phases

## Lifecycle States

Visible state logic is distributed rather than centralized in enums.

Evidence:

- `AccessiDB` methods such as `statoInizio`, `statoConclusione`, `statoVariante`
- `WSSessionManager` statuses `S`, `I`, `P`, `E`
- TED status entities

Inference:

The procurement domain likely uses state transitions tied to procedural phases and form completion status, but the canonical state model is encoded procedurally rather than as a clean domain state machine.

## Invariants and Business Rules

Strongly suggested invariants:

- a lot cannot move into some phases unless prerequisite phases exist
- publications and award/perfection updates depend on prior state
- SOAP operations require a valid WS session/ticket
- authentication/profile context is mandatory for protected web flows

Evidence:

- workflow controller logic
- `AccessiDB` state-check methods
- WS session validation logic

## Entity Catalog

| Entity / Concept | Role | Evidence |
| --- | --- | --- |
| Gara | Main procurement procedure | `GaraManager`, `GaraWS.xsd` |
| Lotto | Child lot within a procedure | `LottoManager`, `LottoWS.xsd` |
| Pubblicazione | Publication event/data | publication managers, TED entities |
| Iniziativa | Initiative/aggregator payload | `IniziativaManager`, `SimogWSAGG` |
| Utente | Authenticated operator | `SrvAutentica`, session logic |
| WS Session | SOAP technical session/ticket | `WSSessionManager`, `WS_SESSIONS` |
| TEDNotice | TED publication notice | TED JPA entities |
| TedDelta | TED delta tracking | TED JPA entities |
| Rubrica item | Administrative registry/contact/ref data | `RubricaManager` |

## Domain Glossary

- `Gara`: procurement procedure or tender
- `Lotto`: lot belonging to a procurement procedure
- `CIG`: domain-specific procurement identifier handled in several flows
- `CUP`: integration-related identifier/service domain visible in dedicated client packages
- `Scheda`: phase-specific form or data section in the procurement lifecycle
- `Pubblicazione`: publication-related information or event
- `TED`: e-procurement publication/exchange domain managed in dedicated modules
- `WS Session`: technical SOAP interaction session tracked by ticket

## Domain Reconstruction Limits

- Exact field-level semantics cannot always be reconstructed from source alone.
- Some legal/administrative codes require maintainer or domain-expert explanation.
- The canonical DB schema is only partly inferable without DDL artifacts.
