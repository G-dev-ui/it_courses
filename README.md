# IT Courses (Android)

Учебное Android‑приложение “Courses” с каталогом курсов, избранным, профилем и экраном курса. Проект сделан в модульной архитектуре (core/feature), с навигацией через Jetpack Navigation и DI через Koin.

## Функциональность

- **Главная**: поиск/сортировка и список курсов (карточки с изображением, рейтингом, датой, описанием и ценой).
- **Избранное**: список курсов, добавленных в избранное.
- **Профиль**: меню действий + “Ваши курсы” со списком и прогрессом прохождения.
- **Экран курса**: обложка, действия, блок автора, кнопки действий и описание “О курсе”.

## Стек и ключевые библиотеки

- **Язык**: Kotlin, JVM 17
- **UI**: XML + ViewBinding, Material Components, ConstraintLayout, RecyclerView
- **Навигация**: Jetpack Navigation (`nav_graph.xml`), deeplink `courses://course/{courseId}`
- **DI**: Koin
- **Сеть/данные**: Retrofit + OkHttp + Moshi, DataStore (для хранения избранного)
- **Списки**: AdapterDelegates (adapterdelegates4)

## Архитектура и структура модулей

Проект разделён на `core` (общие компоненты) и `feature` (фичи/экраны).

### Модули

- **`app`**
  - Точка входа (Activity + `BottomNavigationView`)
  - Инициализация DI (`CoursesApp`)
  - Общие зависимости приложения
- **`core:ui`**
  - Базовые UI‑компоненты, темы/цвета/размеры
  - Общие drawable и стили (в т.ч. `TextAppearance.ItCourses.*`)
  - UI‑утилиты (например, top‑crop изображений)
- **`core:common`**
  - Доменные модели, `AppResult`
  - UseCase’ы (`GetCoursesUseCase`, `GetCourseUseCase`, `ToggleFavoriteUseCase` и т.д.)
- **`core:network`**
  - API слой (Retrofit), репозиторий `CoursesRepositoryImpl`
  - Хранилище избранного (`FavoritesStore` на DataStore)
- **`core:network-mock`**
  - Фабрика мок‑сети (`MockNetworkFactory`) для учебного режима без реального backend’а
- **`feature:home`**, **`feature:favorites`**, **`feature:profile`**, **`feature:course`**
  - Каждый модуль содержит экран/логику фичи: Fragment + ViewModel + state/model + layout’ы

### UI‑подход

- Экран = **Fragment** + **ViewModel** + **UiState**
- Данные от ViewModel приходят через **StateFlow**, UI подписывается в `repeatOnLifecycle(STARTED)`
- Списки реализованы через **RecyclerView + AdapterDelegates**

## Навигация

Граф навигации: `app/src/main/res/navigation/nav_graph.xml`

- Нижняя навигация: `BottomNavigationView` (меню `app/src/main/res/menu/bottom_nav.xml`)
- Экран курса открывается по deeplink:

```text
courses://course/{courseId}
```

## Типографика (Figma → Android)

Типографика вынесена в общие `TextAppearance`‑стили:

- `core/ui/src/main/res/values/text_appearances.xml`
- `core/ui/src/main/res/values/styles.xml`

Это позволяет централизованно менять размеры/веса/межстрочные интервалы без правок во всех layout’ах.

## Сборка и запуск

### Требования

- Android Studio (Giraffe/или новее)
- JDK 17

### Debug APK

Собрать debug APK:

```bash
./gradlew :app:assembleDebug
```

Готовый файл:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Запуск на устройстве/эмуляторе

```bash
./gradlew :app:installDebug
```

## Примечания

- В проекте используется **мок‑сеть** (`core:network-mock`) — приложение не требует реального сервера.
- Избранное хранится локально через **DataStore**.

