ALTER TABLE account ADD COLUMN active BOOLEAN;
UPDATE account SET active = true;