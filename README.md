# Selenium + Cucumber BDD Testing 

> Suite de automatización UI con Selenium, Cucumber y BDD.
> Prueba el sitio real de [Free Range Testers](https://www.freerangetesters.com)
> con escenarios escritos en Gherkin, Page Object Model y pipeline CI/CD
> en GitHub Actions.

---

## Por qué Selenium después de Playwright — y qué aprendí de la diferencia

Cuando aprendí QA Automation, empecé con Playwright porque es más
moderno, más rápido y tiene mejor API. Pero en entrevistas me hicieron
preguntas sobre Selenium que no pude responder bien: ¿qué es un
WebDriver? ¿cuál es la diferencia entre implicit y explicit wait?
¿cómo manejas drivers en distintos sistemas operativos?

Selenium tiene más de 15 años de historia y sigue siendo la herramienta
que usan miles de proyectos en producción — especialmente en empresas
con sistemas legacy. Aprender los dos me dio perspectiva real de cuándo
usar cada uno:

| | Selenium | Playwright |
|---|---|---|
| Madurez | 15+ años, amplia comunidad | ~5 años, creciendo rápido |
| Setup | Necesita WebDriverManager | Viene con browsers incluidos |
| Waits | Implicit + Explicit (manual) | Auto-wait nativo |
| Velocidad | Más lento | Más rápido |
| Cuándo usarlo | Proyectos legacy, Java teams | Proyectos nuevos, TS/JS teams |

---

## BDD con Cucumber — por qué importa más allá del código

BDD (Behavior-Driven Development) no es una forma de escribir tests.
Es una forma de comunicación. Un escenario en Gherkin puede leerlo
el Product Owner, el desarrollador y el QA — y todos entienden
exactamente qué se prueba:

```gherkin
Scenario: I can access the subpages through the navigation bar
  Given I navigate to www.freerangetesters.com
  When I go to "Cursos" using the navigation bar
  Then I should be on the "Cursos" page
```

Esto no es documentación que queda desactualizada. Es el test en sí.
Cuando el escenario falla, el mensaje de error dice exactamente qué
paso falló en lenguaje de negocio, no en términos técnicos de código.

---

## Qué se prueba — y el análisis detrás de cada caso

### Sistema bajo prueba
El sitio real de **Free Range Testers** — una plataforma de cursos
de QA Automation con navegación, páginas de cursos, checkout y registro.

Elegí una web real en lugar de un sandbox de práctica porque los
sitios reales cambian, tienen comportamientos inesperados y presentan
los mismos desafíos que encontrarías en un proyecto profesional.

---

### Escenario 1 — Navegación a subpáginas (Scenario Outline)

```gherkin
@Courses
Scenario Outline: I can access the subpages through the navigation bar
  When I go to "<section>" using the navigation bar
  Then I should be on the "<section>" page

  Examples:
    | section   |
    | Cursos    |
    | Recursos  |
    | Mentorías |
    | suscripciones  |
    | Blog      |
```

**Por qué es un Scenario Outline y no 5 scenarios separados:**
El `Scenario Outline` con `Examples` es la forma que tiene Cucumber
de hacer data-driven testing. Un solo escenario corre 5 veces con
datos distintos. Si mañana agregan una sección "Podcast" al menú,
solo agrego una fila a la tabla — no creo un escenario nuevo.

---

### Escenario 2 — Flujo de cursos hasta checkout

```gherkin
@Plans
  Scenario: Courses are presented correctly to potential customers
    When I go to "Cursos" using the navigation bar
    And The user selects Introduction to testing course
    When The user selects Comprar ahora
    Then I can validate the options in the checkout page
```

**Qué valida:**

- Que el flujo completo desde la navegación hasta el checkout funciona.
- Que los radio buttons de planes están presentes y sus atributos (value, id, etc.) son exactamente los esperados.

 Eso es exactamente su propósito — actuar como
un contrato que alerta que la configuración de planes en producción cambió.

---

## Decisiones técnicas que aprendí debuggeando

### Implicit vs Explicit Wait — la lección más importante

```java
// ❌ Implicit Wait — aplica globalmente, puede enmascarar problemas
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// ✅ Explicit Wait — espera una condición específica
new WebDriverWait(driver, Duration.ofSeconds(15))
    .until(ExpectedConditions.urlContains("cursos"));
```

La trampa que descubrí: mezclar ambos puede causar timeouts dobles.
Si tienes implicit de 10s y explicit de 15s, en el peor caso esperas
25 segundos antes de que falle un test. Usé Explicit Wait
específicamente para las navegaciones, que son las operaciones
más lentas.

### Por qué WebDriverManager en lugar de driver manual

Sin WebDriverManager:
```bash
# Tienes que descargar manualmente chromedriver.exe
# Verificar que coincida con tu versión de Chrome
# Configurar el PATH
# En CI la versión de Chrome puede ser distinta a la tuya
```

Con WebDriverManager:
```java
WebDriverManager.chromedriver().setup(); // Detecta versión y descarga automático
WebDriver driver = new ChromeDriver();
```

En GitHub Actions esto es crítico porque el runner puede tener
una versión diferente de Chrome a la de tu máquina local.

### Headless solo en CI — no en local

```java
if (System.getenv("CI") != null) {
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
}
```

En local quiero ver el navegador — me ayuda a debuggear cuando
algo falla. En CI no hay pantalla, así que headless es obligatorio.
El flag `CI=true` lo setea GitHub Actions automáticamente.

### Page Object Model — por qué cada página es una clase

Sin POM, si el selector del menú de navegación cambia hay que
buscarlo en cada test que lo use. Con POM:

```java
// pages/PaginaPrincipal.java — UN solo lugar
@FindBy(css = "nav a[href*='cursos']")
private WebElement cursosLink;

public void clickOnSectionNavigationBar(String section) {
    // lógica centralizada
}
```

Si el selector cambia, se modifica en `PaginaPrincipal.java`
y todos los steps que llaman a ese método se actualizan solos.


---

## Cómo ejecutar

**Requisitos:** Java 17, Google Chrome instalado

```bash
git clone https://github.com/ipanaque94/SELENIUMFREERANGE.git
cd SELENIUMFREERANGE
./gradlew clean test
```

**Ejecutar solo escenarios de navegación:**
```bash
./gradlew test -Dcucumber.options="--tags @Courses"
```

**Ejecutar solo escenarios de planes:**
```bash
./gradlew test -Dcucumber.options="--tags @Plans"
```

**Generar reporte Cucumber HTML:**
```bash
./gradlew generateCucumberReport
# Reporte en: build/cucumber-report/
```

---

## Stack

| Herramienta | Versión | Uso |
|---|---|---|
| Selenium | 4.38.0 | Automatización del navegador con WebDriver |
| Cucumber | 7.30.0 | Framework BDD — escenarios en Gherkin |
| WebDriverManager | 6.3.2 | Gestión automática de drivers |
| JUnit 5 (assertions) | 5.10.0 | `assertEquals` y `assertTrue` en los steps |
| Gradle | 9 | Build y gestión de dependencias |
| GitHub Actions | — | CI/CD con Chrome headless |

---

## Estructura del proyecto

```
src/test/
├── java/
│   ├── driver/
│   │   └── DriverManager.java      → Singleton de WebDriver con modo headless en CI
│   ├── pages/
│   │   ├── BasePage.java           → Métodos compartidos entre páginas
│   │   ├── PaginaPrincipal.java    → Landing page y navegación
│   │   ├── PaginaCursos.java       → Listado de cursos
│   │   ├── PaginaIntroducción...   → Página del curso de testing
│   │   └── PaginaRegistro.java     → Checkout y registro
│   ├── steps/
│   │   ├── FreeRangeSteps.java     → Step Definitions conectan Gherkin con Java
│   │   └── Hooks.java              → @Before/@After con screenshot en fallos
│   ├── runner/
│   │   └── RunCucumberTest.java    → @RunWith(Cucumber.class) — punto de entrada
│   └── report/
│       └── GenerateReport.java     → Reporte HTML con cucumber-reporting
└── resources/
    ├── features/
    │   └── FreeRangeNavigation.feature
    └── cucumber.properties
```

---

## Dónde encaja en mi aprendizaje

Este fue mi primer proyecto de UI Testing con Java. El lenguaje que
ya conocía de mi proyecto de
[RestAssured API Testing](https://github.com/ipanaque94/RestAssured3APIS)
me permitió enfocarme en aprender Selenium y Cucumber sin tener que
aprender un lenguaje nuevo al mismo tiempo.

Después construí el framework de
[Playwright](https://github.com/ipanaque94/playwright-professional-framework)
para entender las diferencias en la práctica — y apreciar mejor qué
problemas resuelve cada herramienta.

---

## Autor

**Enoc Ipanaque** — Lima, Perú

QA Automation Engineer en formación, estudiando para ISTQB Foundation Level.

[LinkedIn](www.linkedin.com/in/enoc-isaac-ipanaque-rodas-b3729a283) · [GitHub](https://github.com/ipanaque94)
