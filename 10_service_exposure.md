# API / Service Exposure Analysis

## Exposed Service Endpoints

## `SimogWSPDD`

Endpoint mapping:

- `/services/SimogWSPDD`

Primary evidence:

- `SimogWSPDD/WebContent/WEB-INF/web.xml`
- `SimogWSPDD/src/it/avlp/simog/ws/endpoint/SimogWSPDD.java`

Representative operations:

- `login`
- `loginRPNT`
- `chiudiSessione`
- `inserisciGara`
- `consultaGara`
- `consultaNumeroGara`
- `modificaLotto`
- `perfezionaLotto`
- `integraCUP`
- `integraPariOpportunita`
- `modificaDatiPerfezionamento`
- `modificaCPV`
- `consultaIniziativa`

Service profile:

- broad procurement interoperability interface
- mixes session management, consultation, creation, and update operations

## `LoaderAppaltoWS`

Endpoint mapping:

- `/services/LoaderAppaltoWS`

Likely purpose:

- loader-oriented SOAP import or batch-interoperability surface

Evidence:

- web descriptor mapping
- related WSDL artifact in `wsdl_from_browser`

## `LoaderAppaltoWSAVCPASS`

Endpoint mapping:

- `/services/LoaderAppaltoAVCPASS`

Likely purpose:

- AVCpass-oriented loading/integration flow

## `SimogWSOSS`

Endpoint mapping:

- `/services/SimogWSOSS`

Likely purpose:

- specialized procurement SOAP surface; exact acronym semantics require domain confirmation

## `SimogWSAGG`

Endpoint mapping:

- `/services/SimogWSAGG`

Primary operation evidence:

- `comunicaIniziativa(...)`

Service profile:

- specialized integration endpoint for initiative communication and related aggregator flow

## `SimogWSTED`

Endpoint mapping:

- `/services/SimogWSTED`

Primary evidence:

- `SimogWSTED/WebContent/WEB-INF/web.xml`
- `SimogWSTED/src/it/avlp/simog/ws/endpoint/SimogWSTED.java`

Representative operations:

- `deltaGaraTED`
- `deltaLottoTED`
- `cancellaDeltaGaraTED`
- `cancellaDeltaLottoTED`
- `consultaGaraTED`
- `pubblicaGaraTED`
- `verificaTED`
- `inviaAggiudicazioneTED`
- `inviaRettificaTED`
- `inviaAvvisoModificaTED`
- `cancellaRichiestaTED`

## Input / Output Structures

The exposed services rely on:

- XML/XSD-defined procurement structures
- generated or strongly typed request/response objects
- technical result objects included in payloads

Strong evidence:

- WSDL and XSD artifacts
- endpoint method signatures

## Validation Logic

Observed validation patterns:

- mandatory parameter checks in endpoint methods
- service ticket validation through `WSSessionManager`
- downstream business validation in shared action managers
- TED-specific schema and notice validation in TED flows

## Downstream Dependencies

Service operations call into:

- `SimogWSCommon` action managers
- `SimogCommon` managers and validators
- DB helpers and session managers
- TED services and repositories
- external integrations such as CUP or authentication providers where applicable

## Security Assumptions

- caller authentication is explicit for login operations
- protected calls require valid WS ticket/session
- no evidence of modern token standards inside the codebase
- transport-level security assumptions are not visible in source and likely depend on deployment infrastructure

## Versioning Approach

Explicit service versioning strategy is not strongly visible in code.

Evidence:

- version property files exist per module
- WSDL artifacts exist as snapshots

Inference:

Versioning may be handled operationally through deployment/version properties and contract management rather than through URL-versioned APIs.
