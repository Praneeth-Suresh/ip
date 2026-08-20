# Ubiquitous Language

| Business Term | Technical Symbol | Definition | Constraints | Avoid |
| --- | --- | --- | --- | --- |
| Personal Assistant Chatbot | `Odysseus` | The application that helps one person keep track of various things. | All user-facing text uses Odysseus's clever, nautical, Odyssey-inspired voice. | Generic `app` |
| User | `User` | The individual using the chatbot to keep track of their information. | The project is intended for a person, not a multi-user service. | `Customer` |
| Task | abstract `Task` | A task Odysseus remembers during the current session. | Encapsulates its description and done state; stored in insertion order in a dynamic `ArrayList<Task>` and listed by `list`. | Vague `data` |
| ToDo | `Todo` | A task without date or time information. | Created by `todo <description>` and displayed with `[T]`. | Untimed `event` |
| Deadline | `Deadline` | A task to be completed by supplied text. | Created by `deadline <description> /by <text>`; the text is not parsed. | Calendar event |
| Event | `Event` | A task with supplied start and end text. | Created by `event <description> /from <text> /to <text>`; the text is not parsed. | Deadline |
| Task Status | `TaskStatus` | The completion state of a task. | `NOT_DONE` displays `[ ]`; `DONE` displays `[X]`. | Boolean completion flag |
| Task Action | `TaskAction` | A command that targets a task by one-based number. | Supports `mark`, `unmark`, and `delete`. | Free-form action string |
| Command Error | `OdysseusException` | A user-correctable invalid command. | Explains the correction, leaves the task list unchanged, and does not end the conversation. | System failure |
| Conversation | TBD | The exchange through which the user interacts with the chatbot. | Must remain understandable to the user. | `request` when a domain term is needed |
