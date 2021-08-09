CREATE DATABASE edibles;

CREATE TABLE edibles.compositions(
    id int AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY(id)
);
/*
ALTER TABLE edibles.compositions RENAME COLUMN name TO composition_name;
ALTER TABLE edibles.compositions RENAME COLUMN composition_name TO name;
*/
CREATE TABLE edibles.ingredients(
    id int AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE edibles.molecules(
    id int AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    rx_required bool,
    PRIMARY KEY(id)
);

CREATE TABLE edibles.composition_ingredients(
    id int AUTO_INCREMENT UNIQUE,
    composition_id int,
    ingredient_id int,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (composition_id) REFERENCES edibles.compositions(id) ON DELETE SET NULL,
    CONSTRAINT FOREIGN KEY (ingredient_id) REFERENCES edibles.ingredients(id) ON DELETE SET NULL,
    unit varchar(255) NOT NULL,
    strength float NOT NULL
);

CREATE TABLE edibles.molecule_ingredients(
    id int AUTO_INCREMENT UNIQUE,
    molecule_id int,
    ingredient_id int,
    PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (molecule_id) REFERENCES edibles.molecules(id) ON DELETE SET NULL,
    CONSTRAINT FOREIGN KEY (ingredient_id) REFERENCES edibles.ingredients(id) ON DELETE SET NULL
);

INSERT INTO edibles.compositions (name) VALUES ('D');

INSERT INTO edibles.ingredients (name) VALUES ('diclofenac');

SET SQL_SAFE_UPDATES = 0;
DELETE FROM edibles.molecules WHERE name='paracetemol+diclofenac';

INSERT INTO edibles.molecules (name,rx_required) VALUES ('paracetemol+diclofenac',true);

INSERT INTO edibles.composition_ingredients(composition_id,ingredient_id, unit, strength) VALUES ('3','2', 'MGG', '12.7');

INSERT INTO edibles.molecule_ingredients(molecule_id,ingredient_id) VALUES ('6','3');

SELECT * FROM edibles.compositions;
SELECT * FROM edibles.ingredients;
SELECT * FROM edibles.molecules;
SELECT * FROM edibles.composition_ingredients;
SELECT * FROM edibles.molecule_ingredients


