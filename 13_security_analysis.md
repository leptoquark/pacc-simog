# Security Analysis

## Authentication Model

### Web Authentication

Evidence:

- `/checkAuthentication` mapped to `SrvAutentica`
- `LoginManager` supports local, remote, and IAA login flows
- `RemoteLogin` contains generated proxy usage for remote authentication

Observed pattern:

- authentication result is transformed into an application `Utente` context
- session is populated with authenticated user and profile data

### SOAP Authentication

Evidence:

- `SimogWSPDD.login(...)`
- `LoginActionManager`
- `WSSessionManager`

Observed pattern:

- service consumer logs in
- receives ticket/session
- subsequent operations require valid ticket

## Authorization Model

Strong evidence:

- session and profile checks occur in web flows
- business operations check user context and service/session validity

Inference:

Authorization appears decentralized and partly embedded in servlet/business logic rather than enforced through a dedicated role engine or declarative security framework.

## Session Management

### Web

- HTTP session is central
- `ServletBase.checkSession()` enforces user session presence
- locale and user-related context are stored in session

### SOAP

- session/ticket state persisted in database
- statuses managed by `WSSessionManager`

## Filters and Interceptors

### `LocaleFilter`

- enforces UTF-8
- stores locale preference in session

### `AuthenticationFilter`

- enriches MDC logging context with session/user identifiers
- contains commented-out IAM interception logic

Observation:

- active runtime behavior is lighter than the class name may suggest

## Input Validation

Validation is visible in:

- servlet-level checks
- validator classes
- manager preconditions
- SOAP endpoint parameter checks
- XSD validation for XML/TED flows

Security implication:

- there is meaningful input validation, but it is distributed and therefore harder to audit consistently

## Output Encoding / UI Security

No strong evidence of systematic output encoding frameworks was observed during the inventory. Because JSP is used heavily, output safety likely depends on page-level coding practices and tag usage.

This remains an open security review area.

## Audit / Logging of Critical Operations

Evidence:

- Log4j usage throughout the codebase
- MDC enrichment in `AuthenticationFilter`
- transaction and business-operation logging in servlets/managers/endpoints

Inference:

The system has operational logging, but dedicated immutable audit-trail design is not clearly isolated in the visible code.

## Sensitive Operations

Likely sensitive operations include:

- login and profile establishment
- tender and lot creation/update
- award/perfection updates
- WS ticket issuance and invalidation
- TED publication requests
- mass-loader data ingestion

## Probable Security Risks / Legacy Weaknesses

- authentication logic spans multiple historical mechanisms
- commented-out IAM interception logic suggests evolving security design
- manual SQL and request handling increase review surface for injection or consistency defects
- centralized HTTP session usage may complicate traceability and hardening
- transport security assumptions are external to code and must be verified in deployment
- limited visible automated security test evidence

## Trust Boundaries

Visible trust boundaries:

- browser to `SimogWeb`
- SOAP consumers to WS WARs
- application server to database
- application to external authentication and registry services
- filesystem boundary for mass-loader

Each boundary should be validated operationally because not all protections are visible in source alone.
