# Ubiquitous Language

| Business Term | Technical Symbol | Definition | Constraints | Avoid |
| --- | --- | --- | --- | --- |
| Personal Assistant Chatbot | `Odysseus` | The application that helps one person keep track of various things. | All user-facing text uses Odysseus's clever, nautical, Odyssey-inspired voice. | Generic `app` |
| User | `User` | The individual using the chatbot to keep track of their information. | The project is intended for a person, not a multi-user service. | `Customer` |
| Tracked Information | TBD | A thing the user asks the chatbot to keep track of. | Define concrete categories only when requirements specify them. | Vague `data` |
| Conversation | TBD | The exchange through which the user interacts with the chatbot. | Must remain understandable to the user. | `request` when a domain term is needed |
