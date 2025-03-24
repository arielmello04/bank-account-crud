CREATE TABLE account (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    number_account INT UNIQUE NOT NULL,
    agency INT NOT NULL
);