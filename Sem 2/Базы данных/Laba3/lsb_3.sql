CREATE OR REPLACE FUNCTION property_finding_date_initialize() RETURNS TRIGGER
LANGUAGE PLPGSQL
AS $$
    BEGIN
    if (new.finding_date IS NULL) THEN
        new.finding_date := current_timestamp AT TIME ZONE 'Europe/Moscow';
    end if;
    RETURN new;
END $$;

CREATE OR REPLACE TRIGGER property_finding_date_initialize_trigger
BEFORE UPDATE OR INSERT ON property_finding FOR EACH ROW EXECUTE PROCEDURE property_finding_date_initialize();

--- Пример запроса:
INSERT INTO "property_finding"(property_name_id, character_id, object_id, result) VALUES
    ((SELECT id FROM "property_name" WHERE name='плотность'), (SELECT id FROM "character" WHERE name='Василий'),
    (SELECT id FROM "object" WHERE object_name_id=(SELECT id FROM "object_name" WHERE name='Большой брат')), '1.3');

UPDATE "property_finding" SET property_name_id=(SELECT id FROM "property_name" WHERE name='масса') WHERE id='3';