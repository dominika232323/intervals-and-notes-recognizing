Create TABLE IF NOT EXISTS Users
(
    userID INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) UNIQUE,
    passwordHash VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS Notes
(
    noteID INTEGER AUTO_INCREMENT PRIMARY KEY,
    noteName VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS LevelNotes
(
    levelID INTEGER AUTO_INCREMENT PRIMARY KEY,
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
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
    notesGameID INTEGER AUTO_INCREMENT PRIMARY KEY,
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
    levelNotesID INTEGER,
    foreign key (levelNotesID) REFERENCES LevelNotes(levelID),
    datePlayed DATE
);

CREATE TABLE IF NOT EXISTS AnswersNotesGame
(
    answersNotesGameID INTEGER AUTO_INCREMENT PRIMARY KEY,
    notesGameID INTEGER,
    foreign key (notesGameID) REFERENCES NotesGames(notesGameID),
    noteID INTEGER,
    foreign key (noteID) REFERENCES Notes(noteID),
    noteOccurrences INTEGER,
    noteGuessedCorrectly INTEGER
);

CREATE TABLE IF NOT EXISTS LevelIntervals
(
    levelID INTEGER AUTO_INCREMENT PRIMARY KEY,
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
    intervalsGameID INTEGER AUTO_INCREMENT PRIMARY KEY,
    intervalLevelID INTEGER,
    foreign key (intervalLevelID) REFERENCES LevelIntervals(levelID),
    userID INTEGER,
    foreign key (userID) REFERENCES Users(userID),
    datePlayed DATE
);

CREATE TABLE IF NOT EXISTS Intervals
(
    intervalID INTEGER AUTO_INCREMENT PRIMARY KEY,
    intervalName VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS AnswersIntervalsGame
(
    answerIntervalsGameID INTEGER AUTO_INCREMENT PRIMARY KEY,
    intervalsGameID INTEGER,
    foreign key (intervalsGameID) REFERENCES IntervalsGame(intervalsGameID),
    intervalID INTEGER,
    foreign key (intervalID) REFERENCES Intervals(intervalID),
    intervalOccurrences INTEGER,
    intervalGuessedCorrectly INTEGER
);
