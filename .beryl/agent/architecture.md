# Architecture

## Bounded Contexts

| Context | Owns | Does Not Own | Public Entry Point |
| --- | --- | --- | --- |
| Personal assistant chatbot | Conversational interactions and the user's tracked information | Unspecified external integrations and persistence details | `src/main/java/Duke.java` |

## Boundary Rules

1. A context may import only another context's public entry point.
2. Internal files of another context are forbidden imports.
3. External APIs, SDKs, and persistence details must be accessed through adapters.
4. Domain logic must not depend directly on HTTP objects, ORM records, UI state, or vendor client types.

## Public Interface Rule

Each context exposes one explicit public entry point:

- Java: the application entry point currently starts at
  `src/main/java/Duke.java`. Introduce a small explicit public API for each
  additional context when it is created.

## Forbidden Import Policy

Record concrete forbidden import patterns here once contexts exist:

- `[from] -> [to/internal/**]`
- `[from] -> [to/infrastructure/**]`

Keep this list small and high-signal. Add rules only after repeated boundary mistakes.
