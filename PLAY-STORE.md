# Guía de publicación en Google Play — APPS-ANDROID

Esta guía deja las 4 apps **listas para subir a Google Play**. El código y la configuración ya
están preparados (firma, R8, AAB, fichas, privacidad). Lo que queda son los pasos que **solo puede
hacer el titular de la cuenta** (crear el keystore, la cuenta de Play, subir el AAB y rellenar
formularios). Sigue el orden de abajo.

> Repo: `SirkoBot007/APPS-ANDROID` · Apps: SmartNotes AI, FinTrack, CryptoPulse, DayFlow
> Cada app es un proyecto Android independiente dentro de este monorepo.

---

## 0) Lo que YA está preparado en el repo
- ✅ **Firma de release** vía `keystore.properties` (plantilla `keystore.properties.example` en cada app).
- ✅ **R8 / minify + shrinkResources** activados en `release`, con reglas ProGuard correctas
  (incluye *keep* de los enums que se guardan en base de datos).
- ✅ **`targetSdk = 35`** y `versionName = "1.0.0"`, `versionCode = 1` (requisito de Play 2025/2026).
- ✅ **Fichas de tienda** (textos ES + EN) en formato Fastlane: `app/.../fastlane/metadata/android/`.
- ✅ **Política de privacidad** y **Seguridad de los datos** por app (`PRIVACY-POLICY.md`, `DATA-SAFETY.md`).
- ✅ **CI** que compila las 4 apps en cada push (`.github/workflows/android-ci.yml`).
- ⛔ **Pendiente (tú):** keystore real, cuenta Play Console, capturas/gráficos PNG, y la subida.

---

## 1) Requisitos previos
1. **Cuenta de Google Play Console** (pago único de 25 USD): https://play.google.com/console/signup
2. **Android Studio** actualizado (para generar el AAB y el keystore).
3. (Opcional) **Fastlane** si quieres automatizar la subida de fichas/binarios.

---

## 2) Generar el keystore de firma (UNA vez por app, o uno compartido)
> ⚠️ **CRÍTICO:** guarda el keystore y sus contraseñas en un sitio seguro y con copia de seguridad.
> Si lo pierdes, **no podrás volver a actualizar la app** en Google Play. (Con *Play App Signing*,
> este keystore es tu *upload key*; aun así, trátalo como crítico.)

En la carpeta de cada app (ej. `01-SmartNotes-AI/`):
```bash
keytool -genkeypair -v -keystore release-keystore.jks -alias release \
    -keyalg RSA -keysize 2048 -validity 10000
```
Luego copia la plantilla y rellena tus valores:
```bash
cp keystore.properties.example keystore.properties
# edita keystore.properties con tus contraseñas reales
```
`keystore.properties` y `*.jks` están en `.gitignore` — **nunca se suben a GitHub**.

Puedes usar **un único keystore** para las 4 apps (más cómodo) o uno por app (más aislado).
Si compartes uno, apunta cada `keystore.properties` al mismo `.jks`.

---

## 3) Generar el AAB firmado (lo que se sube a Play)
Play exige **Android App Bundle (.aab)**, no APK. Desde la carpeta de cada app:
```bash
./gradlew :app:bundleRelease
```
Salida: `app/build/outputs/bundle/release/app-release.aab`.

> Si no existe `keystore.properties`, la build no se firma automáticamente (no rompe el build, pero
> el AAB no será válido para Play hasta que la firma esté configurada).

**Prueba la build de release antes de publicar** (R8 puede romper algo que en debug funciona):
```bash
./gradlew :app:installRelease   # instala el APK release en un dispositivo/emulador
```
Comprueba que cada app abre, navega y funciona (en SmartNotes AI, prueba una acción de IA con tu
API key en `local.properties`).

---

## 4) Gráficos obligatorios de la ficha (PNG/JPG — los creas tú o Claude Design)
Colócalos en `…/fastlane/metadata/android/<locale>/images/` (o súbelos a mano en Play Console):

| Recurso | Tamaño | Notas |
|---------|--------|-------|
| **Icono de la ficha** (`icon.png`) | 512 × 512 px | PNG 32-bit. (El icono *in-app* ya existe como vector). |
| **Gráfico destacado** (`featureGraphic.png`) | 1024 × 500 px | Banner superior de la ficha. |
| **Capturas de teléfono** (`phoneScreenshots/1.png`, `2.png`, …) | mín. 2, hasta 8 | 16:9 o 9:16; lado mín. 320 px. |
| (Opcional) Capturas de tablet 7"/10" | — | Si publicas para tablets. |

> Las capturas se obtienen ejecutando cada app en el emulador (Android Studio → *Running Devices* →
> botón de captura). El icono y el destacado son tarea de diseño; el color de marca de cada app está
> en su `ui/theme`.

---

## 5) Política de privacidad con URL pública (obligatoria)
Cada app trae `PRIVACY-POLICY.md`. Play pide una **URL**. La forma gratuita más rápida:
1. En GitHub: repo **Settings → Pages → Deploy from branch → `main` / root**.
2. La política quedará accesible, p. ej.:
   `https://sirkobot007.github.io/APPS-ANDROID/01-SmartNotes-AI/PRIVACY-POLICY` (según configuración de Pages).
3. Rellena el email de contacto (`[TU EMAIL DE CONTACTO]`) en cada `PRIVACY-POLICY.md` antes de publicar.

(Alternativa: pegar el contenido en cualquier hosting o en una página de Notion pública.)

---

## 6) Crear la app en Play Console y rellenar fichas
Por cada app:
1. **Crear app** → idioma por defecto **es-ES**, tipo *App*, gratis.
2. **Ficha principal:** copia los textos de `…/fastlane/metadata/android/es-ES/` (título, descripción
   breve, descripción completa) y añade el idioma **en-US** con los de esa carpeta.
3. Sube los **gráficos** del paso 4.
4. **Seguridad de los datos:** rellénalo con `DATA-SAFETY.md` de la app.
5. **Clasificación de contenido (IARC):** completa el cuestionario (estas apps son aptas para todos
   los públicos; CryptoPulse es informativa, sin juego real ni transacciones).
6. **Política de privacidad:** pega la URL del paso 5.
7. **País/regiones** y **categoría** (sugerencia: SmartNotes→Productividad, FinTrack→Finanzas,
   CryptoPulse→Finanzas, DayFlow→Productividad).

---

## 7) Subir el AAB y lanzar
1. **Versiones → Pruebas internas** (recomendado primero) → crear versión → subir `app-release.aab`.
2. Pega las **notas de la versión** desde `…/fastlane/metadata/android/es-ES/changelogs/1.txt`.
3. Revisa, lanza a **pruebas internas**, prueba en tu dispositivo.
4. Cuando esté validado → promociona a **Producción**.

> Automatización opcional: con **Fastlane supply** y una cuenta de servicio de Google Play puedes
> subir fichas, gráficos y AAB con un comando. La estructura `fastlane/metadata/android` ya es la que
> espera `supply`.

---

## 8) Checklist final por app
- [ ] `keystore.properties` creado y `release-keystore.jks` con copia de seguridad.
- [ ] `./gradlew :app:bundleRelease` genera AAB firmado.
- [ ] Build de release probada en dispositivo (R8 OK).
- [ ] Email de contacto rellenado en `PRIVACY-POLICY.md` + URL pública publicada.
- [ ] Icono 512, destacado 1024×500 y ≥2 capturas subidos.
- [ ] Ficha es-ES + en-US completas.
- [ ] Seguridad de los datos + clasificación de contenido completadas.
- [ ] AAB subido a pruebas internas → validado → producción.

---

## Notas técnicas
- **SmartNotes AI** necesita `ANTHROPIC_API_KEY` en `local.properties` para sus funciones de IA.
  Para una app pública en producción, lo ideal es mover esa clave a un **backend propio** (proxy)
  en lugar de incrustarla en el cliente (ver README de la app).
- **compileSdk/targetSdk 35** y **AGP 8.6.1**: si Android Studio pide instalar la plataforma de
  Android 15 (API 35) o build-tools, acéptalo en el primer *sync*.
- Versionado: sube `versionCode` (+1) en cada nueva publicación; `versionName` a tu criterio
  (1.0.0 → 1.0.1 …).
