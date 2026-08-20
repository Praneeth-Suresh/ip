# Design Tree

## Current Design Concept

The project is a Java personal-assistant chatbot. Its sole confirmed purpose is
to help one person keep track of various things; detailed commands, tracked
categories, storage, and interaction rules remain open.

## Open Decisions

| Decision | Options | Current Lean | Why |
| --- | --- | --- | --- |
| What things should the chatbot track? | Tasks, events, notes, other | Not decided | Requirements have not specified categories. |
| How should tracked information persist? | In memory, local file, other | Not decided | No persistence requirement exists yet. |

## Settled Decisions

| Decision | Choice | Date | ADR |
| --- | --- | --- | --- |
| Java 25 is required for build and run tasks. | Java 25 | 2026-08-20 | n/a |
| Tracked information is held only for the current session. | A dynamically sized in-memory `ArrayList<Task>` | 2026-08-20 | n/a |
| Task types share completion behavior while owning their distinct details. | Abstract `Task` base class with `Todo`, `Deadline`, and `Event` subclasses | 2026-08-20 | n/a |
| User command errors are recoverable. | `OdysseusException` is caught once per command; invalid commands do not mutate `TaskList` | 2026-08-20 | n/a |

## Pressure Points

- The available project information is intentionally minimal; obtain product
  requirements before implementing behavior.

## Recording Rule (Design Tree vs ADR)

Add or update this file when:

- A decision is still evolving.
- You are comparing options before implementation.
- The choice may still change after one or two implementation iterations.

Create an ADR when:

- The decision changes module boundaries, persistence shape, adapter contracts, security model, naming conventions used across contexts, or test strategy.
- Future contributors are likely to revisit the choice without clear repo history.
