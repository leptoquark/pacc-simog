# Batch / Scheduler / Background Processing

## Batch Processing Present

The repository contains a concrete batch subsystem: `SimogMassLoader`.

## Mass Loader

### Purpose

Import procurement-related XML files in bulk and generate processing feedback.

### Main Entry Point

- `SimogMassLoader/src/it/avlp/simog/massload/Main.java`

### Runtime Behavior

- checks configuration
- verifies directory structure
- validates XML input
- processes files
- writes feedback/output
- moves or archives files

### Operational Dependencies

- filesystem directories such as `IN`, `OUT`, `WRK`, `BKP`
- XML schemas
- database connectivity
- shared business logic modules

## Supporting Batch / Utility Classes

Visible examples:

- `QueryCIG`
- `BuildListTypes`

These suggest operational support tooling beyond the main batch import path.

## Schedulers

No clear scheduler framework was found in the inventory phase.

No strong evidence observed for:

- Quartz
- `@Scheduled`
- timer listeners
- dedicated background daemon components

Inference:

The system appears primarily request-driven and operator-triggered, with batch handled as explicit command-line execution rather than internal scheduling.

## Retry Patterns

No generalized retry framework is visible from the inventory. Error and reprocessing behavior for mass loading likely depends on file handling, operator intervention, and rerun practices.

## Time-Based Operations

Time-sensitive behavior is visible mainly through:

- date-based feature toggles in `SimogProperties`
- operational timing/validity checks in sessions and service availability logic

## Operational Observations

- batch processing is important for integration and data-ingestion scenarios
- absence of visible scheduling framework suggests external job orchestration may be used
- support teams likely need clear operational runbooks for file placement, failure handling, and replay
