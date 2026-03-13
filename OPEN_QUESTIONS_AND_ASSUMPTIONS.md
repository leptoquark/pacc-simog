# Open Questions and Assumptions

## Open Questions

1. What is the authoritative production deployment topology?
   The code shows multiple WARs, JBoss descriptors, and a shared datasource, but the exact clustering, network zones, and node separation are not visible.

2. What is the official meaning of all business acronyms and legal/regulatory feature toggles?
   `SimogProperties` and package names expose many domain-specific switches, but their legal and organizational meaning requires domain-owner confirmation.

3. Which authentication path is authoritative in production today?
   Code contains local, remote, and IAA/IAM logic, plus commented-out IAM filter logic. The active operational path should be confirmed with maintainers.

4. What is the canonical database schema and who owns it?
   Table and entity names are inferable, but DDL, release process, and schema governance are not present in the repository.

5. Which SOAP endpoints are still officially consumed by external partners?
   Multiple endpoints and WSDL snapshots exist, but usage status and compatibility commitments require operational confirmation.

6. How are failures handled operationally for batch, external services, and TED publication?
   Code shows local error handling, but operational runbooks and escalation paths are not visible.

7. Are there security controls at infrastructure level not visible in code?
   TLS termination, network filtering, secret management, and certificate governance likely exist outside this repository.

## Assumptions Used in the Documentation

1. `SimogWeb` is treated as the main browser-facing application because of its JSP content and broad servlet mappings.

2. `SimogCommon` is treated as the architectural core because most business managers, validators, DB helpers, and configuration logic converge there.

3. `AccessiDB` is treated as a central architectural hub because many managers extend or depend on it, and it contains both persistence and workflow-related logic.

4. The runtime target is assumed to be JBoss-like because JBoss descriptors and JNDI patterns are present.

5. The system is described as legacy because of Java level, servlet/JSP style, SOAP-generation patterns, and manual JDBC transaction handling.

6. Workflow/state logic is described as implicit because no explicit BPM engine or centralized state-machine framework was found, while validators and DB helpers contain state checks.

7. TED is treated as a semi-bounded subsystem because it has its own persistence unit, entities, services, validators, and endpoint module.
