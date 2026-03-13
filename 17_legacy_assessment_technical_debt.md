# Legacy Assessment and Technical Debt

## Overall Assessment

SIMOG is a mature legacy enterprise system with substantial domain knowledge encoded in source code, but its architecture shows clear technical debt accumulated over time.

## Legacy Technologies

Evidence-based legacy indicators:

- Java 6-level build settings
- JSP/Servlet-centric UI
- Axis/JAX-RPC-era generated SOAP artifacts
- manual JDBC and application-managed transactions
- JBoss-specific descriptors

## Architectural Rigidity

Main causes:

- strong centrality of `SimogCommon`
- broad dependency on `AccessiDB`
- shared configuration hub in `SimogProperties`
- distributed workflow rules encoded procedurally

Consequence:

- local changes can have system-wide side effects
- architectural boundaries are not strongly enforced

## Code Smells at System Level

- god classes such as `AccessiDB` and large managers like `GaraManager`
- mixed concerns in servlets and managers
- duplicated transaction/error-handling patterns
- generated and hand-written code interleaved
- procedural branching based on request parameters

## Maintainability Issues

- business rules are often not isolated from persistence
- naming mixes business-domain Italian with technical conventions, limiting quick onboarding for non-native maintainers
- hidden workflow logic requires code reading across multiple modules
- operational behavior depends on external configuration not versioned with code

## Coupling and Cohesion Problems

- high coupling around core modules and shared utility classes
- cohesion varies widely; some modules are focused, others are catch-all hubs
- web and WS layers both depend directly on shared business managers, reducing encapsulation

## Obsolete Libraries / Frameworks

Visible or likely obsolete patterns:

- Axis-era service exposure/client generation
- XMLBeans-era artifacts
- old Java baseline

This does not imply non-functionality, but it does increase modernization and support cost.

## Testing Gaps

Visible evidence of automated testing is very limited.

Observed:

- archetype-style `AppTest`
- some ad hoc test sources in `SimogMassLoader`

Implication:

- regression risk is likely managed mainly by manual testing, operational knowledge, or selective integration testing

## Documentation Gaps

- external configuration meaning is only partly discoverable from code
- some acronyms and legal/business semantics are not self-explanatory
- database schema intent is implicit rather than documented
- service-contract evolution history is absent from the repository

## Operational Fragility

- multiple external dependencies
- filesystem dependence for batch/configuration
- hand-managed transactions
- distributed error handling
- potential drift between environments due to external properties

## Scalability Constraints

Likely constraints inferred from architecture:

- stateful HTTP/session model
- synchronous SOAP interactions
- DB-centric processing
- limited visible caching strategy

## Modernization Blockers

- business knowledge concentrated in large legacy classes
- insufficient automated tests for safe refactoring
- mixed technology stack across modules
- likely institutional dependence on established service contracts and DB schema
