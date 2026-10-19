# España Outdoor — plan de integración sobre Organic Maps

## Estado y alcance

**Primera integración funcional incorporada:** el menú nativo incluye una entrada «España Outdoor» y abre una pantalla propia con accesos al mapa/navegación, descarga de mapas offline y rutas/favoritos/tracks. La conexión con meteorología oficial, evaluación de riesgos y Rescue Link sigue pendiente. La compilación y las pruebas del APK después de estos cambios deben completarse antes de aceptar esta iteración.

Esta rama, `feat/espana-outdoor-integration`, parte del commit upstream `e78a075b7ae77bb31510918a26545d77ea4ae690`. Su propósito es integrar España Outdoor de forma incremental sobre la aplicación nativa, conservando el historial y permitiendo comparar cada cambio con upstream.

**No se cambia todavía el identificador de aplicación, el nombre visible, los iconos ni el diseño de pantallas.** Primero debe obtenerse una compilación reproducible de la base sin modificaciones y validarse en un dispositivo Android.

## Secuencia obligatoria

1. **Reproducibilidad:** clonar con todos los submódulos, comprobar el commit y ejecutar el procedimiento Windows documentado en el repositorio principal.
2. **Línea base:** generar el APK F-Droid debug ARM64, guardar log, informe JSON y SHA-256. Si falla, corregir o documentar la causa antes de personalizar.
3. **Pruebas de campo:** instalación, permisos, localización GPS, navegación, descarga y uso de mapas offline, modo avión, pérdida y recuperación de conectividad, importación/exportación GPX y consumo/estabilidad.
4. **Inventario de integración:** mapear qué funcionalidades ya aporta Organic Maps y qué servicios del proyecto España Outdoor pueden integrarse sin duplicar motores cartográficos ni navegación.
5. **Cambios pequeños:** cada capacidad propia en commits separados, con pruebas y posibilidad de revertir.
6. **Identidad de producto:** solo después de validar la base, definir paquete, nombre, iconos, pantallas y distribución. Mantener atribuciones visibles.
7. **Sincronización upstream:** conservar el remoto upstream y documentar el commit base de cada actualización. No hacer force-push ni reemplazar la historia compartida.

## Atribución y licencias

- Conservar `LICENSE`, `NOTICE`, `DATA_LICENSE.txt`, `LEGAL`, `.reuse/dep5` y las licencias de dependencias.
- Si se usa código o interfaz de Organic Maps, incluir una mención visible y legible al “Organic Maps Project” con enlace a https://organicmaps.app, por ejemplo en “Acerca de” y el menú principal.
- Cuando se utilicen datos cartográficos binarios de Organic Maps, mostrar en el mapa y en zonas visibles “Map data © OpenStreetMap and Organic Maps”, con enlaces a https://organicmaps.app y https://openstreetmap.org/copyright, conforme a `DATA_LICENSE.txt`.
- Revisar licencias por componente, incluidas dependencias y datos; la licencia principal del código no sustituye las licencias de terceros ni las condiciones de los datos.
- No retirar atribuciones ni tratar el fork como producto white-label sin autorización escrita del equipo de Organic Maps.

## Criterios para declarar la base validada

No se considera terminada la validación hasta disponer de:
- compilación limpia con entorno y commit registrados;
- APK identificado por variante, arquitectura y SHA-256;
- pruebas reales en dispositivo Android con resultados anotados;
- verificación de atribución y licencias;
- lista explícita de limitaciones conocidas.

## Relación con el proyecto principal

El repositorio principal `Baltas80/Espana-Outdoors` mantiene la web, servicios, documentación y operaciones. Este fork contiene la base nativa y su personalización. No se fusionarán cambios entre ambos repositorios sin una decisión técnica explícita y una revisión de integración.
