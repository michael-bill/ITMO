--- Не эффективен:
CREATE INDEX idx_типы_ведомостей ON Н_ТИПЫ_ВЕДОМОСТЕЙ (ИД);
--- Не эффективен:
CREATE INDEX idx_ведомости_тв_ид ON Н_ВЕДОМОСТИ (ТВ_ИД);
--- Эффективен
CREATE INDEX idx_ведомости_члвк_ид ON Н_ВЕДОМОСТИ (ЧЛВК_ИД);

SELECT Н_ТИПЫ_ВЕДОМОСТЕЙ.НАИМЕНОВАНИЕ, Н_ВЕДОМОСТИ.ИД
FROM Н_ВЕДОМОСТИ
JOIN Н_ТИПЫ_ВЕДОМОСТЕЙ ON Н_ТИПЫ_ВЕДОМОСТЕЙ.ИД = Н_ВЕДОМОСТИ.ТВ_ИД
WHERE
    Н_ТИПЫ_ВЕДОМОСТЕЙ.ИД = 1 AND
    Н_ВЕДОМОСТИ.ЧЛВК_ИД < 153285 AND
    Н_ВЕДОМОСТИ.ЧЛВК_ИД > 142390;

--- EXPLAIN ANALYZE
Nested Loop  (cost=496.17..5502.69 rows=30924 width=422) (actual time=1.916..15.219 rows=28671 loops=1)
   ->  Seq Scan on "Н_ТИПЫ_ВЕДОМОСТЕЙ"  (cost=0.00..1.04 rows=1 width=422) (actual time=0.019..0.022 rows=1 loops=1)
         Filter: ("ИД" = 1)
         Rows Removed by Filter: 2
   ->  Bitmap Heap Scan on "Н_ВЕДОМОСТИ"  (cost=496.17..5192.41 rows=30924 width=8) (actual time=1.893..11.767 rows=28671 loops=1)
         Recheck Cond: (("ЧЛВК_ИД" < 153285) AND ("ЧЛВК_ИД" > 142390))
         Filter: ("ТВ_ИД" = 1)
         Rows Removed by Filter: 7602
         Heap Blocks: exact=1645
         ->  Bitmap Index Scan on "ВЕД_ЧЛВК_FK_IFK"  (cost=0.00..488.44 rows=36014 width=0) (actual time=1.656..1.657 rows=36273 loops=1)
               Index Cond: (("ЧЛВК_ИД" < 153285) AND ("ЧЛВК_ИД" > 142390))
Planning Time: 0.835 ms
Execution Time: 16.650 ms