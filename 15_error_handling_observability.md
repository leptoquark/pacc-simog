# Error Handling and Observability

## Error Handling Model

SIMOG uses explicit, code-driven error handling rather than a centralized exception framework.

## Exception Types Observed

Representative exception classes:

- `SimogException`
- `ActionException`
- `LottoNotFoundException`
- `CigException`
- `SimogWSException`
- `SimogWsXmlException`
- `TEDErrorException`

Interpretation:

- the system distinguishes between business/process errors and technical/integration errors
- exception handling is scoped by functional area rather than unified in a common policy layer

## Error Propagation Patterns

### Web Flows

- servlet catches exception
- rolls back transaction
- forwards to error page or sends message/validation payload

Evidence:

- helper methods in `ServletBase`

### SOAP Flows

- endpoint catches exceptions
- technical result object is populated
- caller receives structured fault-like business response rather than only transport fault

### Batch Flows

- command-line process uses exit codes and feedback files
- operational diagnosis likely depends on logs and filesystem outputs

## Business Messages

Visible evidence:

- `SimogCommon/src/it/avlp/simog/errormessage/Messaggi.java`

Observation:

- message handling is codified in a large message catalog
- this likely standardizes many user-facing or technical diagnostics

## Logging Framework

### Primary Technology

- Log4j

### Evidence

- widespread `Logger` usage
- `PropertyConfigurator`
- MDC in `AuthenticationFilter`

### Logging Patterns

- system loggers such as `SIMOG_LOGGER` and `MASSLOADER_LOGGER`
- request/session contextual logging
- technical exception logging around servlet and endpoint boundaries

## Monitoring Hooks

No dedicated monitoring or metrics framework was visible during inventory.

No strong evidence observed for:

- Prometheus-style metrics
- JMX-specific custom instrumentation
- tracing frameworks

Inference:

Operational observability likely depends mainly on logs, application server monitoring, and DB/support diagnostics.

## Diagnosability

Strengths:

- explicit logging is present
- MDC enrichment improves trace correlation
- message catalog helps classify known issues

Weaknesses:

- error handling is distributed
- business and technical concerns are mixed in several layers
- lack of visible centralized metrics increases operational dependence on log inspection

## Support / Operations Challenges

- root-cause analysis may require following logic across servlet, manager, DB helper, and integration code
- some failures may depend on external configuration not visible in the repository
- manual transaction handling increases risk of inconsistent failure behavior
