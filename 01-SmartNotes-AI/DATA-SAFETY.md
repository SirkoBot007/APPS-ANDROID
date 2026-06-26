# Seguridad de los Datos (Play Console) — SmartNotes AI

Respuestas para rellenar el formulario **Seguridad de los datos** en Google Play Console.

| Pregunta | Respuesta |
|----------|-----------|
| ¿Recopila o comparte datos del usuario? | La app no recopila datos en servidores propios (no los hay). El texto de una nota se **envía** al proveedor de IA solo cuando el usuario usa una acción de IA. |
| Tipo de datos | **Contenido generado por el usuario** (el texto de la nota que el usuario decide enviar a la IA). |
| ¿Se comparten con terceros? | Sí: con **Anthropic** (API de Claude), únicamente para procesar la acción de IA solicitada. |
| Finalidad | Funcionalidad de la app (generar el resumen/mejora/ampliación). No publicidad, no analítica. |
| ¿Es obligatorio? | No. Solo ocurre si el usuario pulsa una acción de IA. |
| ¿Cifrado en tránsito? | **Sí (HTTPS).** |
| ¿El usuario puede solicitar borrado? | Los datos no se almacenan en servidores de la app; las notas se borran en el dispositivo. |
| Datos recopilados automáticamente (ubicación, IDs, etc.) | **Ninguno.** |

> Nota: el envío de texto a la IA es **iniciado por el usuario** y limitado a la nota concreta.
> Declarar con transparencia este flujo evita rechazos en la revisión de Play.
