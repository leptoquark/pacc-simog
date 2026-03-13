# Executive Technical Overview

## System Purpose

SIMOG is a public-sector procurement lifecycle management and monitoring platform. Evidence from package names, JSP names, XML schema names, and service methods indicates support for tender registration, lot management, publication, award/perfection flows, contract updates, workflow validation, and external interoperability.

Evidence:

- `SimogWeb/WebContent/WEB-INF/web.xml`
- `SimogCommon/src/it/avlp/simog/garamanager/*`
- `SimogWSPDD/src/it/avlp/simog/ws/endpoint/SimogWSPDD.java`
- `XSD/GaraWS.xsd`
- `XSD/LottoWS.xsd`

## Business Domain Covered

The source code indicates management of public procurement procedures, tenders, lots, publications, award information, interoperability tickets/sessions, administrative registries, and TED publication processes.

Representative evidence:

- `GaraManager`, `LottoManager`, `PubblicazioneBandoManager`, `PubblicazioneAggiudicazioneManager`
- TED classes under `it.anticorruzione.ted.*`
- service operations such as `inserisciGara`, `modificaLotto`, `perfezionaLotto`, `integraCUP`, `inviaAggiudicazioneTED`

## High-Level Functional Scope

- Web UI for internal or authenticated operator use
- SOAP service exposure for external system interoperability
- Authentication through local/remote/IAA-connected login logic
- Workflow/state enforcement for procurement forms and procedural phases
- Batch mass-loading of procurement data through XML files
- TED-specific outbound publication and delta management

## Primary User Categories

The exact organizational roles are not fully documented in code, but the following actor categories are strongly suggested:

- internal application operators managing tenders, lots, and procurement records
- external interoperating systems invoking SOAP services
- administrative users handling configuration, registries, or profile selection
- batch operators or support teams running the mass-loader
- TED-related operators or background support staff managing publication flows

This is partly inferred from servlet names, role checks, login/profile logic, and endpoint design.

## Architectural Style

SIMOG uses a hybrid legacy enterprise architecture:

- server-side JSP/Servlet web application
- procedural service/controller pattern around servlets and action classes
- business logic concentrated in large manager classes
- direct JDBC-based persistence in the core modules
- SOAP-oriented service exposure and SOAP client integrations
- a separate JPA-based subsystem for TED data management

This is not a modern dependency-injected layered framework. The architecture is modular at build level but tightly coupled at code level around common managers, shared configuration, and shared DB helpers.

## Key Technologies

- Java 6 level configuration in Maven parent
- Maven multi-module build
- one Ant build file for `SimogXmlBeansPDD`
- JSP, Servlets, Filters
- Axis servlet exposure in `SimogWeb`
- JAX-WS annotated endpoints in WS WARs
- JAX-RPC/Axis-generated stubs for some integrations
- JDBC with manual transaction management
- JPA in TED subsystem
- Log4j logging
- XML/XSD/WSDL-heavy interoperability

## Main Integration Points

Visible integration families include:

- external login/IAM or IAA-related authentication services
- Anagrafe services
- CUP services (`it.mef.serviziCUP`)
- AVCpass-related services
- AUSA-related services
- TED publication subsystem
- SOAP consumers invoking SIMOG service endpoints

## Overall Technical Characteristics

- legacy but still actively integrated enterprise application
- DB-centric design with strong coupling between business rules and persistence
- large operational dependence on container configuration and external property files
- multiple independently deployable WARs
- strong reliance on XML contracts, schemas, and generated artifacts
- sparse automated test evidence

## Likely Operational Context

The system is designed for deployment in a Java EE-style application server with JNDI datasource support and WAR deployment. JBoss descriptors are present in the web modules, and `web.xml` plus datasource lookup patterns indicate container-managed hosting rather than embedded runtime packaging.

Evidence:

- `*/WebContent/WEB-INF/jboss-web.xml`
- `*/WebContent/META-INF/jboss-classloading.xml`
- JNDI datasource references such as `jdbc/SIMOG`
- `ServletBase` and `ConnectionWSManager` datasource lookup logic
