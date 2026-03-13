# Study Visit Presentation Support Material

## How to Explain the System in 5 Minutes

SIMOG is a legacy but operationally rich procurement lifecycle platform. Its main web application is a JSP/Servlet system used by authenticated operators to manage tenders, lots, and phase-specific procedural forms. The same domain logic is also exposed through several SOAP service modules for machine-to-machine interoperability, while batch XML import and a dedicated TED publication subsystem support high-volume and external publication scenarios.

Technically, the system is centered on `SimogCommon`, where most business rules and JDBC persistence logic live. `SimogWeb` is the main browser-facing WAR, `SimogWSCommon` provides shared SOAP action logic, `SimogWSPDD` and `SimogWSAGG` expose procurement services, and `SimogWSTED` plus `SimogTEDCommon` manage TED-related processes. The architecture is modular in build structure but strongly coupled in code, with workflow rules spread across validators, managers, and DB helper classes.

## How to Explain the System in 15 Minutes

1. Start with the business purpose.
   SIMOG supports the procurement lifecycle: tender registration, lot management, publications, procedural state transitions, external interoperability, and TED-oriented publication support.

2. Explain the runtime channels.
   There are four main channels: browser UI in `SimogWeb`, SOAP services in `SimogWSPDD`/`SimogWSAGG`/`SimogWSTED`, batch import in `SimogMassLoader`, and outbound integrations to authentication, registry, and procurement-related services.

3. Explain the architectural core.
   The technical center of gravity is `SimogCommon`, especially `AccessiDB`, `GaraManager`, and related managers. Business logic and SQL are closely intertwined. `ServletBase` and `ConnectionWSManager` provide manual transaction/connection patterns for web and SOAP.

4. Explain the hidden architecture.
   Workflow is not managed by a BPM engine. Instead, lifecycle rules are encoded procedurally in validators, workflow controllers, and DB-access methods. That is why reverse engineering must focus on the interaction between UI flows, managers, and persistence code.

5. Explain the operational model.
   The system expects a JBoss-like application server, JNDI datasource configuration, external property files, and access to multiple external services. SOAP contracts and XML schemas are first-class artifacts. TED is a distinct but integrated subsystem using JPA, unlike the JDBC-heavy core.

## Key Talking Points for Technicians

- SIMOG is not a modern framework application; it is a legacy enterprise system with strong operational value.
- Procurement workflow knowledge is embedded in code, especially managers and validators.
- `AccessiDB` is a major architectural hub and a key maintenance risk.
- SOAP interoperability is extensive and operationally important.
- Configuration externalization is broad and must be understood together with deployment environment.
- TED is the clearest subsystem boundary and shows a more structured persistence approach.

## Likely Questions from Visitors

### Where does a user request actually start?

In `SimogWeb/WebContent/WEB-INF/web.xml`, then through filters, then through servlets usually derived from `ServletBase`.

### Where is the business logic concentrated?

Mostly in `SimogCommon`, especially large manager classes and DB helper layers.

### How are integrations implemented?

Through dedicated SOAP WARs for inbound contracts and generated client proxies/stubs for outbound calls.

### Is there a formal workflow engine?

No explicit BPM engine is visible. Workflow is encoded in validator and manager logic.

### Is persistence modernized consistently?

No. The core uses JDBC/manual SQL; TED uses JPA.

## Suggested Answers Grounded in Architecture

- The system is modular at build level but not strongly decoupled internally.
- The most stable architectural constants are the domain model around gara/lotto and the SOAP contracts.
- The highest-value reverse-engineering areas are `ServletBase`, `AccessiDB`, `GaraManager`, `WSSessionManager`, and `SimogWSTED`.

## What Parts Are Most Worth Demonstrating Live

- login and session establishment
- creation or update of a `gara`
- lot management and phase-specific scheda navigation
- one SOAP operation such as `inserisciGara`
- one TED flow such as `pubblicaGaraTED`
- the mass-loader directory-based processing path

## What Diagrams Should Be Shown First

- system context diagram
- module/container diagram
- request lifecycle sequence
- integration map
- procurement workflow sequence for gara and lotto

## Acronyms / Terms Worth Explaining Early

- `Gara`
- `Lotto`
- `CIG`
- `CUP`
- `TED`
- `WS Session`
- `Scheda`
- `AUSA`
- `AVCPASS`
- `IAA` / `IAM`
