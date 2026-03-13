# Integration and Interoperability Document

## Integration Landscape

SIMOG is heavily integration-oriented. The repository contains both inbound service exposure and outbound client stubs/proxies for external services.

## Inbound Integrations

## SOAP Procurement Services

### Purpose

Expose procurement operations to external systems.

### Direction

Inbound

### Technology

SOAP / JAX-WS endpoint classes in dedicated WARs

### Main Classes

- `SimogWSPDD`
- `LoaderAppaltoWS`
- `LoaderAppaltoWSAVCPASS`
- `SimogWSOSS`
- `SimogWSAGG`
- `SimogWSTED`

### Payloads

- JAXB/XMLBeans-style objects
- XML/XSD-defined procurement structures

### Error Handling

- technical result objects
- endpoint-level exception handling
- WS session/ticket validation failures

### Configuration Points

- endpoint mappings in each module `web.xml`
- shared configuration through `SimogProperties`

## Outbound Integrations

## Authentication Providers

### Purpose

Authenticate users through local, remote, or IAA/IAM-based mechanisms.

### Direction

Outbound

### Technology

SOAP proxy calls through generated clients

### Main Classes

- `LoginManager`
- `RemoteLogin`
- generated proxies under `it.avcp.iam.service.wsdl.impl` and related packages

### Operational Implications

- authentication availability is externally dependent
- coexistence of multiple auth modes increases configuration complexity

## Anagrafe Services

### Purpose

Retrieve or synchronize registry/anagraphic information.

### Direction

Outbound

### Main Packages

- `it.avcp.anagrafe.AnagrafeWS`

### Notes

Exact message semantics require WSDL-level study, but the integration is clearly present in source.

## CUP Services

### Purpose

Integrate with CUP-related external services.

### Direction

Outbound

### Main Packages

- `it.mef.serviziCUP`

### Evidence

- dedicated generated client package
- service operation name `integraCUP`

## AVCpass

### Purpose

Interact with AVCpass-related interoperability flows.

### Direction

Inbound and outbound, depending on operation

### Main Packages

- `it.avcp.avcpass`
- `it.eng.avcp.avcpass`
- endpoint `LoaderAppaltoWSAVCPASS`

## AUSA / Administrative Services

### Purpose

Support administrative registry or authority-related service integration.

### Main Packages

- `ausa.servizi.avcp.it.wsdl`

## TED Integration

### Purpose

Manage notice generation, validation, submission, delta extraction, and publication-oriented exchanges for TED-related processes.

### Direction

Inbound SOAP to SIMOG, with likely outbound document/publication interactions inferred from naming and service intent

### Main Classes

- `SimogWSTED`
- `TEDDbService`
- notice/validator/XML packages under `it.anticorruzione.ted.*`

### Payloads

- TED-specific XML structures and XSDs

## Database Integration

### Purpose

Primary persistence backend

### Direction

Outbound from every runtime component

### Technology

- JNDI datasource `jdbc/SIMOG`
- manual JDBC
- JPA for TED

### Operational Implications

- database availability is foundational for all runtimes
- web and WS flows depend on shared DB state

## File-Based Batch Integration

### Purpose

Mass import/export through XML files and directory handoff

### Direction

Inbound and outbound at filesystem level

### Main Classes

- `SimogMassLoader.Main`

### Operational Implications

- file-placement conventions are part of runtime integration contract
- schema validation is operationally critical

## XML / Contract Dependencies

Visible artifacts:

- WSDLs in `SimogAVCPLogin/wsdl` and `SimogWSPDD/wsdl_from_browser`
- root `XSD/*.xsd`
- TED `src/XSD/**/*`

These artifacts are essential for reverse-engineering external interface contracts and should be preserved as documentation sources.

## Integration Risks

- multiple legacy SOAP technologies coexist
- external dependencies are configured outside the repository
- fault handling is distributed rather than standardized
- generated artifacts increase coupling to contract versions
- some integrations depend on historical business and legal semantics not visible in code alone
