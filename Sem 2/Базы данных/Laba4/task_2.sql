--- Не эффективен:
CREATE INDEX idx_люди_ид ON Н_ЛЮДИ (ИД);
--- Не эффективен:
CREATE INDEX idx_ведомости_члвк_ид ON Н_ВЕДОМОСТИ (ЧЛВК_ИД);
--- Не эффективен:
CREATE INDEX idx_ведомости_дата ON Н_ВЕДОМОСТИ (ДАТА);

SELECT Н_ЛЮДИ.ФАМИЛИЯ, Н_ВЕДОМОСТИ.ДАТА, Н_СЕССИЯ.ЧЛВК_ИД
FROM Н_ЛЮДИ
JOIN Н_ВЕДОМОСТИ ON Н_ЛЮДИ.ИД = Н_ВЕДОМОСТИ.ЧЛВК_ИД
JOIN Н_СЕССИЯ ON Н_ЛЮДИ.ИД = Н_СЕССИЯ.ЧЛВК_ИД
WHERE
    Н_ЛЮДИ.ИД = 100865 AND
    Н_ВЕДОМОСТИ.ДАТА > '2022-06-08';

--- EXPLAIN ANALYZE
Nested Loop  (cost=0.86..20.21 rows=15 width=28) (actual time=0.036..0.037 rows=0 loops=1)
->  Nested Loop  (cost=0.58..15.51 rows=1 width=28) (actual time=0.035..0.036 rows=0 loops=1)
        ->  Index Scan using "ЧЛВК_PK" on "Н_ЛЮДИ"  (cost=0.28..8.30 rows=1 width=20) (actual time=0.032..0.032 rows=1 loops=1)
            Index Cond: ("ИД" = 100865)
        ->  Index Scan using "ВЕД_ДАТА_I" on "Н_ВЕДОМОСТИ"  (cost=0.29..7.20 rows=1 width=12) (actual time=0.002..0.002 rows=0 loops=1)
            Index Cond: ("ДАТА" > '2022-06-08 00:00:00'::timestamp without time zone)
            Filter: ("ЧЛВК_ИД" = 100865)
->  Index Only Scan using "SYS_C003500_IFK" on "Н_СЕССИЯ"  (cost=0.28..4.54 rows=15 width=4) (never executed)
        Index Cond: ("ЧЛВК_ИД" = 100865)
        Heap Fetches: 0
Planning Time: 0.704 ms
Execution Time: 0.088 ms