# Homework for custom lint rules event

Правило, которое определяет, что ветка внутри when-expression может быть удалена т.к. существует ветка else с точно таким же выражением.

Выражение
```kt
when (progress) {
    Progress.NOT_STARTED -> "NOT_STARTED"
    Progress.IN_PROGRESS -> "IN_PROGRESS"
    Progress.COMPLETED -> "COMPLETED"
    else -> "NOT_STARTED"
}
```
может быть заменено на:

```kt
when (progress) {
    Progress.IN_PROGRESS -> "IN_PROGRESS"
    Progress.COMPLETED -> "COMPLETED"
    else -> "NOT_STARTED"
}
```
