Create TABLE IF NOT EXISTS Users
(
    userID INTEGER PRIMARY KEY,
    name VARCHAR(30) UNIQUE,
    passwordHash VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS Notes
(
    noteID INTEGER PRIMARY KEY,
    noteName VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS LevelNotes
(
    levelID INTEGER PRIMARY KEY,
    userID INTEGER REFERENCES Users(userID),
    name VARCHAR(30) UNIQUE,
    lowerNoteBound INTEGER,
    FOREIGN KEY (lowerNoteBound) REFERENCES Notes(noteID),
    higherNoteBound INTEGER,
    FOREIGN KEY (higherNoteBound) REFERENCES Notes(noteID),
    startingWave INTEGER,
    endingWave INTEGER,
    repetitionsNextWave INTEGER
);

CREATE TABLE IF NOT EXISTS NotesGames
(
    notesGameID INTEGER PRIMARY KEY,
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
    levelNotesID INTEGER,
    foreign key (levelNotesID) REFERENCES LevelNotes(levelID),
    datePlayed DATE
);

CREATE TABLE IF NOT EXISTS AnswersNotesGame
(
    answersNotesGameID INTEGER PRIMARY KEY,
    notesGameID INTEGER,
    foreign key (notesGameID) REFERENCES NotesGames(notesGameID),
    noteID INTEGER,
    foreign key (noteID) REFERENCES Notes(noteID),
    noteOccurrences INTEGER,
    noteGuessedCorrectly INTEGER
);

CREATE TABLE IF NOT EXISTS LevelIntervals
(
    levelID INTEGER PRIMARY KEY,
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
    name VARCHAR(30) UNIQUE,
    numberOfRepetitions INTEGER,
    up BOOLEAN,
    together BOOLEAN,
    down BOOLEAN
);

CREATE TABLE IF NOT EXISTS IntervalsGame
(
    intervalsGameID INTEGER PRIMARY KEY,
    intervalLevelID INTEGER REFERENCES LevelIntervals(levelID),
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
    datePlayed DATE
);

CREATE TABLE IF NOT EXISTS Intervals
(
    intervalID INTEGER PRIMARY KEY,
    intervalName VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS AnswersIntervalsGame
(
    answerIntervalsGameID INTEGER PRIMARY KEY,
    intervalsGameID INTEGER,
    foreign key (intervalsGameID) REFERENCES IntervalsGame(intervalsGameID),
    intervalID INTEGER,
    foreign key (intervalID) REFERENCES Intervals(intervalID),
    intervalOccurrences INTEGER,
    intervalGuessedCorrectly INTEGER
);