# QuizSystem

A small command-line Java application for creating and taking multiple-choice quizzes,
with quizzes and questions stored in a local SQLite database.

## Features

- Create a quiz: name, description, questions, answers and the correct answer
- Solve an existing quiz by its ID and get a score at the end
- Quizzes and questions persisted in `database/QuizSystem.db` (SQLite)
- Tables auto-registered via reflection

## Requirements

- Java 17
- Maven

## Build

```bash
mvn clean package
```

The pom does not configure an executable jar, so run the `QuizSystemMain` class from your
IDE.

## Usage

The application prints a menu:

1. Create new Quiz
2. Solve a Quiz (enter the quiz ID)

## Project structure

- `src/main/java/me/hubailmn/QuizSystem` — entry point and managers
- `.../data/database` — SQLite connection and tables
- `.../utils` — console colors and helpers
